package game.physics;

import physics.Polygon;
import physics.Vector2D;

public class Sinkhole extends StaticPhysicsComponent { // TODO: Dont like this, should be a custom path defined in xml
    private Polygon polygon;
    private Vector2D reset;

    public Sinkhole(Vector2D reset) {
        this.reset = reset;
    }

    public Vector2D getReset() {
        return reset;
    }

    @Override
    public Polygon getPolygon() {
        if (polygon == null) {
            float width = parent.getTransform().size.x;
            float height = parent.getTransform().size.y;
            polygon = new Polygon();
            polygon.addPoint(new Vector2D(0.5f*width, -0.14f*height));
            polygon.addPoint(new Vector2D(0.9f*width, -0.57f*height));
            polygon.addPoint(new Vector2D(0.46f*width, -0.96f*height));
            polygon.addPoint(new Vector2D(0.09f*width, -0.77f*height));
            polygon.addPoint(new Vector2D(0.09f*width, -0.32f*height));
        }
        return polygon;
    }
}
