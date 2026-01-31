package net.mcreator.geng;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.network.handling.IPayloadHandler;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.fml.util.thread.SidedThreadGroups;
import net.neoforged.fml.common.Mod;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.IEventBus;

import net.minecraft.util.Tuple;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.chat.Component;
import net.minecraft.network.FriendlyByteBuf;

import net.mcreator.geng.network.GengModVariables;
import net.mcreator.geng.init.GengModVillagerProfessions;
import net.mcreator.geng.init.GengModTabs;
import net.mcreator.geng.init.GengModSounds;
import net.mcreator.geng.init.GengModPotions;
import net.mcreator.geng.init.GengModMobEffects;
import net.mcreator.geng.init.GengModMenus;
import net.mcreator.geng.init.GengModItems;
import net.mcreator.geng.init.GengModEntities;
import net.mcreator.geng.init.GengModBlocks;
import net.mcreator.geng.init.GengModBlockEntities;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.Collection;
import java.util.ArrayList;

@Mod("geng")
public class GengMod {
	public static final Logger LOGGER = LogManager.getLogger(GengMod.class);
	public static final String MODID = "geng";

	public GengMod(IEventBus modEventBus) {
		// Start of user code block mod constructor
		GengMod.LOGGER.info("GengMod 初始化开始");
		// 注册网络包
		GengMod.addNetworkMessage(net.mcreator.geng.network.ShowPopupPacket.TYPE, net.mcreator.geng.network.ShowPopupPacket.STREAM_CODEC, (payload, context) -> {
			GengMod.LOGGER.info("收到弹窗网络包: " + payload.message());
			// 使用 enqueueWork 在主线程执行
			context.enqueueWork(() -> {
				GengMod.LOGGER.info("开始处理弹窗逻辑");
				if (context.player() != null && context.player().level().isClientSide()) {
					GengMod.LOGGER.info("在客户端执行弹窗");
					// 判断是否是测试模式（消息包含"测试"字样）
					boolean isTestMode = payload.message().contains("测试");
					try {
						// 使用Minecraft原生Screen（最可靠）
						net.minecraft.client.Minecraft.getInstance().execute(() -> {
							net.minecraft.client.Minecraft.getInstance().setScreen(new net.mcreator.geng.client.gui.HackerAttackScreen(payload.message(), isTestMode));
						});
						GengMod.LOGGER.info("Minecraft弹窗Screen显示成功");
					} catch (Exception e) {
						GengMod.LOGGER.error("显示Minecraft弹窗失败: ", e);
						// 备选方案：使用聊天消息
						if (context.player() != null) {
							net.minecraft.network.chat.Component message = net.minecraft.network.chat.Component.literal(Component.translatable("chat.geng.lthacker.crash").getString() + "\n" + payload.message());
							context.player().displayClientMessage(message, false);
							GengMod.LOGGER.info("已使用聊天消息替代弹窗");
						}
					}
				} else {
					GengMod.LOGGER.warn("不是客户端环境或玩家不存在");
				}
			});
		});
		// End of user code block mod constructor
		NeoForge.EVENT_BUS.register(this);
		modEventBus.addListener(this::registerNetworking);
		GengModSounds.REGISTRY.register(modEventBus);
		GengModBlocks.REGISTRY.register(modEventBus);
		GengModBlockEntities.REGISTRY.register(modEventBus);
		GengModItems.REGISTRY.register(modEventBus);
		GengModEntities.REGISTRY.register(modEventBus);
		GengModTabs.REGISTRY.register(modEventBus);
		GengModVariables.ATTACHMENT_TYPES.register(modEventBus);
		GengModPotions.REGISTRY.register(modEventBus);
		GengModMobEffects.REGISTRY.register(modEventBus);
		GengModMenus.REGISTRY.register(modEventBus);
		GengModVillagerProfessions.PROFESSIONS.register(modEventBus);
		// Start of user code block mod init
		// 注册村民礼物处理器
		NeoForge.EVENT_BUS.register(net.mcreator.geng.event.VillagerGiftHandler.class);
		// End of user code block mod init
	}

	// Start of user code block mod methods
	@SubscribeEvent
	public void onRegisterCommands(RegisterCommandsEvent event) {
		GengMod.LOGGER.info("注册测试命令");
		net.mcreator.geng.command.TestPopupCommand.register(event.getDispatcher());
	}

	// End of user code block mod methods
	private static boolean networkingRegistered = false;
	private static final Map<CustomPacketPayload.Type<?>, NetworkMessage<?>> MESSAGES = new HashMap<>();

	private record NetworkMessage<T extends CustomPacketPayload>(StreamCodec<? extends FriendlyByteBuf, T> reader, IPayloadHandler<T> handler) {
	}

	public static <T extends CustomPacketPayload> void addNetworkMessage(CustomPacketPayload.Type<T> id, StreamCodec<? extends FriendlyByteBuf, T> reader, IPayloadHandler<T> handler) {
		if (networkingRegistered)
			throw new IllegalStateException("Cannot register new network messages after networking has been registered");
		MESSAGES.put(id, new NetworkMessage<>(reader, handler));
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	private void registerNetworking(final RegisterPayloadHandlersEvent event) {
		final PayloadRegistrar registrar = event.registrar(MODID);
		MESSAGES.forEach((id, networkMessage) -> registrar.playBidirectional(id, ((NetworkMessage) networkMessage).reader(), ((NetworkMessage) networkMessage).handler()));
		networkingRegistered = true;
	}

	private static final Collection<Tuple<Runnable, Integer>> workQueue = new ConcurrentLinkedQueue<>();

	public static void queueServerWork(int tick, Runnable action) {
		if (Thread.currentThread().getThreadGroup() == SidedThreadGroups.SERVER)
			workQueue.add(new Tuple<>(action, tick));
	}

	@SubscribeEvent
	public void tick(ServerTickEvent.Post event) {
		List<Tuple<Runnable, Integer>> actions = new ArrayList<>();
		workQueue.forEach(work -> {
			work.setB(work.getB() - 1);
			if (work.getB() == 0)
				actions.add(work);
		});
		actions.forEach(e -> e.getA().run());
		workQueue.removeAll(actions);
	}
}