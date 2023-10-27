package tfar.speedrunnervshuntersculk.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.gui.screens.TitleScreen;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfar.speedrunnervshuntersculk.CommonClient;
import tfar.speedrunnervshuntersculk.SpeedrunnerVsHunterSculk;
import tfar.speedrunnervshuntersculk.client.Client;

@Mixin(MouseHandler.class)
public class MixinTitleScreen {

    @Shadow
    @Final
    private Minecraft minecraft;

    @Inject(method = "onScroll",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;getInventory()Lnet/minecraft/world/entity/player/Inventory;"), cancellable = true)
    private void onScroll(long window, double horizontal, double vertical, CallbackInfo ci) {
        double delta = (this.minecraft.options.discreteMouseScroll().get() ? Math.signum(horizontal) : vertical) * this.minecraft.options.mouseWheelSensitivity().get();
        if (CommonClient.onScroll(minecraft,delta)) ci.cancel();
    }
}