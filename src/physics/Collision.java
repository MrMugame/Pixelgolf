package physics;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Collision collision = (Collision) o;
        return line == collision.line && Objects.equals(collider, collision.collider);
    }

    @Override
    public int hashCode() {
        return Objects.hash(collider, line);
    }
}
