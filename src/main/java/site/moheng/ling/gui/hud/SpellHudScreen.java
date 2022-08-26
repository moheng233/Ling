package site.moheng.ling.gui.hud;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;
import site.moheng.ling.LingMod;

public class SpellHudScreen extends DrawableHelper implements HudRenderCallback {
    private MinecraftClient client = MinecraftClient.getInstance();

    public int getCenterX(Window windows,int offset) {
        return windows.getScaledWidth() / 2 + offset;
    }

    public int getCenterX(Window windows) {
        return getCenterX(windows,0);
    }

    public int getCenterY(Window windows,int offset) {
        return windows.getScaledHeight() / 2 + offset;
    }

    public int getCenterY(Window windows) {
        return getCenterY(windows ,0);
    }

    @Override
    public void onHudRender(MatrixStack matrixStack, float tickDelta) {
        var maxMana = client.player.getAttributeValue(LingMod.MOD_MAX_MANA);

        if(maxMana >= 0) {
            
        }
    }
}
