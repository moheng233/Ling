package site.moheng.ling.gui.widget;

import java.util.HashMap;

import net.minecraft.util.math.Vec2f;

/**
 * 可以自动对子组件进行垂直或水平布局的容器组件
 */
public class WContainer extends AbsParentWidget {
    protected LayoutDirection direction = LayoutDirection.VERTICAL; 
    protected HashMap<AbsWidget, Vec2f> pos_cache = new HashMap<>();
    
    public WContainer(LayoutDirection direction, AbsWidget ...widgets) {
        super(widgets);
        this.direction = direction;
    }

    @Override
    public void layout() {
        pos_cache.clear();
        var count = 0;
        for (var widget : children) {
            if(direction == LayoutDirection.VERTICAL) {
                pos_cache.put(widget, new Vec2f(0, count));
                count += widget.getHeight();
            } else {
                pos_cache.put(widget, new Vec2f(count, 0));
                count += widget.getWidth();
            }
        }
        super.layout();
    }

    @Override
    public int getX(AbsWidget widget) {
        return getX() + (int) pos_cache.get(widget).x;
    }

    @Override
    public int getY(AbsWidget widget) {
        return getY() + (int) pos_cache.get(widget).y;
    }

    @Override
    public int getWidth() {
        if(direction == LayoutDirection.VERTICAL) {
            return getChilernMaxWidth();
        } else {
            var count = 0;
            for (AbsWidget widget : children) {
                count += widget.getWidth();
            }

            return count;
        }
    }

    @Override
    public int getHeight() {
        if(direction == LayoutDirection.HORIZONTAL) {
            return getChilernMaxHeight();
        } else {
            var count = 0;
            for (AbsWidget widget : children) {
                count += widget.getHeight();
            }

            return count;
        }
    }

    public int getChilernMaxWidth() {
        var max = 0;
        for (AbsWidget widget : children) {
            var width = widget.getWidth();
            if(width > max) {
                max = width;
            }
        }

        return max;
    }

    public int getChilernMaxHeight() {
        var max = 0;
        for (AbsWidget widget : children) {
            var height = widget.getHeight();
            if(height > max) {
                max = height;
            }
        }

        return max;
    }

    public static enum LayoutDirection {
        VERTICAL, HORIZONTAL
    }

}
