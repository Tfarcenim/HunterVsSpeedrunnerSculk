package tfar.speedrunnervshuntersculk.network;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;

public class ClientPacketHandler extends PacketHandler{

    public static void registerClientMessages() {

    }

    public static void sendScroll(boolean right) {
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        buf.writeBoolean(right);
        ClientPlayNetworking.send(scroll, buf);
    }
}
