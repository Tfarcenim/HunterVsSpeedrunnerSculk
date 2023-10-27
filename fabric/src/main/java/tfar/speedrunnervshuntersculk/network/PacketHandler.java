package tfar.speedrunnervshuntersculk.network;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import tfar.speedrunnervshuntersculk.SpeedrunnerVsHunterSculk;
import tfar.speedrunnervshuntersculk.Utils;

public class PacketHandler {
    public static final ResourceLocation scroll = new ResourceLocation(SpeedrunnerVsHunterSculk.MOD_ID, "scroll");

    public static void registerMessages() {
        ServerPlayNetworking.registerGlobalReceiver(scroll, PacketHandler::receiveScroll);
    }



    static void receiveScroll(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl handler, FriendlyByteBuf buf, PacketSender responseSender) {
        boolean right = buf.readBoolean();
        server.execute(() -> Utils.changeMode(player.getMainHandItem(),right));
    }
}
