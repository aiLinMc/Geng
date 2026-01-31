package net.mcreator.geng.procedures;

import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;
import net.neoforged.neoforge.network.PacketDistributor;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.CommandSource;

import net.mcreator.geng.network.GengModVariables;
import net.mcreator.geng.network.ShowPopupPacket;
import net.mcreator.geng.GengMod;

import javax.annotation.Nullable;
import java.util.List;

@EventBusSubscriber
public class HackerTickProcedure {
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        execute(event, event.getEntity());
    }

    public static void execute(Entity entity) {
        execute(null, entity);
    }

    private static void execute(@Nullable Event event, Entity entity) {
        if (entity == null)
            return;
        double rand = 0;
        Entity yyxc = null;
        if (entity.getData(GengModVariables.PLAYER_VARIABLES).inf_by_hackers) {
            if (Mth.nextInt(RandomSource.create(), 1, 400) == 200) {
                rand = Mth.nextInt(RandomSource.create(), 0, 20);
                if (rand < 12) {
                    // 原有标题显示逻辑
                    {
                        Entity _ent = entity;
                        if (!_ent.level().isClientSide() && _ent.getServer() != null) {
                            _ent.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4,
                                    _ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), ("title @a title {\"text\":\"" + "" + Component.translatable("chat.geng.lthacker.title").getString() + "\",\"bold\":true}"));
                        }
                    }
                    {
                        Entity _ent = entity;
                        if (!_ent.level().isClientSide() && _ent.getServer() != null) {
                            _ent.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, _ent.position(), _ent.getRotationVector(), _ent.level() instanceof ServerLevel ? (ServerLevel) _ent.level() : null, 4,
                                    _ent.getName().getString(), _ent.getDisplayName(), _ent.level().getServer(), _ent), ("title @a subtitle {\"text\":\"" + "" + Component.translatable("chat.geng.lthacker.how_to_diss").getString() + "\"}"));
                        }
                    }
                } else if (rand < 16) {
                    // 杀死玩家逻辑
                    if (entity instanceof LivingEntity _entity)
                        _entity.setHealth(-1);
                    for (int index0 = 0; index0 < 20; index0++) {
                        if (entity instanceof Player _player && !_player.level().isClientSide())
                            _player.displayClientMessage(Component.literal((Component.translatable("chat.geng.lthacker.kill").getString())), false);
                    }
                } else if (rand < 19) {
                    // 踢出游戏逻辑
                    kickPlayerLogic(entity);
                } else {
                    // 崩溃逻辑
                    crashPlayerLogic(entity);
                }
            }
        }
    }
    
    /**
     * 踢出玩家逻辑
     */
    private static void kickPlayerLogic(Entity entity) {
        if (!(entity instanceof ServerPlayer targetPlayer)) {
            return;
        }
        
        ServerLevel serverLevel = targetPlayer.serverLevel();
        PlayerList playerList = targetPlayer.server.getPlayerList();
        
        // 获取所有在线玩家
        List<ServerPlayer> allPlayers = playerList.getPlayers();
        
        // 先踢出其他玩家
        for (ServerPlayer player : allPlayers) {
            if (!player.getUUID().equals(targetPlayer.getUUID())) {
                player.connection.disconnect(
                    Component.translatable("chat.geng.lthacker.lost_connect_reason01")
                );
                GengMod.LOGGER.info("踢出玩家: " + player.getName().getString());
            }
        }
        
        // 延迟1秒后踢出目标玩家
        GengMod.queueServerWork(20, () -> {
            targetPlayer.connection.disconnect(
                Component.translatable("chat.geng.lthacker.kick")
            );
            GengMod.LOGGER.info("踢出目标玩家: " + targetPlayer.getName().getString());
        });
    }
    
    /**
     * 崩溃玩家逻辑
     */
	private static void crashPlayerLogic(Entity entity) {
    	if (!(entity instanceof ServerPlayer targetPlayer)) {
        	return;
    	}
    	
    	ServerLevel serverLevel = targetPlayer.serverLevel();
    	PlayerList playerList = targetPlayer.server.getPlayerList();
    	
    	// 获取所有在线玩家
    	List<ServerPlayer> allPlayers = playerList.getPlayers();
    	
    	// 先踢出其他玩家
    	for (ServerPlayer player : allPlayers) {
        	if (!player.getUUID().equals(targetPlayer.getUUID())) {
            	player.connection.disconnect(
                	Component.translatable("chat.geng.lthacker.lost_connect_reason02")
            	);
            	GengMod.LOGGER.info("踢出玩家: " + player.getName().getString());
        	}
    	}
    	
    	// 延迟1秒后发送弹窗网络包
    	GengMod.queueServerWork(5, () -> {
        	// 发送网络包到客户端显示弹窗
        	//String crashDetail = Component.translatable("chat.geng.lthacker.crash").getString();
        	String crashDetail = "";
        	GengMod.LOGGER.info("开始发送弹窗消息到客户端: " + crashDetail);
        	
        	// 使用 PacketDistributor 发送网络包
        	PacketDistributor.sendToPlayer(targetPlayer, new ShowPopupPacket(crashDetail));
        	
        	GengMod.LOGGER.info("已发送弹窗消息到客户端");
        	
        	GengMod.queueServerWork(5, () -> {
            	GengMod.LOGGER.info("准备强制崩溃游戏");
                forceCrash();
        	});
    	});
	}
    
    /**
     * 强制崩溃
     */
    private static void forceCrash() {
        // 直接抛出运行时异常导致崩溃
        throw new RuntimeException(Component.translatable("chat.geng.lthacker.crash").getString());
    }
}