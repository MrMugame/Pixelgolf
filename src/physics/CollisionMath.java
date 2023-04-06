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

/*    private static boolean pointLine(Physics.Vector2D line1, Physics.Vector2D line2, Physics.Vector2D point) {
        // d1+d2 == len
        // (d1+d2)^2 == len^2
        // d1^2 + 2d1d2 + d2^2 == len^2
        // d1^2 + 2sqrt(d1^2*d2^2) + d2^2 == len^2
        float d1 = point.sub(line1).magnitudeSquared();
        float d2 = point.sub(line2).magnitudeSquared();
        float combined = d1 + 2 * (float) Math.sqrt(d1*d2) + d2;
        float len = line1.sub(line2).magnitudeSquared();

        float inaccuracy = 0.1f;
        return (combined >= len-inaccuracy && combined <= len+inaccuracy);
    }*/
}
