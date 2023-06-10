package game.physics;

import physics.Polygon;
import physics.Vector2D;

public class Triangle extends StaticPhysicsComponent {
    private Polygon polygon;

    public Triangle() {}

    @Override
    public Polygon getPolygon() {
        if (polygon == null) {
            float width = parent.getTransform().size.x;
            float height = parent.getTransform().size.y;
            polygon = new Polygon();
            polygon.addPoint(new Vector2D(0, 0));
            polygon.addPoint(new Vector2D(0, -height));
            polygon.addPoint(new Vector2D(-width, 0));
        }
        return polygon;
    }
}
