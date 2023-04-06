package physics;

public class Collision {
    public final Polygon collider;
    public final int line;

    public Collision(Polygon c, int l) {
        collider = c;
        line = l;
    }

    public Vector2D getNormal() {
        return collider.getLineNormal(line);
    }

    public boolean matches(Collision o) {
        return collider.equals(o.collider) && line == o.line;
    }
}
