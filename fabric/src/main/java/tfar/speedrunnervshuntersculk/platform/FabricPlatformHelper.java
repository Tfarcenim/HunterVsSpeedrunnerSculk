package tfar.speedrunnervshuntersculk.platform;

import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import net.minecraft.world.entity.ai.attributes.Attribute;
import tfar.speedrunnervshuntersculk.network.ClientPacketHandler;
import tfar.speedrunnervshuntersculk.platform.services.IPlatformHelper;
import net.fabricmc.loader.api.FabricLoader;

public class FabricPlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public boolean isModLoaded(String modId) {

        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public void sendScrollPacket(boolean up) {
        ClientPacketHandler.sendScroll(up);
    }

    @Override
    public void sendKeybindPacket() {
        ClientPacketHandler.sendKeyBind();
    }

    @Override
    public Attribute getBlockReachAttribute() {
        return ReachEntityAttributes.REACH;
    }

    @Override
    public Attribute getEntityReachAttribute() {
        return ReachEntityAttributes.ATTACK_RANGE;
    }
}
