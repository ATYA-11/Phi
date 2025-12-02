package physics.math;

import java.util.Objects;
import java.util.function.DoubleUnaryOperator;

public class Vector2D implements Cloneable, Comparable<Vector2D> {

    // ---------------------------------------------------------------------
    // Constants & configuration
    // ---------------------------------------------------------------------
    public static final Vector2D ZERO    = new Vector2D(0.0, 0.0);
    public static final Vector2D ONE     = new Vector2D(1.0, 1.0);
    public static final Vector2D UNIT_X  = new Vector2D(1.0, 0.0);
    public static final Vector2D UNIT_Y  = new Vector2D(0.0, 1.0);
    public static final Vector2D NEG_ONE = new Vector2D(-1.0, -1.0);

    /** Default epsilon for approximate comparisons. */
    public static final double EPS = 1e-9;

    private double x;
    private double y;

    // ---------------------------------------------------------------------
    // Constructors & factories
    // ---------------------------------------------------------------------
    public Vector2D() { this(0.0, 0.0); }
    public Vector2D(double x, double y) { this.x = x; this.y = y; }
    public static Vector2D of(double x, double y) { return new Vector2D(x, y); }

    // ---------------------------------------------------------------------
    // Basic accessors & mutators (pooling friendly)
    // ---------------------------------------------------------------------
    public double getX() { return x; }
    public double getY() { return y; }
    public Vector2D set(double x, double y) { this.x = x; this.y = y; return this; }
    public Vector2D setFrom(Vector2D other) { this.x = other.x; this.y = other.y; return this; }
    public Vector2D reset() { this.x = 0.0; this.y = 0.0; return this; }
    @Override public Vector2D clone() { return new Vector2D(x, y); }

    // ---------------------------------------------------------------------
    // Mutating-first arithmetic (in-place) - hot-loop friendly
    // ---------------------------------------------------------------------
    public Vector2D add(Vector2D o)      { this.x += o.x; this.y += o.y; return this; }
    public Vector2D sub(Vector2D o)      { this.x -= o.x; this.y -= o.y; return this; }
    public Vector2D mul(double s)        { this.x *= s; this.y *= s; return this; }
    public Vector2D div(double s)        { this.x /= s; this.y /= s; return this; }
    public Vector2D hadamard(Vector2D o) { this.x *= o.x; this.y *= o.y; return this; }

    public Vector2D add(double s) { this.x += s; this.y += s; return this; }
    public Vector2D sub(double s) { this.x -= s; this.y -= s; return this; }

    // ---------------------------------------------------------------------
    // Pure variants (return new instance)
    // ---------------------------------------------------------------------
    public Vector2D added(Vector2D o)      { return new Vector2D(this.x + o.x, this.y + o.y); }
    public Vector2D subtracted(Vector2D o) { return new Vector2D(this.x - o.x, this.y - o.y); }
    public Vector2D multiplied(double s)   { return new Vector2D(this.x * s, this.y * s); }
    public Vector2D divided(double s)      { return new Vector2D(this.x / s, this.y / s); }
    public Vector2D hadamarded(Vector2D o) { return new Vector2D(this.x * o.x, this.y * o.y); }

    public Vector2D added(double s) { return new Vector2D(this.x + s, this.y + s); }
    public Vector2D subtracted(double s) { return new Vector2D(this.x - s, this.y - s); }

    // ---------------------------------------------------------------------
    // Length / normalization / fast helpers
    // ---------------------------------------------------------------------
    public double lengthSquared() { return x * x + y * y; }
    public double length() { return Math.sqrt(lengthSquared()); }

    /**
     * Returns 1/length. If length <= EPS returns 0.0 (safe for physics).
     */
    public double invLength() {
        double ls = lengthSquared();
        return ls <= EPS ? 0.0 : 1.0 / Math.sqrt(ls);
    }

    /** Alias for invLength; kept for clarity in hot loops. */
    public double invSqrt() { return invLength(); }

    /**
     * Normalize returning a NEW vector. If length == 0 returns ZERO clone (no NaN).
     */
    public Vector2D normalized() {
        double len = length();
        if (len <= EPS) return ZERO.clone();
        return new Vector2D(x / len, y / len);
    }

    /**
     * Normalize in-place. If length == 0 leaves vector unchanged (Option 1 semantics).
     */
    public Vector2D normalizeInPlace() {
        double len = length();
        if (len <= EPS) return this;
        this.x /= len; this.y /= len;
        return this;
    }

    /**
     * Normalize with fallback: returns new vector normalized or provided fallback clone.
     */
    public Vector2D safeNormalized(Vector2D fallback) {
        double len = length();
        if (len <= EPS) return (fallback == null) ? ZERO.clone() : fallback.clone();
        return new Vector2D(x / len, y / len);
    }

    // ---------------------------------------------------------------------
    // Dot / cross & projection-like helper (algebraic)
    // ---------------------------------------------------------------------
    public double dot(Vector2D o) { return this.x * o.x + this.y * o.y; }
    public double cross(Vector2D o) { return this.x * o.y - this.y * o.x; }
    public double project(Vector2D axis) { return dot(axis); }

    // ---------------------------------------------------------------------
    // Comparisons, tolerances, and sanity checks
    // ---------------------------------------------------------------------
    public boolean isZero() { return x == 0.0 && y == 0.0; }
    public boolean approxEquals(Vector2D o, double eps) {
        return Math.abs(this.x - o.x) <= eps && Math.abs(this.y - o.y) <= eps;
    }
    public boolean approxEquals(Vector2D o) { return approxEquals(o, EPS); }
    public boolean equalsEpsilon(Vector2D o, double eps) { return approxEquals(o, eps); }
    public boolean isFinite() { return Double.isFinite(x) && Double.isFinite(y); }
    public boolean isNaN() { return Double.isNaN(x) || Double.isNaN(y); }

    // ---------------------------------------------------------------------
    // Interpolation & smooth clamps
    // ---------------------------------------------------------------------
    /** Linear interpolation (pure). this*(1-t) + other*t */
    public Vector2D lerped(Vector2D other, double t) {
        return new Vector2D(this.x + (other.x - this.x) * t, this.y + (other.y - this.y) * t);
    }

    /** Linear interpolation in-place. this = this*(1-t) + other*t */
    public Vector2D lerpInPlace(Vector2D other, double t) {
        this.x += (other.x - this.x) * t;
        this.y += (other.y - this.y) * t;
        return this;
    }

    public Vector2D mix(Vector2D other, double t) { return lerped(other, t); }

    public static double clamp01(double t) {
        if (t <= 0.0) return 0.0;
        if (t >= 1.0) return 1.0;
        return t;
    }

    public static double smoothStep(double t) {
        t = clamp01(t);
        return t * t * (3 - 2 * t);
    }

    // ---------------------------------------------------------------------
    // Rounding / snapping / discretization
    // ---------------------------------------------------------------------
    public Vector2D floored() { return new Vector2D(Math.floor(x), Math.floor(y)); }
    public Vector2D ceiled()  { return new Vector2D(Math.ceil(x), Math.ceil(y)); }
    public Vector2D rounded() { return new Vector2D(Math.round(x), Math.round(y)); }
    public Vector2D absed()   { return new Vector2D(Math.abs(x), Math.abs(y)); }
    public Vector2D signed()  { return new Vector2D(Math.signum(x), Math.signum(y)); }
    public Vector2D negated() { return new Vector2D(-x, -y); }
    public Vector2D swapped() { return new Vector2D(y, x); }

    /** Snap components to a grid (pure). If gridSize <= 0 returns clone. */
    public Vector2D snapped(double gridSize) {
        if (gridSize <= 0.0) return clone();
        return new Vector2D(Math.round(this.x / gridSize) * gridSize,
                            Math.round(this.y / gridSize) * gridSize);
    }

    /** Snap components to a grid in-place. */
    public Vector2D snapToGridInPlace(double gridSize) {
        if (gridSize <= 0.0) return this;
        this.x = Math.round(this.x / gridSize) * gridSize;
        this.y = Math.round(this.y / gridSize) * gridSize;
        return this;
    }

    // ---------------------------------------------------------------------
    // Safe division & helpers
    // ---------------------------------------------------------------------
    public Vector2D safeDiv(double s, double defaultValue) {
        return s == 0.0 ? new Vector2D(defaultValue, defaultValue) : new Vector2D(x / s, y / s);
    }

    public Vector2D safeDivInPlace(double s) {
        if (s == 0.0) return this;
        this.x /= s; this.y /= s; return this;
    }

    // ---------------------------------------------------------------------
    // Mapping
    // ---------------------------------------------------------------------
    public Vector2D map(DoubleUnaryOperator fn) {
        return new Vector2D(fn.applyAsDouble(x), fn.applyAsDouble(y));
    }

    public Vector2D mapInPlace(DoubleUnaryOperator fn) {
        this.x = fn.applyAsDouble(this.x);
        this.y = fn.applyAsDouble(this.y);
        return this;
    }

    // ---------------------------------------------------------------------
    // Serialization / conversion
    // ---------------------------------------------------------------------
    public String toCSV() { return x + "," + y; }
    public String toJson() { return "{\"x\":" + x + ",\"y\":" + y + "}"; }
    public double[] toArray() { return new double[]{x, y}; }
    public Vector2F toFloat() { return new Vector2F((float) x, (float) y); }
    public Vector2I toInt() { return new Vector2I((int) x, (int) y); }
    public Vector2DR toRecord() { return new Vector2DR(x, y); }

    // ---------------------------------------------------------------------
    // Statistics / quick helpers
    // ---------------------------------------------------------------------
    public double sum() { return x + y; }
    public double product() { return x * y; }
    public double average() { return (x + y) * 0.5; }
    public double magnitudeMax() { return Math.max(Math.abs(x), Math.abs(y)); }
    public double magnitudeMin() { return Math.min(Math.abs(x), Math.abs(y)); }

    // ---------------------------------------------------------------------
    // Batch static utilities for solvers (operate on arrays)
    // ---------------------------------------------------------------------
    /**
     * dst[i] += src[i] * scale (in-place).
     * Arrays must be same length.
     */
    public static void addScaled(Vector2D[] dst, Vector2D[] src, double scale) {
        if (dst == null || src == null || dst.length != src.length) throw new IllegalArgumentException("arrays");
        for (int i = 0; i < dst.length; i++) {
            dst[i].x += src[i].x * scale;
            dst[i].y += src[i].y * scale;
        }
    }

    /** Multiply all vectors in-place by scalar s. */
    public static void scaleAll(Vector2D[] arr, double s) {
        if (arr == null) return;
        for (int i = 0; i < arr.length; i++) arr[i].mulInPlace(s);
    }

    /** Sum of all vectors (pure). */
    public static Vector2D sum(Vector2D[] arr) {
        if (arr == null || arr.length == 0) return ZERO.clone();
        double sx = 0.0, sy = 0.0;
        for (Vector2D v : arr) { sx += v.x; sy += v.y; }
        return new Vector2D(sx, sy);
    }

    /** Average of vector array (pure). */
    public static Vector2D average(Vector2D[] arr) {
        if (arr == null || arr.length == 0) return ZERO.clone();
        Vector2D s = sum(arr);
        return s.divided(arr.length);
    }

    // ---------------------------------------------------------------------
    // clamp length (pure and in-place)
    // ---------------------------------------------------------------------
    /** Pure clamp length to max (returns new vector). */
    public Vector2D clampedLength(double max) {
        double ls = lengthSquared();
        if (ls <= max * max) return clone();
        double inv = 1.0 / Math.sqrt(ls);
        return new Vector2D(x * inv * max, y * inv * max);
    }

    /** In-place clamp length to max. */
    public Vector2D clampLengthInPlace(double max) {
        double ls = lengthSquared();
        if (ls <= max * max) return this;
        double inv = 1.0 / Math.sqrt(ls);
        this.x *= inv * max;
        this.y *= inv * max;
        return this;
    }

    // ---------------------------------------------------------------------
    // Equals / hash / compare
    // ---------------------------------------------------------------------
    @Override
    public int hashCode() {
        return Objects.hash(Double.valueOf(x), Double.valueOf(y));
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Vector2D)) return false;
        Vector2D o = (Vector2D) obj;
        return Double.compare(o.x, x) == 0 && Double.compare(o.y, y) == 0;
    }

    @Override
    public int compareTo(Vector2D o) {
        return Double.compare(this.lengthSquared(), o.lengthSquared());
    }

    @Override
    public String toString() {
        return "Vector2D(" + x + ", " + y + ")";
    }

    // ---------------------------------------------------------------------
    // Internal helper for direct in-place mul used by scaleAll()
    // (keeps method short and optimized)
    // ---------------------------------------------------------------------
    private Vector2D mulInPlace(double s) { this.x *= s; this.y *= s; return this; }
}

