package tfar.speedrunnervshuntersculk;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import tfar.speedrunnervshuntersculk.network.PacketHandler;

public class SpeedrunnerVsHunterSculkFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        
        // This method is invoked by the Fabric mod loader when it is ready
        // to load your mod. You can access Fabric and Common code in this
        // project.

        // Use Fabric to bootstrap the Common mod.
        register();
        SpeedrunnerVsHunterSculk.init();
        CommandRegistrationCallback.EVENT.register(this::registerCommands);
        PacketHandler.registerMessages();
    }

    private void registerCommands(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext registryAccess, Commands.CommandSelection environment) {
        ModCommand.register(dispatcher);
    }

    private void register() {
        Registry.register(BuiltInRegistries.BLOCK,new ResourceLocation(SpeedrunnerVsHunterSculk.MOD_ID,"speedrunner_sculk_sensor"),Init.SPEEDRUNNER_SCULK_SENSOR);
        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE,new ResourceLocation(SpeedrunnerVsHunterSculk.MOD_ID,"speedrunner_sculk_sensor"),Init.SPEEDRUNNER_SCULK_SENSOR_E);
        Registry.register(BuiltInRegistries.ITEM,new ResourceLocation(SpeedrunnerVsHunterSculk.MOD_ID,"speedrunner_sculk_sensor"),Init.SPEEDRUNNER_SCULK_SENSOR_I);
        Registry.register(BuiltInRegistries.ENTITY_TYPE,new ResourceLocation(SpeedrunnerVsHunterSculk.MOD_ID,"void_hole"),Init.VOID_HOLE);
    }
}
