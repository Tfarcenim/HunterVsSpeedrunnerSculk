package tfar.speedrunnervshuntersculk.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.RenderType;
import tfar.speedrunnervshuntersculk.Init;

public class Client implements ClientModInitializer {


    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(Init.SPEEDRUNNER_SCULK_SENSOR, RenderType.cutout());
    }

}
