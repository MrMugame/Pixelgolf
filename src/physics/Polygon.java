package physics;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import static game.Transform.rotateVector;

public class Polygon {
    private ArrayList<Vector2D> points = new ArrayList<>();;

    private Vector2D translation = new Vector2D();
    private float rotation = 0;
    // TODO: Rename cause of awt polygon

    public Polygon() {}

    public void addPoint(Vector2D p) {
        points.add(p);
    }

    public ArrayList<Collision> collisionCircle(Vector2D c, float radius) {
        ArrayList<Collision> collisions = new ArrayList<>();
        for (int i = 0; i < points.size(); i++) {
            Vector2D a = rotateVector(points.get(i), rotation).add(translation);
            Vector2D b = rotateVector(points.get(i+1 >= points.size() ? 0 : i+1), rotation).add(translation);
            if (CollisionMath.lineCircle(a, b, c, radius)) collisions.add(new Collision(this, i));
        }
        return collisions;
    }

    // TODO: Make behaviour at convex corners a little better
    public Vector2D getLineNormal(int index) {
        Vector2D a = points.get(index);
        Vector2D b = points.get(index+1 >= points.size() ? 0 : index+1);
        return b.sub(a).normal();
    }

    public void setTranslation(Vector2D translation) {
        this.translation = translation;
    }
    public void setRotation(float rotation) {
        this.rotation = rotation;
    }


    public java.awt.Polygon toAWTPolygon(int scalar) {
        java.awt.Polygon poly = new java.awt.Polygon();

        for (Vector2D point : points) {
            poly.addPoint((int) (point.x * scalar), (int) (-point.y * scalar));
        }

        return poly;
    }
}
