package tfar.speedrunnervshuntersculk;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.gameevent.GameEvent;

public class TimeLimitedBlockItem extends BlockItem {
    public TimeLimitedBlockItem(Block $$0, Properties $$1) {
        super($$0, $$1);
    }

    @Override
    public InteractionResult useOn(UseOnContext $$0) {
        Player player = $$0.getPlayer();
        if (player != null) {
            player.getCooldowns().addCooldown(Init.SPEEDRUNNER_SCULK_SENSOR_HEALTH_I,20 * 60);
            player.getCooldowns().addCooldown(Init.SPEEDRUNNER_SCULK_SENSOR_REACH_I,20 * 60);
            player.getCooldowns().addCooldown(Init.SPEEDRUNNER_SCULK_SENSOR_KNOCKBACK_I,20 * 60);
            player.getCooldowns().addCooldown(Init.SPEEDRUNNER_SCULK_SENSOR_VOID_I,20 * 60);

        }
        return super.useOn($$0);
    }

    public InteractionResult place(BlockPlaceContext $$0) {
        if (!this.getBlock().isEnabled($$0.getLevel().enabledFeatures())) {
            return InteractionResult.FAIL;
        } else if (!$$0.canPlace()) {
            return InteractionResult.FAIL;
        } else {
            BlockPlaceContext $$1 = this.updatePlacementContext($$0);
            if ($$1 == null) {
                return InteractionResult.FAIL;
            } else {
                BlockState $$2 = this.getPlacementState($$1);
                if ($$2 == null) {
                    return InteractionResult.FAIL;
                } else if (!this.placeBlock($$1, $$2)) {
                    return InteractionResult.FAIL;
                } else {
                    BlockPos $$3 = $$1.getClickedPos();
                    Level $$4 = $$1.getLevel();
                    Player $$5 = $$1.getPlayer();
                    ItemStack $$6 = $$1.getItemInHand();
                    BlockState $$7 = $$4.getBlockState($$3);
                    if ($$7.is($$2.getBlock())) {
                        $$7 = this.updateBlockStateFromTag($$3, $$4, $$6, $$7);
                        this.updateCustomBlockEntityTag($$3, $$4, $$5, $$6, $$7);
                        $$7.getBlock().setPlacedBy($$4, $$3, $$7, $$5, $$6);
                        if ($$5 instanceof ServerPlayer) {
                            CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer)$$5, $$3, $$6);
                        }
                    }

                    SoundType $$8 = $$7.getSoundType();
                    $$4.playSound($$5, $$3, this.getPlaceSound($$7), SoundSource.BLOCKS, ($$8.getVolume() + 1.0F) / 2.0F, $$8.getPitch() * 0.8F);
                    $$4.gameEvent(GameEvent.BLOCK_PLACE, $$3, GameEvent.Context.of($$5, $$7));
                    if ($$5 == null || !$$5.getAbilities().instabuild) {
                       // $$6.shrink(1);
                    }

                    return InteractionResult.sidedSuccess($$4.isClientSide);
                }
            }
        }
    }

    private BlockState updateBlockStateFromTag(BlockPos $$0, Level $$1, ItemStack $$2, BlockState $$3) {
        BlockState $$4 = $$3;
        CompoundTag $$5 = $$2.getTag();
        if ($$5 != null) {
            CompoundTag $$6 = $$5.getCompound("BlockStateTag");
            StateDefinition<Block, BlockState> $$7 = $$3.getBlock().getStateDefinition();

            for(String $$8 : $$6.getAllKeys()) {
                Property<?> $$9 = $$7.getProperty($$8);
                if ($$9 != null) {
                    String $$10 = $$6.get($$8).getAsString();
                    $$4 = updateState($$4, $$9, $$10);
                }
            }
        }

        if ($$4 != $$3) {
            $$1.setBlock($$0, $$4, 2);
        }

        return $$4;
    }

    private static <T extends Comparable<T>> BlockState updateState(BlockState $$0, Property<T> $$1, String $$2) {
        return $$1.getValue($$2).map($$2x -> $$0.setValue($$1, $$2x)).orElse($$0);
    }

}
