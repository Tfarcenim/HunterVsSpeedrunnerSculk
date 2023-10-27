package tfar.speedrunnervshuntersculk.client;

import net.minecraft.client.Minecraft;
import tfar.speedrunnervshuntersculk.Init;
import tfar.speedrunnervshuntersculk.platform.Services;

public class CommonClient {

    public static boolean onScroll(Minecraft minecraft, double delta) {
        if (minecraft.player != null &&minecraft.player.isCrouching() && minecraft.player.getMainHandItem().getItem() == Init.SPEEDRUNNER_SCULK_SENSOR_I) {
            Services.PLATFORM.sendScrollPacket(delta < 0);
            return true;
        }
        return false;
    }

    public static void clientTick(Minecraft minecraft) {
        while (ModKeybinds.VOID_HOLE.consumeClick()) {
            Services.PLATFORM.sendKeybindPacket();
        }
    }
}
