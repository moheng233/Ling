package site.moheng.ling.gui.widget;

import java.util.HashMap;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import site.moheng.ling.util.Point;

public class WSpellEdit extends AbsParentWidget {

    public int width;
    public int height;
    public float scale = 1;

    public int offsetX = 0;
    public int offsetY = 0;

    public HashMap<AbsWidget, Point> posMap = new HashMap<>();

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        matrices.push();
        matrices.translate(getX(), getY(), 0);
        matrices.scale(scale, scale, 1);
        matrices.translate(offsetX, offsetY, 0);
        var pos = Point.of(mouseX, mouseY);
        for (AbsWidget widget : children) {
            widget.render(matrices, pos.x(), pos.y(), delta);
        }
        matrices.pop();
        super.render(matrices, mouseX, mouseY, delta);
    }

    public void setPos(AbsWidget widget, Point pos) {
        posMap.put(widget, pos);
    }

    @Override
    public int getX(AbsWidget widget) {
        return posMap.getOrDefault(widget, Point.ZERO).x();
    }

    @Override
    public int getY(AbsWidget widget) {
        return posMap.getOrDefault(widget, Point.ZERO).y();
    }

    @Override
    public Point convent(Point point) {
        return super.convent(point).multiply(1 / scale);
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if(focused.isEmpty()) {
            if (button == 0) {
                offsetX += deltaX;
                offsetY += deltaY;

                return true;
            }
        }
        return false;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        var mouse = Point.of((float) mouseX, (float) mouseY);
        var last_pos = convent(mouse);
        scale = (float) MathHelper.clamp(scale + amount * 0.1, 0.2, 2);
        var now_pos = convent(mouse);
        offsetX += (now_pos.x() - last_pos.x());
        offsetY += (now_pos.y() - last_pos.y());
        return false;
    }

    public WSpellEdit(int width, int height, AbsWidget... widgets) {
        super(widgets);
        this.width = width;
        this.height = height;
    }
}
