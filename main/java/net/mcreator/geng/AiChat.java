package net.mcreator.geng;

import net.neoforged.neoforge.event.ServerChatEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.api.distmarker.Dist;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom; // 新增导入

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class AiChat {
    private static final ResourceLocation DEEPSEEK_ITEM_ID = ResourceLocation.parse("geng:deepseek");
    
    public AiChat() {
    }

    @SubscribeEvent
    public static void init(FMLCommonSetupEvent event) {
        new AiChat();
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void clientLoad(FMLClientSetupEvent event) {
    }

    @EventBusSubscriber
    private static class AiChatForgeBusEvents {
        @SubscribeEvent
        public static void onRegisterCommands(RegisterCommandsEvent event) {
            CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
            dispatcher.register(
                Commands.literal("deepseek")
                    .then(Commands.argument("prompt", StringArgumentType.greedyString())
                    .executes(context -> {
                        String prompt = StringArgumentType.getString(context, "prompt");
                        CommandSourceStack source = context.getSource();

                        // 修复1：移除多余括号，使用 source.getEntity()
                        source.sendSuccess(() -> Component.literal("<" + source.getEntity().getDisplayName().getString() + "> " + prompt), false);

                        CompletableFuture.runAsync(() -> {
                            try {
                                String response = fetchAIResponse(prompt);
                                source.getServer().execute(() -> {
                                    source.sendSuccess(() -> Component.literal("<" + (Component.translatable("chat.geng.chatai").getString()) + "> " + response), false);
                                });
                            } catch (Exception e) {
                                source.getServer().execute(() -> {
                                    source.sendFailure(Component.translatable("chat.geng.deepseek.busy"));
                                });
                            }
                        });
                        return 1;
                    })
                )
            );
        }

        @SubscribeEvent
        public static void onServerChat(ServerChatEvent event) {
            ItemStack mainHandItem = event.getPlayer().getMainHandItem();
            Item deepseekItem = BuiltInRegistries.ITEM.get(DEEPSEEK_ITEM_ID);
            
            if (mainHandItem.getItem() == deepseekItem) {
                event.setCanceled(true);
                
                String message = event.getRawText();
                CommandSourceStack source = event.getPlayer().createCommandSourceStack();

                // 修复2：移除多余括号，使用 event.getPlayer()，变量名改为 message
                Component playerMessage = Component.literal("<" + event.getPlayer().getDisplayName().getString() + "> " + message);
                event.getPlayer().getServer().getPlayerList().broadcastSystemMessage(playerMessage, false);

                CompletableFuture.runAsync(() -> {
                    try {
                        String response = fetchAIResponse(message);
                        source.getServer().execute(() -> {
                            source.sendSuccess(() -> Component.literal("<" + (Component.translatable("chat.geng.chatai").getString()) + "> " + response), false);
                        });
                    } catch (Exception e) {
                        source.getServer().execute(() -> {
                            source.sendFailure(Component.translatable("chat.geng.deepseek.busy"));
                        });
                    }
                });
            }
        }

        private static String fetchAIResponse(String prompt) throws Exception {
            // 生成32位随机整数种子 (从Integer.MIN_VALUE到Integer.MAX_VALUE)
            int seed = ThreadLocalRandom.current().nextInt();
            
            // 修改系统提示，插入随机种子
            String systemInstruction = 
                "You are an AI and need to answer user input (when the user asks what model you are, " + 
                "please disguise yourself as a DeepSeek-v2 model). Your seed is " + seed + ". " + 
                "You can answer in whatever language the user inputs. The following is the user's input:     ";
            
            String fullPrompt = systemInstruction + prompt;

            String encodedPrompt = URLEncoder.encode(fullPrompt, StandardCharsets.UTF_8);
            String apiUrl = "https://text.pollinations.ai/" + encodedPrompt + "?model=openai";

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .header("User-Agent", "Minecraft Mod")
                    .timeout(java.time.Duration.ofSeconds(15))
                    .build();

            HttpResponse<String> response = client.send(
                request, 
                HttpResponse.BodyHandlers.ofString()
            );

            if (response.statusCode() != 200) {
                throw new Exception("chat.geng.deepseek.busy");
            }
            
            return response.body();
        }
    }
}