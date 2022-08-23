package site.moheng.ling.gui.widget;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class WLable extends AbsWidget {

    protected Text text;
    protected int color = 0xffffffff;
    
    public WLable(Text text, int color) {
        this.text = text;
        this.color = color;
    }

    public WLable(Text text) {
        this.text = text;
    }

    @Override
    public int getWidth() {
        return client.textRenderer.getWidth(text);
    }

    @Override
    public int getHeight() {
        return client.textRenderer.fontHeight;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        client.textRenderer.draw(matrices, text, getX(), getY(), color);
        super.render(matrices, mouseX, mouseY, delta);
    }
    
}
