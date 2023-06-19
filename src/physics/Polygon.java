package physics;

import java.util.ArrayList;

public class Polygon {
    private ArrayList<Vector2D> points = new ArrayList<>();;

    private Vector2D translation = new Vector2D();
    private float rotation = 0;

    public Polygon() {}

    public void addPoint(Vector2D p) {
        points.add(p);
    }

    public ArrayList<Collision> collisionCircle(Vector2D c, float radius) {
        ArrayList<Collision> collisions = new ArrayList<>();
        for (int i = 0; i < points.size(); i++) {
            Vector2D a = points.get(i).rotate(-rotation).add(translation);
            Vector2D b = points.get(i+1 >= points.size() ? 0 : i+1).rotate(-rotation).add(translation);
            if (CollisionMath.lineCircle(a, b, c, radius)) collisions.add(new Collision(this, i));
        }
        return collisions;
    }

    public Vector2D getLineNormal(int index) {
        Vector2D a = points.get(index).rotate(-rotation);
        Vector2D b = points.get(index+1 >= points.size() ? 0 : index+1).rotate(-rotation);

        // Komisches Verhalten an convexen Ecken, wenn der Ball quasi mit beiden Linien gleichzeitig kollidiert und somit in die gleiche Richtung zurückgespiegelt wird. Könnte man fixen, ist jedoch nervig und kostet Zeit (Deswegen: Niemals selber Physik implementieren)
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
