package tfar.speedrunnervshuntersculk.client;

import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;
import tfar.speedrunnervshuntersculk.SpeedrunnerVsHunterSculk;

public class ModKeybinds {

    public static final KeyMapping VOID_HOLE = new KeyMapping("key.void_hole", GLFW.GLFW_KEY_V,"categories."+ SpeedrunnerVsHunterSculk.MOD_ID);
    public static final KeyMapping TIME_PAUSE = new KeyMapping("key.time_pause", GLFW.GLFW_KEY_P,"categories."+ SpeedrunnerVsHunterSculk.MOD_ID);

}
