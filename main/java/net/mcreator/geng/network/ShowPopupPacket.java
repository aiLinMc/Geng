package net.mcreator.geng.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record ShowPopupPacket(String message) implements CustomPacketPayload {
    public static final Type<ShowPopupPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("geng", "show_popup"));
    
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
    
    // 修复 StreamCodec - 使用文档中的方式
    public static final StreamCodec<FriendlyByteBuf, ShowPopupPacket> STREAM_CODEC = StreamCodec.of(
        (buf, packet) -> buf.writeUtf(packet.message, 32767),
        buf -> new ShowPopupPacket(buf.readUtf(32767))
    );
}