package physics;

public class CollisionMath {
    public static boolean lineCircle(Vector2D point1, Vector2D point2, Vector2D circle, float radius) {
        if (pointCircle(circle, radius, point1) || pointCircle(circle, radius, point2)) return true;

        Vector2D b = point2.sub(point1);
        Vector2D a = circle.sub(point1);

        float factor = a.dot(b) / b.dot(b);

        if (factor <= 0 || factor >= 1) return false;

        Vector2D projectedA = b.scale(factor);
        Vector2D p = point1.add(projectedA);

        return pointCircle(circle, radius, p);
    }

    public static boolean pointCircle(Vector2D circle, float radius, Vector2D point) {
        return circle.sub(point).magnitudeSquared() < radius*radius;
    }
}
