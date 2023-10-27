package tfar.speedrunnervshuntersculk;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SculkSensorBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;
import org.jetbrains.annotations.Nullable;

public class SpeedrunnerSculkSensorBlock extends SculkSensorBlock {
    public SpeedrunnerSculkSensorBlock(Properties $$0) {
        super($$0);
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
    public void setPlacedBy(Level level, BlockPos pos, BlockState $$2, @Nullable LivingEntity livingEntity, ItemStack $$4) {
        super.setPlacedBy(level, pos, $$2, livingEntity, $$4);
        if (level.getBlockEntity(pos) instanceof SpeedrunnerSculkSensorBlockEntity speedrunnerSculkSensorBlockEntity && livingEntity != null) {
            speedrunnerSculkSensorBlockEntity.setOwner(livingEntity.getUUID());
        }
    }
}
