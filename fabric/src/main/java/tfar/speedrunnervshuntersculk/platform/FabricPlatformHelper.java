package tfar.speedrunnervshuntersculk.platform;

import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attribute;
import tfar.speedrunnervshuntersculk.KeyAction;
import tfar.speedrunnervshuntersculk.SpeedrunnerVsHunterSculkFabric;
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
    public void sendKeybindPacket(KeyAction action) {
        ClientPacketHandler.sendKeyBind(action);
    }

    @Override
    public Attribute getBlockReachAttribute() {
        return ReachEntityAttributes.REACH;
    }

    @Override
    public void stopTime(ServerPlayer player) {
        SpeedrunnerVsHunterSculkFabric.stopTime(player);
    }

    @Override
    public Attribute getEntityReachAttribute() {
        return ReachEntityAttributes.ATTACK_RANGE;
    }
}
