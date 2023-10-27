package tfar.speedrunnervshuntersculk;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.level.EntityGetter;
import net.minecraft.world.level.block.entity.SculkSensorBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class SpeedrunnerSculkSensorBlockEntity extends SculkSensorBlockEntity implements OwnableEntity {
    public SpeedrunnerSculkSensorBlockEntity(BlockPos $$0, BlockState $$1) {
        super(Init.SPEEDRUNNER_SCULK_SENSOR_E,$$0, $$1);
    }

    private UUID owner;



    @Override
    public VibrationSystem.User createVibrationUser() {
        return new VibeUser(this.getBlockPos());
    }

    @Nullable
    @Override
    public UUID getOwnerUUID() {
        return owner;
    }

    @Override
    public EntityGetter level() {
        return getLevel();
    }

    @Override
    protected void saveAdditional(CompoundTag $$0) {
        super.saveAdditional($$0);
        if (owner != null) {
            $$0.putUUID("owner",owner);
        }
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
        setChanged();
    }

    @Override
    public void load(CompoundTag $$0) {
        super.load($$0);
        if ($$0.contains("owner")) {
            owner = $$0.getUUID("owner");
        }
    }

    public class VibeUser extends VibrationUser {

        public VibeUser(BlockPos $$1) {
            super($$1);
        }


        @Override
        public boolean canReceiveVibration(ServerLevel $$0, BlockPos $$1, GameEvent $$2, @Nullable GameEvent.Context context) {
            boolean preconditions = super.canReceiveVibration($$0, $$1, $$2, context);
            if (!preconditions) return false;
            return context == null || context.sourceEntity() == null ||
                    !context.sourceEntity().getUUID().equals(owner);
        }

        @Override
        public void onReceiveVibration(ServerLevel $$0, BlockPos $$1, GameEvent $$2, @Nullable Entity $$3, @Nullable Entity $$4, float $$5) {
            super.onReceiveVibration($$0, $$1, $$2, $$3, $$4, $$5);
        }
    }
}
