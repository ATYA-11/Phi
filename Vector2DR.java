package physics.math;

import java.io.Serializable;

public record Vector2DR(double x, double y) implements Serializable {

    public static final Vector2DR ZERO = new Vector2DR(0.0, 0.0);
    public static final Vector2DR ONE = new Vector2DR(1.0, 1.0);
    public static final Vector2DR UP = new Vector2DR(0.0, 1.0);
    public static final Vector2DR DOWN = new Vector2DR(0.0, -1.0);
    public static final Vector2DR LEFT = new Vector2DR(-1.0, 0.0);
    public static final Vector2DR RIGHT = new Vector2DR(1.0, 0.0);

    // ---- PURE OPERATIONS ----

    public Vector2DR added(Vector2DR v) {
        return new Vector2DR(x + v.x, y + v.y);
    }

    public Vector2DR subbed(Vector2DR v) {
        return new Vector2DR(x - v.x, y - v.y);
    }

    public Vector2DR scaled(double s) {
        return new Vector2DR(x * s, y * s);
    }

    public Vector2DR negated() {
        return new Vector2DR(-x, -y);
    }

    public Vector2DR absed() {
        return new Vector2DR(Math.abs(x), Math.abs(y));
    }

    public Vector2DR clamped(double min, double max) {
        return new Vector2DR(
                Math.max(min, Math.min(max, x)),
                Math.max(min, Math.min(max, y))
        );
    }

    public Vector2DR lerped(Vector2DR target, double t) {
        return new Vector2DR(
                x + (target.x - x) * t,
                y + (target.y - y) * t
        );
    }

    // ---- MATH ----

    public double dot(Vector2DR v) {
        return x * v.x + y * v.y;
    }

    public double lengthSquared() {
        return x * x + y * y;
    }

    public double length() {
        return Math.sqrt(x * x + y * y);
    }

    public double distanceSquared(Vector2DR v) {
        double dx = v.x - x;
        double dy = v.y - y;
        return dx * dx + dy * dy;
    }

    public double distance(Vector2DR v) {
        return Math.sqrt(distanceSquared(v));
    }

    public Vector2DR normalized() {
        double len = length();
        if (len == 0.0) return ZERO;
        return new Vector2DR(x / len, y / len);
    }

    // ---- CONVERSION ----

    public Vector2D toMutableD() {
        return new Vector2D(x, y);
    }

    public Vector2F toMutableF() {
        return new Vector2F((float) x, (float) y);
    }

    public Vector2I toMutableI() {
        return new Vector2I((int) x, (int) y);
    }
}
