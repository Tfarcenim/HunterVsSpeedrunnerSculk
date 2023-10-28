package tfar.speedrunnervshuntersculk.client;

import net.minecraft.client.Minecraft;
import tfar.speedrunnervshuntersculk.Init;
import tfar.speedrunnervshuntersculk.KeyAction;
import tfar.speedrunnervshuntersculk.TimeLimitedBlockItem;
import tfar.speedrunnervshuntersculk.platform.Services;

public class CommonClient {

    public static boolean onScroll(Minecraft minecraft, double delta) {
        if (minecraft.player != null &&minecraft.player.isCrouching() && minecraft.player.getMainHandItem().getItem() instanceof TimeLimitedBlockItem) {
            Services.PLATFORM.sendScrollPacket(delta < 0);
            return true;
        }
        return false;
    }

    public static void clientTick(Minecraft minecraft) {
        while (ModKeybinds.VOID_HOLE.consumeClick()) {
            Services.PLATFORM.sendKeybindPacket(KeyAction.VOID_HOLE);
        }
        while (ModKeybinds.TIME_PAUSE.consumeClick()) {
            Services.PLATFORM.sendKeybindPacket(KeyAction.TIME_PAUSE);
        }
    }

    public static void clientSetup() {
    }
}
