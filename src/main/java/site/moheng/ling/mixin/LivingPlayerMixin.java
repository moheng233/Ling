package site.moheng.ling.mixin;

import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import site.moheng.ling.LingMod;

@Mixin(PlayerEntity.class)
public class LivingPlayerMixin {
    @Inject(at = @At("RETURN"), method = "createPlayerAttributes")
    private static void createPlayerAttributes(CallbackInfoReturnable<DefaultAttributeContainer.Builder> cir) {
        cir.getReturnValue().add(LingMod.MOD_MAX_MANA, 0);
    }
}
