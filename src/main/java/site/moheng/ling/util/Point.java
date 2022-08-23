package site.moheng.ling.util;

public record Point(int x, int y) {
    public static final Point ZERO = new Point(0, 0);

    public static Point of(int x, int y) {
        return new Point(x, y);
    }

    public static Point of(float x, float y) {
        return new Point((int)x, (int)y);
    }

    public static Point of(double x, double y) {
        return new Point((int)x, (int)y);
    }

    public Point add(Point point) {
        return new Point(x + point.x, y + point.y);
    }

    public Point reduce(Point point) {
        return new Point(x - point.x, y - point.y);
    }

    public Point multiply(float scale) {
        return Point.of(x * scale, y * scale);
    }
}
