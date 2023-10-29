package tfar.speedrunnervshuntersculk;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SculkSensorBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SculkSensorBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SculkSensorPhase;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;
import org.jetbrains.annotations.Nullable;

public class SpeedrunnerSculkSensorBlock extends SculkSensorBlock {
    public final BuffType buffType;

    public SpeedrunnerSculkSensorBlock(Properties $$0, BuffType buffType) {
        super($$0);
        this.buffType = buffType;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos $$0, BlockState $$1) {
        return new SpeedrunnerSculkSensorBlockEntity($$0, $$1);
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level2, BlockState state, BlockEntityType<T> blockEntityType) {
        if (!level2.isClientSide) {
            return SculkSensorBlock.createTickerHelper(blockEntityType, Init.SPEEDRUNNER_SCULK_SENSOR_E, (level, blockPos, blockState, sculkSensorBlockEntity) -> VibrationSystem.Ticker.tick(level, sculkSensorBlockEntity.getVibrationData(), sculkSensorBlockEntity.getVibrationUser()));
        }
        return null;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState $$2, @Nullable LivingEntity livingEntity, ItemStack stack) {
        super.setPlacedBy(level, pos, $$2, livingEntity, stack);
        if (level.getBlockEntity(pos) instanceof SpeedrunnerSculkSensorBlockEntity speedrunnerSculkSensorBlockEntity && livingEntity != null) {
            speedrunnerSculkSensorBlockEntity.setOwner(livingEntity.getUUID());
            BuffType buffType = BuffType.values()[stack.getOrCreateTag().getInt(Utils.BUFF_TAG)];
        }
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        BlockEntity blockEntity;
        if (!level.isClientSide() && SculkSensorBlock.canActivate(state) && entity.getType() != EntityType.WARDEN && (blockEntity = level.getBlockEntity(pos)) instanceof SculkSensorBlockEntity) {
            SculkSensorBlockEntity sculkSensorBlockEntity = (SculkSensorBlockEntity)blockEntity;
            if (level instanceof ServerLevel serverLevel) {
                if (sculkSensorBlockEntity.getVibrationUser().canReceiveVibration(serverLevel, pos, GameEvent.STEP, GameEvent.Context.of(entity,state))) {
                    sculkSensorBlockEntity.getListener().forceScheduleVibration(serverLevel, GameEvent.STEP, GameEvent.Context.of(entity,state), entity.position());
                }
            }
        }
       // super.stepOn(level, pos, state, entity);
    }

    @Override
    public void activate(@Nullable Entity $$0, Level level, BlockPos pos, BlockState $$3, int $$4, int $$5) {
        level.setBlock(pos, $$3.setValue(PHASE, SculkSensorPhase.ACTIVE).setValue(POWER, $$4), 3);
        level.scheduleTick(pos, $$3.getBlock(), this.getActiveTicks());
        updateNeighbours(level, pos, $$3);
        tryResonateVibration($$0, level, pos, $$5);
        level.gameEvent($$0, GameEvent.SCULK_SENSOR_TENDRILS_CLICKING, pos);
        if (!(Boolean)$$3.getValue(WATERLOGGED)) {
         //   level.playSound(null, (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, SoundEvents.SCULK_CLICKING, SoundSource.BLOCKS, 1.0F, level.random.nextFloat() * 0.2F + 0.8F);
        }

        if (SpeedrunnerVsHunterSculk.speedrunner != null && !level.isClientSide) {
            ServerPlayer serverPlayer = level.getServer().getPlayerList().getPlayer(SpeedrunnerVsHunterSculk.speedrunner);
            if (serverPlayer != null) {
                BuffType buffType = this.buffType;
                buffType.activate.accept(serverPlayer);
            }
        }
    }

    @Override
    public int getActiveTicks() {
        return SpeedrunnerVsHunterSculk.server.getLevel(Level.OVERWORLD).getGameRules().getInt(SpeedrunnerVsHunterSculk.RULE_SPEEDRUNNER_SCULK_COOLDOWN);
    }
}
