package tfar.speedrunnervshuntersculk;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegisterEvent;
import tfar.speedrunnervshuntersculk.data.Datagen;

@Mod(SpeedrunnerVsHunterSculk.MOD_ID)
public class SpeedrunnerVsHunterSculkForge {
    
    public SpeedrunnerVsHunterSculkForge() {
    
        // This method is invoked by the Forge mod loader when it is ready
        // to load your mod. You can access Forge and Common code in this
        // project.
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(Datagen::gather);
        bus.addListener(this::register);
        // Use Forge to bootstrap the Common mod.
        SpeedrunnerVsHunterSculk.init();
        MinecraftForge.EVENT_BUS.addListener(this::commands);
    }

    private void commands(RegisterCommandsEvent event) {
        ModCommand.register(event.getDispatcher());
    }

    private void register(RegisterEvent event) {
        event.register(Registries.BLOCK, new ResourceLocation(SpeedrunnerVsHunterSculk.MOD_ID, "speedrunner_sculk_sensor_health"),() -> Init.SPEEDRUNNER_SCULK_SENSOR_HEALTH);
        event.register(Registries.ITEM, new ResourceLocation(SpeedrunnerVsHunterSculk.MOD_ID, "speedrunner_sculk_sensor_health"),() -> Init.SPEEDRUNNER_SCULK_SENSOR_HEALTH_I);

        event.register(Registries.BLOCK, new ResourceLocation(SpeedrunnerVsHunterSculk.MOD_ID, "speedrunner_sculk_sensor_reach"),() -> Init.SPEEDRUNNER_SCULK_SENSOR_REACH);
        event.register(Registries.ITEM, new ResourceLocation(SpeedrunnerVsHunterSculk.MOD_ID, "speedrunner_sculk_sensor_reach"),() -> Init.SPEEDRUNNER_SCULK_SENSOR_REACH_I);

        event.register(Registries.BLOCK, new ResourceLocation(SpeedrunnerVsHunterSculk.MOD_ID, "speedrunner_sculk_sensor_knockback"),() -> Init.SPEEDRUNNER_SCULK_SENSOR_KNOCKBACK);
        event.register(Registries.ITEM, new ResourceLocation(SpeedrunnerVsHunterSculk.MOD_ID, "speedrunner_sculk_sensor_knockback"),() -> Init.SPEEDRUNNER_SCULK_SENSOR_KNOCKBACK_I);

        event.register(Registries.BLOCK, new ResourceLocation(SpeedrunnerVsHunterSculk.MOD_ID, "speedrunner_sculk_sensor_void"),() -> Init.SPEEDRUNNER_SCULK_SENSOR_VOID);
        event.register(Registries.ITEM, new ResourceLocation(SpeedrunnerVsHunterSculk.MOD_ID, "speedrunner_sculk_sensor_void"),() -> Init.SPEEDRUNNER_SCULK_SENSOR_VOID_I);
        
        event.register(Registries.BLOCK_ENTITY_TYPE,new ResourceLocation(SpeedrunnerVsHunterSculk.MOD_ID,"speedrunner_sculk_sensor"),() -> Init.SPEEDRUNNER_SCULK_SENSOR_E);
    }
}