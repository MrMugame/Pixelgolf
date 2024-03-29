package game.physics;

import physics.Polygon;

public class Custom extends StaticPhysicsComponent {
    private Polygon polygon;

    public Custom(Polygon polygon) {
        this.polygon = polygon;
    }

    @Override
    public Polygon getPolygon() {
        return polygon;
    }
}
