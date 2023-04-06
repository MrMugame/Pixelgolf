package game.physics;

import physics.Polygon;

public abstract class StaticPhysicsComponent extends PhysicsComponent {
    private Polygon polygon;

    public StaticPhysicsComponent(Polygon poly) {
        polygon = poly;
    }

    public Polygon getPolygon() {
        return polygon;
    }
}
