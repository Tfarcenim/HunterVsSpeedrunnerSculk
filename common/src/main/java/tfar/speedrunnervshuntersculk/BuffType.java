package tfar.speedrunnervshuntersculk;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import tfar.speedrunnervshuntersculk.platform.Services;

import java.util.function.Consumer;
import java.util.function.Supplier;

public enum BuffType {

    BLANK(serverPlayer -> {},() -> Init.SPEEDRUNNER_SCULK_SENSOR_BLANK_I),
    HEALTH(serverPlayer -> {
        Utils.addStackableAttributeModifier(serverPlayer, Attributes.MAX_HEALTH, 4);
        if (serverPlayer.getRandom().nextDouble() < .3) {
            serverPlayer.addEffect(new MobEffectInstance(MobEffects.REGENERATION,40,2));
        }
    },() -> Init.SPEEDRUNNER_SCULK_SENSOR_HEALTH_I),
    REACH(serverPlayer -> {
        Utils.addStackableAttributeModifier(serverPlayer, Services.PLATFORM.getBlockReachAttribute(),1);
        Utils.addStackableAttributeModifier(serverPlayer, Services.PLATFORM.getEntityReachAttribute(),1);
    },() -> Init.SPEEDRUNNER_SCULK_SENSOR_REACH_I),
    KNOCKBACK(serverPlayer -> Utils.addStackableAttributeModifier(serverPlayer, Attributes.ATTACK_KNOCKBACK,1),() -> Init.SPEEDRUNNER_SCULK_SENSOR_KNOCKBACK_I),
    VOID_POWER(serverPlayer -> VoidPowerManager.addPower(serverPlayer,1),() -> Init.SPEEDRUNNER_SCULK_SENSOR_VOID_I);

    public final Consumer<ServerPlayer> activate;
    private final Supplier<Item> itemSupplier;

    BuffType(Consumer<ServerPlayer> activate, Supplier<Item> itemSupplier) {
        this.activate = activate;
        this.itemSupplier = itemSupplier;
    }

    public Item getItem() {
        return itemSupplier.get();
    }
}
