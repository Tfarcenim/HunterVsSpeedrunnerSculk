package tfar.speedrunnervshuntersculk;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class VoidHoleEntity extends Entity implements OwnableEntity {
    public VoidHoleEntity(EntityType<?> $$0, Level $$1) {
        super($$0, $$1);
    }


    protected static final EntityDataAccessor<Optional<UUID>> DATA_OWNERUUID_ID = SynchedEntityData.defineId(
            VoidHoleEntity.class, EntityDataSerializers.OPTIONAL_UUID);
    public int progress;

    public List<BlockPos> breakPositions = new ArrayList<>();
    public static int speed = 20;

    public static int radius = 10;

    public void createOrderedBreakPositions() {
        breakPositions = new ArrayList<>();
        for (int y = -radius;y < radius;y++) {
            for (int z = -radius;z < radius;z++) {
                for (int x = -radius;x < radius;x++) {
                    if (x * x + y * y + z * z < radius * radius) {
                        breakPositions.add(new BlockPos(x,y,z));
                    }
                }
            }
        }
        breakPositions.sort((pos1, pos2) -> {
            double product1 = pos1.distSqr(Vec3i.ZERO);
            double product2 = pos2.distSqr(Vec3i.ZERO);
            if (product1 > product2) {
                return 1;
            } else if (product1 == product2) {
                return 0;
            }
            return -1;
        });

    }

    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide) {
            level().getProfiler().push("voidHole");
            for (int i = 0; i < speed; i++) {
                progress++;
                if (progress < breakPositions.size()) {
                    BlockPos offset = breakPositions.get(progress);
                    BlockPos off = blockPosition().offset(offset);
                    BlockState state = level().getBlockState(off);
                    List<ItemStack> loot = Block.getDrops(state, (ServerLevel) level(),off,level().getBlockEntity(off),getOwner(),getOwner().getItemInHand(InteractionHand.MAIN_HAND));
                    giveItemsToOwner(loot);
                    level().removeBlock(off,false);
                } else {
                    discard();
                }
            }
            level().getProfiler().pop();
        }
    }

    public void giveItemsToOwner(List<ItemStack> stacks) {

        LivingEntity owner = getOwner();

        if (owner instanceof ServerPlayer serverplayer) {
            for (ItemStack stack : stacks) {
                int k = stack.getCount();

                while (k > 0) {
                    int l = Math.min(stack.getMaxStackSize(), k);
                    k -= l;
                    ItemStack itemstack1 = new ItemStack(stack.getItem(),l);
                    itemstack1.setTag(stack.getTag());
                    boolean flag = serverplayer.getInventory().add(itemstack1);
                    if (flag && itemstack1.isEmpty()) {
                        itemstack1.setCount(1);
                        ItemEntity itementity1 = serverplayer.drop(itemstack1, false);
                        if (itementity1 != null) {
                            itementity1.makeFakeItem();
                        }

                        serverplayer.level().playSound((Player) null, serverplayer.getX(), serverplayer.getY(), serverplayer.getZ(), SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 0.2F, ((serverplayer.getRandom().nextFloat() - serverplayer.getRandom().nextFloat()) * 0.7F + 1.0F) * 2.0F);
                        serverplayer.containerMenu.broadcastChanges();
                    } else {
                        ItemEntity itementity = serverplayer.drop(itemstack1, false);
                        if (itementity != null) {
                            itementity.setNoPickUpDelay();
                            itementity.setTarget(serverplayer.getUUID());
                        }
                    }
                }
            }
        }
    }


    @Override
    protected void defineSynchedData() {
        entityData.define(DATA_OWNERUUID_ID,Optional.empty());
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag var1) {
        progress = var1.getInt("progress");

        UUID $$1;
        if (var1.hasUUID("Owner")) {
            $$1 = var1.getUUID("Owner");
        } else {
            String $$2 = var1.getString("Owner");
            $$1 = OldUsersConverter.convertMobOwnerIfNecessary(this.getServer(), $$2);
        }

        if ($$1 != null) {
            try {
                this.setOwnerUUID($$1);
            } catch (Throwable var4) {
            }
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag var1) {
        var1.putInt("progress",progress);
        if (this.getOwnerUUID() != null) {
            var1.putUUID("Owner", this.getOwnerUUID());
        }
    }

    @Nullable
    @Override
    public UUID getOwnerUUID() {
        return this.entityData.get(DATA_OWNERUUID_ID).orElse(null);
    }

    public void setOwnerUUID(@javax.annotation.Nullable UUID $$0) {
        this.entityData.set(DATA_OWNERUUID_ID, Optional.ofNullable($$0));
    }
}
