package game.physics;

import physics.Polygon;
import physics.Vector2D;

public class Rectangle extends StaticPhysicsComponent {
    private Polygon polygon;

    public Rectangle() {}

    @Override
    public Polygon getPolygon() {
        if (polygon == null) {
            float width = parent.getTransform().size.x;
            float height = parent.getTransform().size.y;
            polygon = new Polygon();
            polygon.addPoint(new Vector2D(0, 0));
            polygon.addPoint(new Vector2D(0, -height));
            polygon.addPoint(new Vector2D(width, -height));
            polygon.addPoint(new Vector2D(width, 0));
        }
        return polygon;
    }
}
