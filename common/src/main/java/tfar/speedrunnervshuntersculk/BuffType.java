package tfar.speedrunnervshuntersculk;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attributes;
import tfar.speedrunnervshuntersculk.platform.Services;

import java.util.function.Consumer;

public enum BuffType {

    BLANK(serverPlayer -> {}),
    HEALTH(serverPlayer -> Utils.addStackableAttributeModifier(serverPlayer, Attributes.MAX_HEALTH,4)),
    REACH(serverPlayer -> {
        Utils.addStackableAttributeModifier(serverPlayer, Services.PLATFORM.getBlockReachAttribute(),1);
        Utils.addStackableAttributeModifier(serverPlayer, Services.PLATFORM.getEntityReachAttribute(),1);
    }),
    KNOCKBACK(serverPlayer -> Utils.addStackableAttributeModifier(serverPlayer, Attributes.ATTACK_KNOCKBACK,1)),
    VOID_POWER(serverPlayer -> VoidPowerManager.addPower(serverPlayer,1));

    public final Consumer<ServerPlayer> activate;

    BuffType(Consumer<ServerPlayer> activate) {
        this.activate = activate;
    }

}
