package tfar.huntervsspeedrunnersculk;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.SculkSensorBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
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
}
