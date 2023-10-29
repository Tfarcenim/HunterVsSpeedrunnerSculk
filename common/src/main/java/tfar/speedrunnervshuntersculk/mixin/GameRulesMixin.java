package tfar.speedrunnervshuntersculk.mixin;

import net.minecraft.world.level.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfar.speedrunnervshuntersculk.SpeedrunnerVsHunterSculk;

@Mixin(GameRules.class)
public abstract class GameRulesMixin {

    @Shadow
    private static <T extends GameRules.Value<T>> GameRules.Key<T> register(String name, GameRules.Category category, GameRules.Type<T> type) {
        throw new VerifyError();
    }

    @Inject(at = @At("TAIL"), method = "<clinit>")
    private static void init(CallbackInfo info) {
        SpeedrunnerVsHunterSculk.RULE_SPEEDRUNNER_SCULK_COOLDOWN = register("speedrunnerSculkCooldown",
                GameRules.Category.PLAYER, GameRules.IntegerValue.create(20));
    }
}