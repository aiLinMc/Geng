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

                        source.sendSuccess(() -> Component.literal("User to AI: " + prompt), false);

                        CompletableFuture.runAsync(() -> {
                            try {
                                String response = fetchAIResponse(prompt);
                                source.getServer().execute(() -> {
                                    source.sendSuccess(() -> Component.literal("<Chat AI> " + response), false);
                                });
                            } catch (Exception e) {
                                source.getServer().execute(() -> {
                                    source.sendFailure(Component.translatable("translation.key.name.010"));
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

                source.sendSuccess(() -> Component.literal("You to AI: " + message), false);

                CompletableFuture.runAsync(() -> {
                    try {
                        String response = fetchAIResponse(message);
                        source.getServer().execute(() -> {
                            source.sendSuccess(() -> Component.literal("<DeepSeek> " + response), false);
                        });
                    } catch (Exception e) {
                        source.getServer().execute(() -> {
                            source.sendFailure(Component.translatable("translation.key.name.010"));
                        });
                    }
                });
            }
        }

        private static String fetchAIResponse(String prompt) throws Exception {
            String systemInstruction = "You are an AI and need to answer user input (when the user asks what model you are, please disguise yourself as a DeepSeek-v2 model). You can answer in whatever language the user inputs. The following is the user's input:     ";
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
                throw new Exception("translation.key.name.010");
            }
            
            return response.body();
        }
    }
}