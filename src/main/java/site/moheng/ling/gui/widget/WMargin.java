package site.moheng.ling.gui.widget;

public class WMargin extends AbsParentWidget {
    public int lPadding;
    public int tPadding;
    public int rPadding;
    public int bPadding;

    public int width = 0;
    public int height = 0;

    @Override
    public int getX(AbsWidget widget) {
        return lPadding;
    }

    @Override
    public int getY(AbsWidget widget) {
        return tPadding;
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
    public void layout() {
        var widget = children.getFirst();
        if (widget != null) {
            width = lPadding + widget.getWidth() + rPadding;
            height = tPadding + widget.getHeight() + bPadding;
        }
        super.layout();
    }

    public WMargin(int lPadding, int tPadding, int rPadding, int bPadding, AbsWidget... widgets) {
        super(widgets);
        this.lPadding = lPadding;
        this.tPadding = tPadding;
        this.rPadding = rPadding;
        this.bPadding = bPadding;
    }

    public WMargin(int hPadding, int vPadding, AbsWidget... widgets) {
        super(widgets);
        this.lPadding = hPadding;
        this.tPadding = vPadding;
        this.rPadding = hPadding;
        this.bPadding = vPadding;
    }

    public WMargin(int padding, AbsWidget... widgets) {
        super(widgets);
        this.lPadding = padding;
        this.tPadding = padding;
        this.rPadding = padding;
        this.bPadding = padding;
    }

}
