package tfar.speedrunnervshuntersculk;

import com.mojang.brigadier.CommandDispatcher;
import io.github.suel_ki.timeclock.core.data.TimeData;
import io.github.suel_ki.timeclock.core.helper.SoundHelper;
import io.github.suel_ki.timeclock.core.networking.ModPackets;
import io.github.suel_ki.timeclock.core.networking.packets.ShaderSetupS2CPacket;
import io.github.suel_ki.timeclock.core.networking.packets.SoundManagerS2CPacket;
import io.github.suel_ki.timeclock.core.platform.NetworkPlatform;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import tfar.speedrunnervshuntersculk.network.PacketHandler;

import java.util.Iterator;
import java.util.Optional;

public class SpeedrunnerVsHunterSculkFabric implements ModInitializer, UseBlockCallback, ServerLifecycleEvents.ServerStarted {

    @Override
    public void onInitialize() {

        // This method is invoked by the Fabric mod loader when it is ready
        // to load your mod. You can access Fabric and Common code in this
        // project.

        // Use Fabric to bootstrap the Common mod.
        register();
        SpeedrunnerVsHunterSculk.init();
        CommandRegistrationCallback.EVENT.register(this::registerCommands);
        UseBlockCallback.EVENT.register(this);
        ServerLifecycleEvents.SERVER_STARTED.register(this);

        PacketHandler.registerMessages();
    }

    private void registerCommands(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext registryAccess, Commands.CommandSelection environment) {
        ModCommand.register(dispatcher);
    }

    public static void stopTime(ServerPlayer player) {
        ServerLevel serverLevel = player.serverLevel();
        Optional<? extends TimeData> dataOptional = TimeData.get(serverLevel);
        if (dataOptional.isPresent()) {
            TimeData data = dataOptional.get();
            Iterator<ServerPlayer> var11 = serverLevel.players().iterator();
            boolean pause = data.isTimePaused();
            if (pause) return;

            while (var11.hasNext()) {
                ServerPlayer serverPlayer = var11.next();
                NetworkPlatform.sendToClient(serverPlayer, new SoundManagerS2CPacket(ModPackets.SOUND_MANAGER, pause));
                NetworkPlatform.sendToClient(serverPlayer, new ShaderSetupS2CPacket(ModPackets.SHADER_SETUP, "shaders/post/desaturate.json"));
            }

            SoundEvent sound = SoundHelper.getSoundEvent();
            if (sound != SoundEvents.EMPTY) {
                serverLevel.playSound(null, player.getX(), player.getY(), player.getZ(), sound, SoundSource.PLAYERS, 1.0F, 1.0F);
            }

            //  if (config.ability_tick > 0) {
            data.addAbilityTick(20 * 20, TimeData.AbilityType.PAUSE);
            data.pauseTime(true);
            //}
        }
    }

    private void register() {
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(SpeedrunnerVsHunterSculk.MOD_ID, "speedrunner_sculk_sensor_blank"), Init.SPEEDRUNNER_SCULK_SENSOR_BLANK);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(SpeedrunnerVsHunterSculk.MOD_ID, "speedrunner_sculk_sensor_blank"), Init.SPEEDRUNNER_SCULK_SENSOR_BLANK_I);

        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(SpeedrunnerVsHunterSculk.MOD_ID, "speedrunner_sculk_sensor_health"), Init.SPEEDRUNNER_SCULK_SENSOR_HEALTH);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(SpeedrunnerVsHunterSculk.MOD_ID, "speedrunner_sculk_sensor_health"), Init.SPEEDRUNNER_SCULK_SENSOR_HEALTH_I);

        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(SpeedrunnerVsHunterSculk.MOD_ID, "speedrunner_sculk_sensor_reach"), Init.SPEEDRUNNER_SCULK_SENSOR_REACH);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(SpeedrunnerVsHunterSculk.MOD_ID, "speedrunner_sculk_sensor_reach"), Init.SPEEDRUNNER_SCULK_SENSOR_REACH_I);

        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(SpeedrunnerVsHunterSculk.MOD_ID, "speedrunner_sculk_sensor_knockback"), Init.SPEEDRUNNER_SCULK_SENSOR_KNOCKBACK);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(SpeedrunnerVsHunterSculk.MOD_ID, "speedrunner_sculk_sensor_knockback"), Init.SPEEDRUNNER_SCULK_SENSOR_KNOCKBACK_I);

        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(SpeedrunnerVsHunterSculk.MOD_ID, "speedrunner_sculk_sensor_void"), Init.SPEEDRUNNER_SCULK_SENSOR_VOID);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(SpeedrunnerVsHunterSculk.MOD_ID, "speedrunner_sculk_sensor_void"), Init.SPEEDRUNNER_SCULK_SENSOR_VOID_I);

        Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, new ResourceLocation(SpeedrunnerVsHunterSculk.MOD_ID, "speedrunner_sculk_sensor"), Init.SPEEDRUNNER_SCULK_SENSOR_E);
        Registry.register(BuiltInRegistries.ENTITY_TYPE, new ResourceLocation(SpeedrunnerVsHunterSculk.MOD_ID, "void_hole"), Init.VOID_HOLE);
    }


    //this is fired on server and client, be careful
    @Override
    public InteractionResult interact(Player player, Level world, InteractionHand hand, BlockHitResult hitResult) {
        if (player.getItemInHand(hand).isEmpty() && player.getUUID().equals(SpeedrunnerVsHunterSculk.speedrunner) && player.isCrouching()) {
            if (!world.isClientSide) {
                BlockPos pos = hitResult.getBlockPos();
                player.teleportTo(pos.getX() + .5, pos.getY() + 1, pos.getZ() + .5);

            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public void onServerStarted(MinecraftServer server) {
        SpeedrunnerVsHunterSculk.serverStarted(server);
    }
}
