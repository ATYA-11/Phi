package physics.math.vector;
 
public final class VectorMath {

    private VectorMath() {}

        public static double dot(double ax, double ay, double bx, double by) {
        return ax * bx + ay * by;
    }

    public static double length(double x, double y) {
        return Math.sqrt(x * x + y * y);
    }

    public static double lengthSquared(double x, double y) {
        return x * x + y * y;
    }

    public static double distance(double ax, double ay, double bx, double by) {
        double dx = bx - ax;
        double dy = by - ay;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public static double distanceSquared(double ax, double ay, double bx, double by) {
        double dx = bx - ax;
        double dy = by - ay;
        return dx * dx + dy * dy;
    }

    public static double lerp(double a, double b, double t) {
        return a + (b - a) * t;
    }

    public static double clamp(double v, double min, double max) {
        return Math.max(min, Math.min(max, v));
    }

    public static Vector2D sum(Vector2D[] arr) {
        if (arr == null || arr.length == 0) return Vector2D.ZERO.clone();
        double sx = 0.0, sy = 0.0;
        for (Vector2D v : arr) {
            if (v != null) {
                sx += v.getX();
                sy += v.getY();
            }
        }
        return new Vector2D(sx, sy);
    }

    public static Vector2F sum(Vector2F[] arr) {
        if (arr == null || arr.length == 0) return new Vector2F(0f, 0f);
        float sx = 0f, sy = 0f;
        for (Vector2F v : arr) {
            if (v != null) {
                sx += v.x;
                sy += v.y;
            }
        }
        return new Vector2F(sx, sy);
    }

    public static Vector2I sum(Vector2I[] arr) {
        if (arr == null || arr.length == 0) return new Vector2I(0, 0);
        int sx = 0, sy = 0;
        for (Vector2I v : arr) {
            if (v != null) {
                sx += v.x;
                sy += v.y;
            }
        }
        return new Vector2I(sx, sy);
    }

    public static void scaleAll(Vector2D[] arr, double s) {
        if (arr == null) return;
        for (Vector2D v : arr) {
            if (v != null) v.mul(s);
        }
    }

    public static void scaleAll(Vector2F[] arr, float s) {
        if (arr == null) return;
        for (Vector2F v : arr) {
            if (v != null) v.scale(s);
        }
    }

    public static void scaleAll(Vector2I[] arr, int s) {
        if (arr == null) return;
        for (Vector2I v : arr) {
            if (v != null) v.scale(s);
        }
    }

    public static void normalizeAll(Vector2D[] arr) {
        if (arr == null) return;
        for (Vector2D v : arr) {
            if (v != null) v.normalizeInPlace();
        }
    }

    public static void normalizeAll(Vector2F[] arr) {
        if (arr == null) return;
        for (Vector2F v : arr) {
            if (v != null) v.normalize();
        }
    }

    public static void lerpAll(Vector2D[] a, Vector2D[] b, Vector2D[] out, double t) {
        if (a == null || b == null || out == null) throw new IllegalArgumentException("arrays must not be null");
        if (a.length != b.length || a.length != out.length) throw new IllegalArgumentException("array lengths must match");
        for (int i = 0; i < out.length; i++) {
            Vector2D ai = a[i];
            Vector2D bi = b[i];
            if (out[i] == null) out[i] = new Vector2D();
            if (ai == null && bi == null) { out[i].set(0.0, 0.0); continue; }
            double ax = (ai == null) ? 0.0 : ai.getX();
            double ay = (ai == null) ? 0.0 : ai.getY();
            double bx = (bi == null) ? 0.0 : bi.getX();
            double by = (bi == null) ? 0.0 : bi.getY();
            out[i].set(
                ax + (bx - ax) * t,
                ay + (by - ay) * t
            );
        }
    }

    public static void lerpAll(Vector2F[] a, Vector2F[] b, Vector2F[] out, float t) {
        if (a == null || b == null || out == null) throw new IllegalArgumentException("arrays must not be null");
        if (a.length != b.length || a.length != out.length) throw new IllegalArgumentException("array lengths must match");
        for (int i = 0; i < out.length; i++) {
            Vector2F ai = a[i];
            Vector2F bi = b[i];
            if (out[i] == null) out[i] = new Vector2F();
            if (ai == null && bi == null) { out[i].set(0f, 0f); continue; }
            float ax = (ai == null) ? 0f : ai.x;
            float ay = (ai == null) ? 0f : ai.y;
            float bx = (bi == null) ? 0f : bi.x;
            float by = (bi == null) ? 0f : bi.y;
            out[i].set(
                ax + (bx - ax) * t,
                ay + (by - ay) * t
            );
        }
    }
}

