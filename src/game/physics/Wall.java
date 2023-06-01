package game.physics;

import physics.Polygon;

public class Wall extends StaticPhysicsComponent {

    private Polygon polygon;

    public Wall(Polygon polygon) {
        this.polygon = polygon;
    }

    @Override
    public Polygon getPolygon() {
        return polygon;
    }
}
