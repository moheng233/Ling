package site.moheng.ling.gui.widget;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import site.moheng.ling.util.NinePatchHelper;

public class WButtom extends AbsWidget {
    public static final Identifier WIDGETS_TEXTURE = new Identifier("textures/gui/widgets.png");
    public static final NinePatchHelper BUTTOM = new NinePatchHelper(WIDGETS_TEXTURE, 0, 2, 197, 199, 66, 68, 82, 85);

    protected int width;
    protected int height;

    protected Text text;

    public WButtom(int width, int height, Text text) {
        this.width = width;
        this.height = height;
        this.text = text;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        TextRenderer textRenderer = client.textRenderer;
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, WIDGETS_TEXTURE);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        int j = isCollision(mouseX, mouseY) ? 0xFFFFFF : 0xA0A0A0;
        BUTTOM.draw(matrices, getX(), getY(), getWidth(), getHeight());
        drawCenteredText(matrices, textRenderer, text, getX()+ this.width / 2, getY() + (this.height - 8) / 2, j | MathHelper.ceil(1.0f * 255.0f) << 24);
    }

    @Override
    public void layout() {
        width = Math.max(width, client.textRenderer.getWidth(text) + 20);
        super.layout();
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }
    
}
