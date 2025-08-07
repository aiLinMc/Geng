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
			clone.z02 = original.z02;
			clone.z01 = original.z01;
			clone.y02 = original.y02;
			clone.y01 = original.y01;
			clone.x02 = original.x02;
			clone.x01 = original.x01;
			clone.uuid01 = original.uuid01;
			clone.txt02 = original.txt02;
			clone.txt01 = original.txt01;
			clone.Sweet = original.Sweet;
			clone.SilverDollarNum = original.SilverDollarNum;
			clone.randomPxx = original.randomPxx;
			clone.random1 = original.random1;
			clone.PxxHitNum = original.PxxHitNum;
			clone.PutinMode = original.PutinMode;
			clone.num7 = original.num7;
			clone.num6 = original.num6;
			clone.num5 = original.num5;
			clone.num4 = original.num4;
			clone.num3 = original.num3;
			clone.num2 = original.num2;
			clone.num1 = original.num1;
			clone.lie = original.lie;
			clone.InLottery = original.InLottery;
			clone.InChoiceGui = original.InChoiceGui;
			clone.CopperCoinNum = original.CopperCoinNum;
			clone.choose = original.choose;
			clone.bargaining_progress_display = original.bargaining_progress_display;
			clone.bargaining_progress = original.bargaining_progress;
			clone.AlreadyGetCoins = original.AlreadyGetCoins;
			clone.headgear_eff_display = original.headgear_eff_display;
			if (!event.isWasDeath()) {
				clone.ddm_sound_tick = original.ddm_sound_tick;
			}
			event.getEntity().setData(PLAYER_VARIABLES, clone);
		}
	}

	public static class PlayerVariables implements INBTSerializable<CompoundTag> {
		public double z02 = 0.0;
		public double z01 = 0.0;
		public double y02 = 0.0;
		public double y01 = 0.0;
		public double x02 = 0.0;
		public double x01 = 0.0;
		public String uuid01 = "";
		public String txt02 = "";
		public String txt01 = "";
		public double Sweet = 0.0;
		public double SilverDollarNum = 0;
		public double randomPxx = 0.0;
		public double random1 = 0.0;
		public double PxxHitNum = 0.0;
		public String PutinMode = "reset";
		public double num7 = 0;
		public double num6 = 0.0;
		public double num5 = 0.0;
		public double num4 = 0.0;
		public double num3 = 0.0;
		public double num2 = 0.0;
		public double num1 = 0.0;
		public boolean lie = false;
		public boolean InLottery = false;
		public boolean InChoiceGui = false;
		public double CopperCoinNum = 0;
		public boolean choose = false;
		public boolean bargaining_progress_display = false;
		public double bargaining_progress = 0.0;
		public boolean AlreadyGetCoins = false;
		public double ddm_sound_tick = 0;
		public boolean headgear_eff_display = false;

		@Override
		public CompoundTag serializeNBT(HolderLookup.Provider lookupProvider) {
			CompoundTag nbt = new CompoundTag();
			nbt.putDouble("z02", z02);
			nbt.putDouble("z01", z01);
			nbt.putDouble("y02", y02);
			nbt.putDouble("y01", y01);
			nbt.putDouble("x02", x02);
			nbt.putDouble("x01", x01);
			nbt.putString("uuid01", uuid01);
			nbt.putString("txt02", txt02);
			nbt.putString("txt01", txt01);
			nbt.putDouble("Sweet", Sweet);
			nbt.putDouble("SilverDollarNum", SilverDollarNum);
			nbt.putDouble("randomPxx", randomPxx);
			nbt.putDouble("random1", random1);
			nbt.putDouble("PxxHitNum", PxxHitNum);
			nbt.putString("PutinMode", PutinMode);
			nbt.putDouble("num7", num7);
			nbt.putDouble("num6", num6);
			nbt.putDouble("num5", num5);
			nbt.putDouble("num4", num4);
			nbt.putDouble("num3", num3);
			nbt.putDouble("num2", num2);
			nbt.putDouble("num1", num1);
			nbt.putBoolean("lie", lie);
			nbt.putBoolean("InLottery", InLottery);
			nbt.putBoolean("InChoiceGui", InChoiceGui);
			nbt.putDouble("CopperCoinNum", CopperCoinNum);
			nbt.putBoolean("choose", choose);
			nbt.putBoolean("bargaining_progress_display", bargaining_progress_display);
			nbt.putDouble("bargaining_progress", bargaining_progress);
			nbt.putBoolean("AlreadyGetCoins", AlreadyGetCoins);
			nbt.putDouble("ddm_sound_tick", ddm_sound_tick);
			nbt.putBoolean("headgear_eff_display", headgear_eff_display);
			return nbt;
		}

		@Override
		public void deserializeNBT(HolderLookup.Provider lookupProvider, CompoundTag nbt) {
			z02 = nbt.getDouble("z02");
			z01 = nbt.getDouble("z01");
			y02 = nbt.getDouble("y02");
			y01 = nbt.getDouble("y01");
			x02 = nbt.getDouble("x02");
			x01 = nbt.getDouble("x01");
			uuid01 = nbt.getString("uuid01");
			txt02 = nbt.getString("txt02");
			txt01 = nbt.getString("txt01");
			Sweet = nbt.getDouble("Sweet");
			SilverDollarNum = nbt.getDouble("SilverDollarNum");
			randomPxx = nbt.getDouble("randomPxx");
			random1 = nbt.getDouble("random1");
			PxxHitNum = nbt.getDouble("PxxHitNum");
			PutinMode = nbt.getString("PutinMode");
			num7 = nbt.getDouble("num7");
			num6 = nbt.getDouble("num6");
			num5 = nbt.getDouble("num5");
			num4 = nbt.getDouble("num4");
			num3 = nbt.getDouble("num3");
			num2 = nbt.getDouble("num2");
			num1 = nbt.getDouble("num1");
			lie = nbt.getBoolean("lie");
			InLottery = nbt.getBoolean("InLottery");
			InChoiceGui = nbt.getBoolean("InChoiceGui");
			CopperCoinNum = nbt.getDouble("CopperCoinNum");
			choose = nbt.getBoolean("choose");
			bargaining_progress_display = nbt.getBoolean("bargaining_progress_display");
			bargaining_progress = nbt.getDouble("bargaining_progress");
			AlreadyGetCoins = nbt.getBoolean("AlreadyGetCoins");
			ddm_sound_tick = nbt.getDouble("ddm_sound_tick");
			headgear_eff_display = nbt.getBoolean("headgear_eff_display");
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