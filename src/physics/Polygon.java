package physics;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class Polygon {
    private ArrayList<Vector2D> points = new ArrayList<>();;

    public Vector2D translation = new Vector2D();
    public boolean closed;

    public Polygon(boolean c) {
        closed = c;
    }

    public void addPoint(Vector2D p) {
        points.add(p);
    }

    public ArrayList<Collision> collisionCircle(Vector2D c, float radius) {
        ArrayList<Collision> collisions = new ArrayList<>();
        for (int i = 0; i < (closed ? points.size() : points.size()-1); i++) {
            Vector2D a = points.get(i).add(translation);
            Vector2D b = points.get(i+1 >= points.size() ? 0 : i+1).add(translation);
            if (CollisionMath.lineCircle(a, b, c, radius)) collisions.add(new Collision(this, i));
        }
        return collisions;
    }

    public Vector2D getLineNormal(int index) {
        Vector2D a = points.get(index);
        Vector2D b = points.get(index+1 >= points.size() ? 0 : index+1);
        return b.sub(a).normal();
    }

    public void setTranslation(Vector2D translation) {
        this.translation = translation;
    }
}
