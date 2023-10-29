package tfar.speedrunnervshuntersculk;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class Init {

    public static final Block SPEEDRUNNER_SCULK_SENSOR_BLANK = new SpeedrunnerSculkSensorBlock(BlockBehaviour.Properties.copy(Blocks.SCULK_SENSOR),BuffType.BLANK);
    public static final Block SPEEDRUNNER_SCULK_SENSOR_HEALTH = new SpeedrunnerSculkSensorBlock(BlockBehaviour.Properties.copy(Blocks.SCULK_SENSOR),BuffType.HEALTH);
    public static final Block SPEEDRUNNER_SCULK_SENSOR_REACH = new SpeedrunnerSculkSensorBlock(BlockBehaviour.Properties.copy(Blocks.SCULK_SENSOR),BuffType.REACH);
    public static final Block SPEEDRUNNER_SCULK_SENSOR_KNOCKBACK = new SpeedrunnerSculkSensorBlock(BlockBehaviour.Properties.copy(Blocks.SCULK_SENSOR),BuffType.KNOCKBACK);
    public static final Block SPEEDRUNNER_SCULK_SENSOR_VOID = new SpeedrunnerSculkSensorBlock(BlockBehaviour.Properties.copy(Blocks.SCULK_SENSOR),BuffType.VOID_POWER);

    public static final BlockEntityType<SpeedrunnerSculkSensorBlockEntity> SPEEDRUNNER_SCULK_SENSOR_E =
            BlockEntityType.Builder.of(SpeedrunnerSculkSensorBlockEntity::new,SPEEDRUNNER_SCULK_SENSOR_BLANK,SPEEDRUNNER_SCULK_SENSOR_HEALTH,SPEEDRUNNER_SCULK_SENSOR_REACH
                    ,SPEEDRUNNER_SCULK_SENSOR_KNOCKBACK,SPEEDRUNNER_SCULK_SENSOR_VOID).build(null);

    public static final Item SPEEDRUNNER_SCULK_SENSOR_BLANK_I = new TimeLimitedBlockItem(SPEEDRUNNER_SCULK_SENSOR_BLANK,new Item.Properties());
    public static final Item SPEEDRUNNER_SCULK_SENSOR_HEALTH_I = new TimeLimitedBlockItem(SPEEDRUNNER_SCULK_SENSOR_HEALTH,new Item.Properties());
    public static final Item SPEEDRUNNER_SCULK_SENSOR_REACH_I = new TimeLimitedBlockItem(SPEEDRUNNER_SCULK_SENSOR_REACH,new Item.Properties());
    public static final Item SPEEDRUNNER_SCULK_SENSOR_KNOCKBACK_I = new TimeLimitedBlockItem(SPEEDRUNNER_SCULK_SENSOR_KNOCKBACK,new Item.Properties());
    public static final Item SPEEDRUNNER_SCULK_SENSOR_VOID_I = new TimeLimitedBlockItem(SPEEDRUNNER_SCULK_SENSOR_VOID,new Item.Properties());

    public static final EntityType<VoidHoleEntity> VOID_HOLE = EntityType.Builder.of(VoidHoleEntity::new, MobCategory.MISC).build("void_hole");

}
