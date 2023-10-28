package tfar.speedrunnervshuntersculk;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import tfar.speedrunnervshuntersculk.platform.Services;

import java.util.UUID;

public class Utils {

    public static final String BUFF_TAG = "buffType";
    static final Item[] items = new Item[]{Init.SPEEDRUNNER_SCULK_SENSOR_HEALTH_I,Init.SPEEDRUNNER_SCULK_SENSOR_REACH_I,Init.SPEEDRUNNER_SCULK_SENSOR_KNOCKBACK_I,Init.SPEEDRUNNER_SCULK_SENSOR_VOID_I};
    public static void changeMode(ServerPlayer player, boolean up) {
        ItemStack stack =player.getMainHandItem();

        if (stack.getItem() instanceof TimeLimitedBlockItem item) {
            Block block = Block.byItem(item);
            BuffType buffType = ((SpeedrunnerSculkSensorBlock)block).buffType;
            int o = buffType.ordinal();
            if (up) {
                o++;
                if (o >= BuffType.values().length) o = 0;
            } else {
                o--;
                if (o < 0) o = BuffType.values().length - 1;
            }
            player.setItemInHand(InteractionHand.MAIN_HAND,items[o].getDefaultInstance());
        }
    }

    static final UUID modifier_uuid = UUID.fromString("627369a4-ee41-4c26-a4f9-c1e3738aabda");

    public static void addStackableAttributeModifier(ServerPlayer player, Attribute attribute,double amount) {
        AttributeInstance attributeInstance = player.getAttribute(attribute);
        if (attributeInstance != null) {
            AttributeModifier attributeModifier = attributeInstance.getModifier(modifier_uuid);
            double existingAmount = attributeModifier == null ? 0 : attributeModifier.getAmount();
            if (attributeModifier != null) {
                attributeInstance.removePermanentModifier(modifier_uuid);
            }
            attributeInstance.addPermanentModifier(new AttributeModifier(modifier_uuid,"Sculk Buff",existingAmount + amount, AttributeModifier.Operation.ADDITION));
        }
    }

    public static void summonVoidHole(ServerPlayer player) {
        if (player.getUUID().equals(SpeedrunnerVsHunterSculk.speedrunner) && VoidPowerManager.getVoidPower(player) > 0) {
            VoidHoleEntity voidHoleEntity = Init.VOID_HOLE.create(player.level());
            voidHoleEntity.createOrderedBreakPositions();
            voidHoleEntity.moveTo(player.getPosition(0));
            voidHoleEntity.setOwnerUUID(player.getUUID());
            voidHoleEntity.setNoGravity(true);
            voidHoleEntity.setDeltaMovement(player.getLookAngle());
            player.level().addFreshEntity(voidHoleEntity);
        }
    }

    public static void stopTime(ServerPlayer player) {
        if (player.getUUID().equals(SpeedrunnerVsHunterSculk.speedrunner) && VoidPowerManager.getVoidPower(player) > 2) {
            Services.PLATFORM.stopTime(player);
        }
    }


    public static void handleKeybind(ServerPlayer player,KeyAction keyAction) {
        switch (keyAction) {
            case VOID_HOLE -> summonVoidHole(player);
            case TIME_PAUSE -> stopTime(player);
        }
    }
}
