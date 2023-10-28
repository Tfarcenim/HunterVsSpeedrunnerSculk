package tfar.speedrunnervshuntersculk.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.impl.client.keybinding.KeyBindingRegistryImpl;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import tfar.speedrunnervshuntersculk.Init;

public class Client implements ClientModInitializer, ClientTickEvents.StartTick {


    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(Init.SPEEDRUNNER_SCULK_SENSOR_HEALTH, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(Init.SPEEDRUNNER_SCULK_SENSOR_REACH, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(Init.SPEEDRUNNER_SCULK_SENSOR_KNOCKBACK, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(Init.SPEEDRUNNER_SCULK_SENSOR_VOID, RenderType.cutout());

        EntityRendererRegistry.register(Init.VOID_HOLE, VoidHoleRenderer::new);
        KeyBindingHelper.registerKeyBinding(ModKeybinds.VOID_HOLE);
        KeyBindingHelper.registerKeyBinding(ModKeybinds.TIME_PAUSE);
        ClientTickEvents.START_CLIENT_TICK.register(this);
        CommonClient.clientSetup();
    }


    @Override
    public void onStartTick(Minecraft client) {
        CommonClient.clientTick(client);
    }
}
