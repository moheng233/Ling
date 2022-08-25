package site.moheng.ling.gui.widget;

import java.util.LinkedList;
import java.util.Optional;

import net.minecraft.client.util.math.MatrixStack;
import site.moheng.ling.util.Point;

public abstract class AbsParentWidget extends AbsWidget {
    protected LinkedList<AbsWidget> children = new LinkedList<>();
    protected Optional<AbsWidget> focused = Optional.empty();

    public AbsParentWidget(AbsWidget ...widgets) {
        add(widgets);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        var pos = convent(Point.of(mouseX, mouseY));
        for (AbsWidget widget : children) {
            widget.render(matrices,  pos.x(), pos.y(), delta);
        }
        super.render(matrices,  mouseX, mouseY, delta);
    }

    public AbsParentWidget add(AbsWidget ...widgets) {
        for (var widget : widgets) {
            children.add(widget);
            widget.parent = Optional.of(this);
        }
        return this;
    }

    public boolean isFocused(AbsWidget widget) {
        if(focused.isPresent()) {
            return focused.get() == widget;
        }

        return false;
    }

    protected void setFocused(Optional<AbsWidget> widget) {
        if(widget.isPresent()) {
            if(widget.get().canFocused()) {
                focused = widget;
            }
        } else {
            focused = widget;
        }
    }

    public abstract int getX(AbsWidget widget);
    public abstract int getY(AbsWidget widget);

    @Override
    public void layout() {
        for (AbsWidget widget : children) {
            widget.layout();
        }
        super.layout();
    }

    public Point convent(Point point) {
        return point.reduce(getPoint());
    }

    public Optional<AbsWidget> find(Point point) {
        for (AbsWidget widget : children) {
            if(widget.isCollision(point.x(), point.y())) {
                return Optional.of(this);
            }
        }

        return Optional.empty();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if(isCollision(mouseX, mouseY)) {
            var pos = convent(Point.of(mouseX, mouseY));
            var widget = find(pos);
            setFocused(widget);
            if(widget.isPresent()){
                return widget.get().mouseClicked(pos.x(), pos.y(), button);
            }
        } 

        return false;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if(isCollision(mouseX, mouseY)) {
            var pos = convent(Point.of(mouseX, mouseY));
            var widget = find(pos);
            if(widget.isPresent()){
                return widget.get().mouseReleased(pos.x(), pos.y(), button);
            }
        } 

        return false;
    }
}
