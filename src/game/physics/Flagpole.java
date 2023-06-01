package game.physics;

import game.physics.StaticPhysicsComponent;
import physics.Polygon;
import physics.Vector2D;


public class Flagpole extends StaticPhysicsComponent {

    private Polygon polygon;

    public Flagpole(float size) {
        // TODO: Change to an init method so you dont have size parameter
        polygon = new Polygon();
        polygon.addPoint(new Vector2D(size/2-0.1f, -size/2+0.1f));
        polygon.addPoint(new Vector2D(size/2-0.1f, -size/2-0.1f));
        polygon.addPoint(new Vector2D(size/2+0.1f, -size/2-0.1f));
        polygon.addPoint(new Vector2D(size/2+0.1f, -size/2+0.1f));
    }

    @Override
    public Polygon getPolygon() {
        return polygon;
    }
}
