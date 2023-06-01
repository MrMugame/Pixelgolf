package physics;

import java.util.Formattable;
import java.util.Formatter;

public class Vector2D {
    public float x;
    public float y;

    public Vector2D() {
        this.x = 0;
        this.y = 0;
    }

    public Vector2D(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float dot(Vector2D p) {
        return p.x * x + p.y * y;
    }

    public Vector2D add(Vector2D p) {
        return new Vector2D(x + p.x, y + p.y);
    }

    public Vector2D sub(Vector2D p) {
        return new Vector2D(x - p.x, y - p.y);
    }

    public Vector2D scale(float a) {
        return new Vector2D(a * x, a * y);
    }

    public float magnitude() {
        return (float) Math.sqrt(x*x + y*y);
    }

    public float magnitudeSquared() {
        return x*x + y*y;
    }

    public Vector2D invert() {
        return this.scale(-1f);
    }

    public Vector2D normalize() {
        return this.scale(1/this.magnitude());
    }

    public Vector2D invertY() {
        return new Vector2D(x, -y);
    }

    public Vector2D normal() {
        return new Vector2D( -y, x);
    }

    public float getAngle() {
        return (float) Math.acos(-x / magnitude()) * Math.signum(y);
    }

    public boolean isNullVector() {
        return x == 0 && y == 0;
    }

    public Vector2D invertX() {
        return new Vector2D(-x, y);
    }
}
