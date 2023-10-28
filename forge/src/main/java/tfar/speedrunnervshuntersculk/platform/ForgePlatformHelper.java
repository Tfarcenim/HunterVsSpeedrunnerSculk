package tfar.speedrunnervshuntersculk.platform;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraftforge.common.ForgeMod;
import tfar.speedrunnervshuntersculk.KeyAction;
import tfar.speedrunnervshuntersculk.platform.services.IPlatformHelper;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;

public class ForgePlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {

        return "Forge";
    }

    @Override
    public boolean isModLoaded(String modId) {

        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return !FMLLoader.isProduction();
    }

    @Override
    public void sendScrollPacket(boolean up) {

    }

    @Override
    public void sendKeybindPacket(KeyAction action) {

    }

    @Override
    public void stopTime(ServerPlayer player) {

    }

    @Override
    public Attribute getBlockReachAttribute() {
        return ForgeMod.BLOCK_REACH.get();
    }

    @Override
    public Attribute getEntityReachAttribute() {
        return ForgeMod.ENTITY_REACH.get();
    }
}