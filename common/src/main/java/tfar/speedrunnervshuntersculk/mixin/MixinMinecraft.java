package tfar.speedrunnervshuntersculk.mixin;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfar.speedrunnervshuntersculk.SpeedrunnerVsHunterSculk;

@Mixin(Minecraft.class)
public class MixinMinecraft {
    
    @Inject(at = @At("TAIL"), method = "<init>")
    private void init(CallbackInfo info) {
        
        SpeedrunnerVsHunterSculk.LOG.info("This line is printed by an example mod common mixin!");
        SpeedrunnerVsHunterSculk.LOG.info("MC Version: {}", Minecraft.getInstance().getVersionType());
    }
}