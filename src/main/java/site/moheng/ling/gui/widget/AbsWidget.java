package site.moheng.ling.gui.widget;

import java.util.Optional;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.Element;
import net.minecraft.client.util.math.MatrixStack;
import site.moheng.ling.util.Point;

@Environment(EnvType.CLIENT)
public abstract class AbsWidget extends DrawableHelper implements Element {
    protected Optional<AbsParentWidget> parent = Optional.empty();
    protected MinecraftClient client = MinecraftClient.getInstance();

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {

    }

    public void setParent(Optional<AbsParentWidget> parent) {
        this.parent = parent;
    }

    public abstract int getWidth();

    public abstract int getHeight();

    public boolean isCollision(int x, int y) {
        return x > getX() && x < (getX() + getWidth()) && y > getY() && y < (getY() + getHeight());
    }

    public boolean isCollision(double x, double y) {
        return isCollision((int)x, (int)y);
    }

    public int getX() {
        return parent.get().getX(this);
    }

    public int getY() {
        return parent.get().getY(this);
    }

    public Point getPoint() {
        return new Point(getX(), getY());
    }

    public boolean canFocused() {
        return false;
    }

    /**
     * 对组件进行布局更新，带有递归调用
     */
    public void layout() {

    }
}
