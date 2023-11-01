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
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class VoidHoleEntity extends Projectile {

    VoidHoleEntity(EntityType<? extends Projectile> $$0, Level $$1) {
        super($$0, $$1);
    }

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
    protected void defineSynchedData() {

    }

    @Override
    public void tick() {
        super.tick();

        HitResult hitresult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
        if (hitresult.getType() != HitResult.Type.MISS) {
            this.onHit(hitresult);
        }


        if (!level().isClientSide && getDeltaMovement().lengthSqr() < .0005) {
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
        Vec3 vec3 = this.getDeltaMovement();
        double d0 = this.getX() + vec3.x;
        double d1 = this.getY() + vec3.y;
        double d2 = this.getZ() + vec3.z;
        setPos(d0,d1,d2);
    }

    @Override
    protected void onHit(HitResult $$0) {
        super.onHit($$0);
        setDeltaMovement(Vec3.ZERO);
    }

    public void giveItemsToOwner(List<ItemStack> stacks) {

        LivingEntity owner = getOwner();

        if (owner instanceof ServerPlayer serverplayer) {
            for (ItemStack stack : stacks) {
                serverplayer.addItem(stack);
            }
        }
    }

    /*
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

                        serverplayer.level().playSound(null, serverplayer.getX(), serverplayer.getY(), serverplayer.getZ(), SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 0.2F, ((serverplayer.getRandom().nextFloat() - serverplayer.getRandom().nextFloat()) * 0.7F + 1.0F) * 2.0F);
                        serverplayer.containerMenu.broadcastChanges();
                    } else {
                        ItemEntity itementity = serverplayer.drop(itemstack1, false);
                        if (itementity != null) {
                            itementity.setNoPickUpDelay();
                            itementity.setTarget(serverplayer.getUUID());
                        }
                    }
                }

     */

    @Override
    protected void readAdditionalSaveData(CompoundTag var1) {
        progress = var1.getInt("progress");
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag var1) {
        var1.putInt("progress",progress);
    }


    @Nullable
    @Override
    public LivingEntity getOwner() {
        return (LivingEntity) super.getOwner();
    }

    @Override
    public boolean alwaysAccepts() {
        return super.alwaysAccepts();
    }
}
