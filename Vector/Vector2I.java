package physics.math;

import java.io.Serializable;

public class Vector2I implements Serializable {
    public int x;
    public int y;

    public static final Vector2I ZERO = new Vector2I(0, 0);
    public static final Vector2I ONE = new Vector2I(1, 1);
    public static final Vector2I UP = new Vector2I(0, 1);
    public static final Vector2I DOWN = new Vector2I(0, -1);
    public static final Vector2I LEFT = new Vector2I(-1, 0);
    public static final Vector2I RIGHT = new Vector2I(1, 0);

    public Vector2I() {
        this.x = 0;
        this.y = 0;
    }

    public Vector2I(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vector2I(Vector2I v) {
        this.x = v.x;
        this.y = v.y;
    }

    // ---- PURE OPS (return new instance) ----

    public Vector2I added(Vector2I v) {
        return new Vector2I(x + v.x, y + v.y);
    }

    public Vector2I subbed(Vector2I v) {
        return new Vector2I(x - v.x, y - v.y);
    }

    public Vector2I scaled(int s) {
        return new Vector2I(x * s, y * s);
    }

    public Vector2I negated() {
        return new Vector2I(-x, -y);
    }

    public Vector2I absed() {
        return new Vector2I(Math.abs(x), Math.abs(y));
    }

    public Vector2I clamped(int min, int max) {
        return new Vector2I(
                Math.max(min, Math.min(max, x)),
                Math.max(min, Math.min(max, y))
        );
    }

    // ---- MUTATING OPS ----

    public Vector2I set(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public Vector2I set(Vector2I v) {
        this.x = v.x;
        this.y = v.y;
        return this;
    }

    public Vector2I add(Vector2I v) {
        this.x += v.x;
        this.y += v.y;
        return this;
    }

    public Vector2I sub(Vector2I v) {
        this.x -= v.x;
        this.y -= v.y;
        return this;
    }

    public Vector2I scale(int s) {
        this.x *= s;
        this.y *= s;
        return this;
    }

    public Vector2I negate() {
        this.x = -x;
        this.y = -y;
        return this;
    }

    public Vector2I abs() {
        this.x = Math.abs(x);
        this.y = Math.abs(y);
        return this;
    }

    public Vector2I clamp(int min, int max) {
        this.x = Math.max(min, Math.min(max, x));
        this.y = Math.max(min, Math.min(max, y));
        return this;
    }

    // ---- SCALAR MATH ----

    public int dot(Vector2I v) {
        return x * v.x + y * v.y;
    }

    public int lengthSquared() {
        return x * x + y * y;
    }

    public double length() {
        return Math.sqrt(x * x + y * y);
    }

    public int distanceSquared(Vector2I v) {
        int dx = v.x - x;
        int dy = v.y - y;
        return dx * dx + dy * dy;
    }

    public double distance(Vector2I v) {
        return Math.sqrt(distanceSquared(v));
    }

    // ---- CONVERSION ----

    public Vector2D toDouble() {
        return new Vector2D(x, y);
    }

    public Vector2F toFloat() {
        return new Vector2F(x, y);
    }

    @Override
    public String toString() {
        return "Vector2I(" + x + ", " + y + ")";
    }
}
