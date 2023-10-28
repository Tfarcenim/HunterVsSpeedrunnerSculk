package tfar.speedrunnervshuntersculk.network;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import tfar.speedrunnervshuntersculk.KeyAction;
import tfar.speedrunnervshuntersculk.SpeedrunnerVsHunterSculk;
import tfar.speedrunnervshuntersculk.Utils;

public class PacketHandler {
    public static final ResourceLocation scroll = new ResourceLocation(SpeedrunnerVsHunterSculk.MOD_ID, "scroll");
    public static final ResourceLocation keybind = new ResourceLocation(SpeedrunnerVsHunterSculk.MOD_ID, "keybind");

    public static void registerMessages() {
        ServerPlayNetworking.registerGlobalReceiver(scroll, PacketHandler::receiveScroll);
        ServerPlayNetworking.registerGlobalReceiver(keybind,PacketHandler::receiveButton);
    }



    static void receiveScroll(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl handler, FriendlyByteBuf buf, PacketSender responseSender) {
        boolean right = buf.readBoolean();
        server.execute(() -> Utils.changeMode(player,right));
    }

    static void receiveButton(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl handler, FriendlyByteBuf buf, PacketSender responseSender) {
        KeyAction action = KeyAction.values()[buf.readInt()];
        server.execute(() -> Utils.handleKeybind(player,action));
    }
}
