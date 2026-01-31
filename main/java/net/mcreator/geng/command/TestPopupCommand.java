package net.mcreator.geng.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.network.PacketDistributor;
import net.mcreator.geng.GengMod;
import net.mcreator.geng.network.ShowPopupPacket;

public class TestPopupCommand {
    
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("testpopup")
            .requires(source -> source.hasPermission(2))
            .executes(context -> {
                ServerPlayer player = context.getSource().getPlayer();
                if (player != null) {
                    // 发送测试弹窗
                    String testMessage = Component.translatable("chat.geng.lthacker.crash.test_detail").getString();
                    GengMod.LOGGER.info("发送测试弹窗到玩家: " + player.getName().getString());
                    
                    // 发送网络包
                    PacketDistributor.sendToPlayer(player, new ShowPopupPacket(testMessage));
                    
                    // 同时发送聊天消息确认
                    player.displayClientMessage(
                        Component.translatable("chat.geng.lthacker.testpopup_success"),
                        false
                    );
                    
                    // 服务器端也记录
                    context.getSource().sendSuccess(
                        () -> Component.translatable("chat.geng.lthacker.testpopup_server"),
                        false
                    );
                    
                    return 1;
                }
                return 0;
            })
        );
    }
}