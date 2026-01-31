package net.mcreator.geng.network;

import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.chat.Component;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.HolderLookup;

import net.mcreator.geng.GengMod;

import java.util.function.Supplier;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class GengModVariables {
	public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, GengMod.MODID);
	public static final Supplier<AttachmentType<PlayerVariables>> PLAYER_VARIABLES = ATTACHMENT_TYPES.register("player_variables", () -> AttachmentType.serializable(() -> new PlayerVariables()).build());

	@SubscribeEvent
	public static void init(FMLCommonSetupEvent event) {
		GengMod.addNetworkMessage(PlayerVariablesSyncMessage.TYPE, PlayerVariablesSyncMessage.STREAM_CODEC, PlayerVariablesSyncMessage::handleData);
	}

	@EventBusSubscriber
	public static class EventBusVariableHandlers {
		@SubscribeEvent
		public static void onPlayerLoggedInSyncPlayerVariables(PlayerEvent.PlayerLoggedInEvent event) {
			if (event.getEntity() instanceof ServerPlayer player)
				player.getData(PLAYER_VARIABLES).syncPlayerVariables(event.getEntity());
		}

		@SubscribeEvent
		public static void onPlayerRespawnedSyncPlayerVariables(PlayerEvent.PlayerRespawnEvent event) {
			if (event.getEntity() instanceof ServerPlayer player)
				player.getData(PLAYER_VARIABLES).syncPlayerVariables(event.getEntity());
		}

		@SubscribeEvent
		public static void onPlayerChangedDimensionSyncPlayerVariables(PlayerEvent.PlayerChangedDimensionEvent event) {
			if (event.getEntity() instanceof ServerPlayer player)
				player.getData(PLAYER_VARIABLES).syncPlayerVariables(event.getEntity());
		}

		@SubscribeEvent
		public static void clonePlayer(PlayerEvent.Clone event) {
			PlayerVariables original = event.getOriginal().getData(PLAYER_VARIABLES);
			PlayerVariables clone = new PlayerVariables();
			clone.uuid01 = original.uuid01;
			clone.txt02 = original.txt02;
			clone.SilverDollarNum = original.SilverDollarNum;
			clone.randomPxx = original.randomPxx;
			clone.PxxHitNum = original.PxxHitNum;
			clone.PutinMode = original.PutinMode;
			clone.num6 = original.num6;
			clone.num2 = original.num2;
			clone.InLottery = original.InLottery;
			clone.InChoiceGui = original.InChoiceGui;
			clone.CopperCoinNum = original.CopperCoinNum;
			clone.choose = original.choose;
			clone.bargaining_progress_display = original.bargaining_progress_display;
			clone.bargaining_progress = original.bargaining_progress;
			clone.AlreadyGetCoins = original.AlreadyGetCoins;
			clone.headgear_eff_display = original.headgear_eff_display;
			clone.polar_bear_x = original.polar_bear_x;
			clone.polar_bear_y = original.polar_bear_y;
			clone.polar_bear_z = original.polar_bear_z;
			clone.max_y_speed = original.max_y_speed;
			clone.JiahaoMusicTick = original.JiahaoMusicTick;
			clone.inf_by_hackers = original.inf_by_hackers;
			if (!event.isWasDeath()) {
				clone.ddm_sound_tick = original.ddm_sound_tick;
			}
			event.getEntity().setData(PLAYER_VARIABLES, clone);
		}
	}

	public static class PlayerVariables implements INBTSerializable<CompoundTag> {
		public String uuid01 = "";
		public String txt02 = "";
		public double SilverDollarNum = 0;
		public double randomPxx = 0.0;
		public double PxxHitNum = 0.0;
		public String PutinMode = "reset";
		public double num6 = 0.0;
		public double num2 = 0.0;
		public boolean InLottery = false;
		public boolean InChoiceGui = false;
		public double CopperCoinNum = 0;
		public boolean choose = false;
		public boolean bargaining_progress_display = false;
		public double bargaining_progress = 0.0;
		public boolean AlreadyGetCoins = false;
		public double ddm_sound_tick = 0;
		public boolean headgear_eff_display = false;
		public double polar_bear_x = 0;
		public double polar_bear_y = 0;
		public double polar_bear_z = 0;
		public double max_y_speed = 0.0;
		public double JiahaoMusicTick = 0;
		public boolean inf_by_hackers = false;

		@Override
		public CompoundTag serializeNBT(HolderLookup.Provider lookupProvider) {
			CompoundTag nbt = new CompoundTag();
			nbt.putString("uuid01", uuid01);
			nbt.putString("txt02", txt02);
			nbt.putDouble("SilverDollarNum", SilverDollarNum);
			nbt.putDouble("randomPxx", randomPxx);
			nbt.putDouble("PxxHitNum", PxxHitNum);
			nbt.putString("PutinMode", PutinMode);
			nbt.putDouble("num6", num6);
			nbt.putDouble("num2", num2);
			nbt.putBoolean("InLottery", InLottery);
			nbt.putBoolean("InChoiceGui", InChoiceGui);
			nbt.putDouble("CopperCoinNum", CopperCoinNum);
			nbt.putBoolean("choose", choose);
			nbt.putBoolean("bargaining_progress_display", bargaining_progress_display);
			nbt.putDouble("bargaining_progress", bargaining_progress);
			nbt.putBoolean("AlreadyGetCoins", AlreadyGetCoins);
			nbt.putDouble("ddm_sound_tick", ddm_sound_tick);
			nbt.putBoolean("headgear_eff_display", headgear_eff_display);
			nbt.putDouble("polar_bear_x", polar_bear_x);
			nbt.putDouble("polar_bear_y", polar_bear_y);
			nbt.putDouble("polar_bear_z", polar_bear_z);
			nbt.putDouble("max_y_speed", max_y_speed);
			nbt.putDouble("JiahaoMusicTick", JiahaoMusicTick);
			nbt.putBoolean("inf_by_hackers", inf_by_hackers);
			return nbt;
		}

		@Override
		public void deserializeNBT(HolderLookup.Provider lookupProvider, CompoundTag nbt) {
			uuid01 = nbt.getString("uuid01");
			txt02 = nbt.getString("txt02");
			SilverDollarNum = nbt.getDouble("SilverDollarNum");
			randomPxx = nbt.getDouble("randomPxx");
			PxxHitNum = nbt.getDouble("PxxHitNum");
			PutinMode = nbt.getString("PutinMode");
			num6 = nbt.getDouble("num6");
			num2 = nbt.getDouble("num2");
			InLottery = nbt.getBoolean("InLottery");
			InChoiceGui = nbt.getBoolean("InChoiceGui");
			CopperCoinNum = nbt.getDouble("CopperCoinNum");
			choose = nbt.getBoolean("choose");
			bargaining_progress_display = nbt.getBoolean("bargaining_progress_display");
			bargaining_progress = nbt.getDouble("bargaining_progress");
			AlreadyGetCoins = nbt.getBoolean("AlreadyGetCoins");
			ddm_sound_tick = nbt.getDouble("ddm_sound_tick");
			headgear_eff_display = nbt.getBoolean("headgear_eff_display");
			polar_bear_x = nbt.getDouble("polar_bear_x");
			polar_bear_y = nbt.getDouble("polar_bear_y");
			polar_bear_z = nbt.getDouble("polar_bear_z");
			max_y_speed = nbt.getDouble("max_y_speed");
			JiahaoMusicTick = nbt.getDouble("JiahaoMusicTick");
			inf_by_hackers = nbt.getBoolean("inf_by_hackers");
		}

		public void syncPlayerVariables(Entity entity) {
			if (entity instanceof ServerPlayer serverPlayer)
				PacketDistributor.sendToPlayer(serverPlayer, new PlayerVariablesSyncMessage(this));
		}
	}

	public record PlayerVariablesSyncMessage(PlayerVariables data) implements CustomPacketPayload {
		public static final Type<PlayerVariablesSyncMessage> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(GengMod.MODID, "player_variables_sync"));
		public static final StreamCodec<RegistryFriendlyByteBuf, PlayerVariablesSyncMessage> STREAM_CODEC = StreamCodec
				.of((RegistryFriendlyByteBuf buffer, PlayerVariablesSyncMessage message) -> buffer.writeNbt(message.data().serializeNBT(buffer.registryAccess())), (RegistryFriendlyByteBuf buffer) -> {
					PlayerVariablesSyncMessage message = new PlayerVariablesSyncMessage(new PlayerVariables());
					message.data.deserializeNBT(buffer.registryAccess(), buffer.readNbt());
					return message;
				});

		@Override
		public Type<PlayerVariablesSyncMessage> type() {
			return TYPE;
		}

		public static void handleData(final PlayerVariablesSyncMessage message, final IPayloadContext context) {
			if (context.flow() == PacketFlow.CLIENTBOUND && message.data != null) {
				context.enqueueWork(() -> context.player().getData(PLAYER_VARIABLES).deserializeNBT(context.player().registryAccess(), message.data.serializeNBT(context.player().registryAccess()))).exceptionally(e -> {
					context.connection().disconnect(Component.literal(e.getMessage()));
					return null;
				});
			}
		}
	}
}