package site.moheng.ling.util;

import net.minecraft.client.util.math.Rect2i;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3i;

public class MathRect extends Rect2i {

    public MathRect(int x, int y, int width, int height) {
        super(x, y, width, height);
    }
    
    public MathRect(Vec2f pos, int width, int height) {
        super((int)pos.x, (int)pos.y, width, height);
    }

    public MathRect(Vec3i pos, int width, int height) {
        super(pos.getX(), pos.getY(), width, height);
    }

    public Vec2f getPos() {
        return new Vec2f(getX(), getY());
    }

    public MathRect add(Vec2f pos) {
        return new MathRect(getPos().add(pos), getWidth(), getHeight());
    }

    public boolean contains(Vec2f pos) {
        return contains((int)pos.x, (int)pos.y);
    }

    public boolean contains(Vec3i pos) {
        return contains(pos.getX(), pos.getY());
    }

    public MathRect multiply(float scale) {
        return new MathRect(getPos().multiply(scale), (int)(getWidth() * scale), (int)(getHeight() * scale));
    }

    public Vec2f center() {
        return new Vec2f(getX() + (getWidth() / 2), getY() + (getHeight() / 2));
    }

    public boolean contains(double x, double y) {
        return contains((int)x, (int) y);
    }
}
