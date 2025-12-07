package physics.math.vector;

import java.io.Serializable;

public class Vector2F implements Serializable {
    public float x;
    public float y;

    public static final Vector2F ZERO = new Vector2F(0f, 0f);
    public static final Vector2F ONE = new Vector2F(1f, 1f);
    public static final Vector2F UP = new Vector2F(0f, 1f);
    public static final Vector2F DOWN = new Vector2F(0f, -1f);
    public static final Vector2F LEFT = new Vector2F(-1f, 0f);
    public static final Vector2F RIGHT = new Vector2F(1f, 0f);

    public Vector2F() {
        this.x = 0f;
        this.y = 0f;
    }

    public Vector2F(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2F(Vector2F v) {
        this.x = v.x;
        this.y = v.y;
    }

    public Vector2F added(Vector2F v) {
        return new Vector2F(x + v.x, y + v.y);
    }

    public Vector2F subbed(Vector2F v) {
        return new Vector2F(x - v.x, y - v.y);
    }

    public Vector2F scaled(float s) {
        return new Vector2F(x * s, y * s);
    }

    public Vector2F negated() {
        return new Vector2F(-x, -y);
    }

    public Vector2F absed() {
        return new Vector2F(Math.abs(x), Math.abs(y));
    }

    public Vector2F clamped(float min, float max) {
        return new Vector2F(
                Math.max(min, Math.min(max, x)),
                Math.max(min, Math.min(max, y))
        );
    }

    public Vector2F lerped(Vector2F target, float t) {
        return new Vector2F(
                x + (target.x - x) * t,
                y + (target.y - y) * t
        );
    }

    public Vector2F set(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public Vector2F set(Vector2F v) {
        this.x = v.x;
        this.y = v.y;
        return this;
    }

    public Vector2F add(Vector2F v) {
        this.x += v.x;
        this.y += v.y;
        return this;
    }

    public Vector2F sub(Vector2F v) {
        this.x -= v.x;
        this.y -= v.y;
        return this;
    }

    public Vector2F scale(float s) {
        this.x *= s;
        this.y *= s;
        return this;
    }

    public Vector2F negate() {
        this.x = -x;
        this.y = -y;
        return this;
    }

    public Vector2F abs() {
        this.x = Math.abs(x);
        this.y = Math.abs(y);
        return this;
    }

    public Vector2F clamp(float min, float max) {
        this.x = Math.max(min, Math.min(max, x));
        this.y = Math.max(min, Math.min(max, y));
        return this;
    }

    public float dot(Vector2F v) {
        return x * v.x + y * v.y;
    }

    public float lengthSquared() {
        return x * x + y * y;
    }

    public float length() {
        return (float) Math.sqrt(x * x + y * y);
    }

    public float distanceSquared(Vector2F v) {
        float dx = v.x - x;
        float dy = v.y - y;
        return dx * dx + dy * dy;
    }

    public float distance(Vector2F v) {
        return (float) Math.sqrt(distanceSquared(v));
    }

    public Vector2F normalize() {
        float len = length();
        if (len == 0f) return this;
        x /= len;
        y /= len;
        return this;
    }

    public Vector2F normalized() {
        float len = length();
        if (len == 0f) return new Vector2F();
        return new Vector2F(x / len, y / len);
    }

    public Vector2D toDouble() {
        return new Vector2D(x, y);
    }

    public Vector2I toInt() {
        return new Vector2I((int) x, (int) y);
    }

    @Override
    public String toString() {
        return "Vector2F(" + x + ", " + y + ")";
    }
}


