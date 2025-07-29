package net.mcreator.geng.network;

import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.chat.Component;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.core.BlockPos;

import net.mcreator.geng.procedures.GuiCloseProcedure;
import net.mcreator.geng.GengMod;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public record LieDetectorLieButtonMessage(int buttonID, int x, int y, int z) implements CustomPacketPayload {

	public static final Type<LieDetectorLieButtonMessage> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(GengMod.MODID, "lie_detector_lie_buttons"));
	public static final StreamCodec<RegistryFriendlyByteBuf, LieDetectorLieButtonMessage> STREAM_CODEC = StreamCodec.of((RegistryFriendlyByteBuf buffer, LieDetectorLieButtonMessage message) -> {
		buffer.writeInt(message.buttonID);
		buffer.writeInt(message.x);
		buffer.writeInt(message.y);
		buffer.writeInt(message.z);
	}, (RegistryFriendlyByteBuf buffer) -> new LieDetectorLieButtonMessage(buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt()));
	@Override
	public Type<LieDetectorLieButtonMessage> type() {
		return TYPE;
	}

	public static void handleData(final LieDetectorLieButtonMessage message, final IPayloadContext context) {
		if (context.flow() == PacketFlow.SERVERBOUND) {
			context.enqueueWork(() -> handleButtonAction(context.player(), message.buttonID, message.x, message.y, message.z)).exceptionally(e -> {
				context.connection().disconnect(Component.literal(e.getMessage()));
				return null;
			});
		}
	}

	public static void handleButtonAction(Player entity, int buttonID, int x, int y, int z) {
		Level world = entity.level();
		// security measure to prevent arbitrary chunk generation
		if (!world.hasChunkAt(new BlockPos(x, y, z)))
			return;
		if (buttonID == 0) {

			GuiCloseProcedure.execute(entity);
		}
	}

	@SubscribeEvent
	public static void registerMessage(FMLCommonSetupEvent event) {
		GengMod.addNetworkMessage(LieDetectorLieButtonMessage.TYPE, LieDetectorLieButtonMessage.STREAM_CODEC, LieDetectorLieButtonMessage::handleData);
	}
}