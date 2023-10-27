package tfar.speedrunnervshuntersculk;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;

public class Utils {

    public static final String BUFF_TAG = "buffType";
    public static void changeMode(ServerPlayer player, boolean up) {
        ItemStack stack =player.getMainHandItem();
        int o = stack.getOrCreateTag().getInt(BUFF_TAG);
        if (up) {
            o++;
            if (o >= BuffType.values().length) o = 0;
        } else {
            o--;
            if (o < 0) o = BuffType.values().length-1;
        }
        stack.getOrCreateTag().putInt(BUFF_TAG,o);
        BuffType newMode = BuffType.values()[o];
        player.sendSystemMessage(Component.translatable("Buff is "+newMode),true);
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
            player.level().addFreshEntity(voidHoleEntity);
        }
    }
}
