package game.physics;

import game.physics.StaticPhysicsComponent;
import physics.Polygon;
import physics.Vector2D;


public class Flagpole extends StaticPhysicsComponent {

    private Polygon polygon;

    public Flagpole() {}

    @Override
    public Polygon getPolygon() {
        if (polygon == null) {
            float width = parent.getTransform().size.x;
            float height = parent.getTransform().size.y;
            polygon = new Polygon();
            polygon.addPoint(new Vector2D(width/2-0.1f, -height/2+0.1f));
            polygon.addPoint(new Vector2D(width/2-0.1f, -height/2-0.1f));
            polygon.addPoint(new Vector2D(width/2+0.1f, -height/2-0.1f));
            polygon.addPoint(new Vector2D(width/2+0.1f, -height/2+0.1f));
        }
        return polygon;
    }
}
