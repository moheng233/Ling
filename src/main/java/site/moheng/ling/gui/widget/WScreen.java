package site.moheng.ling.gui.widget;

import net.minecraft.client.util.math.MatrixStack;

public class WScreen extends AbsParentWidget {
    protected int screenWidth;
    protected int screenHeight;

    public WScreen(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }
    
    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public int getX(AbsWidget widget) {
        return (screenWidth - widget.getWidth()) / 2;
    }

    @Override
    public int getY(AbsWidget widget) {
        return (screenHeight - widget.getHeight()) / 2;
    }

    @Override
    public int getX() {
        return 0;
    }

    @Override
    public int getY() {
        return 0;
    }

    @Override
    public int getWidth() {
        return screenWidth;
    }

    @Override
    public int getHeight() {
        return screenHeight;
    }

    
    
}
