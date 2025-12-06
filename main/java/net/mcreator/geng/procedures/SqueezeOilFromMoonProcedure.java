package net.mcreator.geng.procedures;

import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;
import net.neoforged.api.distmarker.Dist;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.chat.Component;
import net.minecraft.network.RegistryFriendlyByteBuf;

import net.mcreator.geng.GengMod;

import javax.annotation.Nullable;

@EventBusSubscriber(value = {Dist.CLIENT})
public class SqueezeOilFromMoonProcedure {
    @SubscribeEvent
    public static void onRightClick(PlayerInteractEvent.RightClickEmpty event) {
        PacketDistributor.sendToServer(new SqueezeOilFromMoonMessage());
        execute(event.getLevel(), event.getEntity());
    }

    @EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
    public record SqueezeOilFromMoonMessage() implements CustomPacketPayload {
        public static final Type<SqueezeOilFromMoonMessage> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(GengMod.MODID, "procedure_squeeze_oil_from_moon"));
        public static final StreamCodec<RegistryFriendlyByteBuf, SqueezeOilFromMoonMessage> STREAM_CODEC = StreamCodec.of((RegistryFriendlyByteBuf buffer, SqueezeOilFromMoonMessage message) -> {
        }, (RegistryFriendlyByteBuf buffer) -> new SqueezeOilFromMoonMessage());

        @Override
        public Type<SqueezeOilFromMoonMessage> type() {
            return TYPE;
        }

        public static void handleData(final SqueezeOilFromMoonMessage message, final IPayloadContext context) {
            if (context.flow() == PacketFlow.SERVERBOUND) {
                context.enqueueWork(() -> {
                    if (!context.player().level().hasChunkAt(context.player().blockPosition()))
                        return;
                    execute(context.player().level(), context.player());
                }).exceptionally(e -> {
                    context.connection().disconnect(Component.literal(e.getMessage()));
                    return null;
                });
            }
        }

        @SubscribeEvent
        public static void registerMessage(FMLCommonSetupEvent event) {
            GengMod.addNetworkMessage(SqueezeOilFromMoonMessage.TYPE, SqueezeOilFromMoonMessage.STREAM_CODEC, SqueezeOilFromMoonMessage::handleData);
        }
    }

    public static void execute(LevelAccessor world, Entity entity) {
        execute(null, world, entity);
    }

    private static void execute(@Nullable Event event, LevelAccessor world, Entity entity) {
        if (entity == null)
            return;
        
        long t = world.dayTime() % 24000;
        //double i = Math.min(t - 12000, 24000 - t) / 2400;
        //double allowedDeviation = 3.7 + i;
        double i = Math.min(t - 12000, 24000 - t);
        double allowedDeviation = 5 + i / (2070 - (i / 3));
        
        double pitch = entity.getXRot();
        double yaw = entity.getYRot();
        
        //double moonPitch = 90 * Math.sin(t * Math.PI / 12000);
        //double moonPitch = -90 + Math.abs(t - 18000) / 520 * 9;
        double moonPitch = (90.0 / 5150.0) * Math.abs(t - 18000) - 90.0;
        double moonYaw = 400;
        boolean MoonOnHead = false;
        if (t < (17700)) {
				moonYaw = 270;
        	} else if (t < (18300)) {
        		MoonOnHead = true;
        	} else {
        		moonYaw = 90;
        	}
        
        double normalizedYaw = (yaw % 360 + 360) % 360;
        
        if ((MoonOnHead || Math.abs(normalizedYaw - moonYaw) < allowedDeviation) && Math.abs(pitch - moonPitch) < 5) {
        	if (t > 12800 && t < 23200) {
            	GetOilProcedure.execute(world, entity.getX(), entity.getY(), entity.getZ(), entity);
        	}
        }
    }
}