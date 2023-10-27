package tfar.speedrunnervshuntersculk;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
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
    private BuffType buffType;


    public void setBuffType(BuffType buffType) {
        this.buffType = buffType;
        setChanged();
    }

    public BuffType getBuffType() {
        return buffType;
    }

    @Override
    public VibrationSystem.User createVibrationUser() {
        return new VibeUser(this.getBlockPos());
    }

    @Nullable
    @Override
    public UUID getOwnerUUID() {
        return owner;
    }

    public void setOwner(@Nullable UUID owner) {
        this.owner = owner;
        setChanged();
    }

    @Override
    public EntityGetter level() {
        return getLevel();
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if (owner != null) {
            tag.putUUID("owner",owner);
        }
        tag.putInt(Utils.BUFF_TAG,buffType.ordinal());
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("owner")) {
            owner = tag.getUUID("owner");
        }
        buffType = BuffType.values()[tag.getInt(Utils.BUFF_TAG)];
    }

    public class VibeUser extends VibrationUser {

        public VibeUser(BlockPos $$1) {
            super($$1);
        }


        @Override
        public boolean canReceiveVibration(ServerLevel $$0, BlockPos $$1, GameEvent $$2, @Nullable GameEvent.Context context) {
            boolean preconditions = super.canReceiveVibration($$0, $$1, $$2, context);
            if (!preconditions) return false;

            if (true) return true;

            if (context == null) return true;

            return context.sourceEntity() instanceof ServerPlayer serverPlayer && !serverPlayer.getUUID().equals(SpeedrunnerVsHunterSculk.speedrunner);

        }

        @Override
        public void onReceiveVibration(ServerLevel $$0, BlockPos $$1, GameEvent $$2, @Nullable Entity $$3, @Nullable Entity $$4, float $$5) {
            super.onReceiveVibration($$0, $$1, $$2, $$3, $$4, $$5);
        }
    }
}
