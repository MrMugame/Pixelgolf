package game.physics;

import game.Component;
import physics.Polygon;

public abstract class StaticPhysicsComponent extends Component {
    private Polygon polygon;

    public StaticPhysicsComponent(Polygon poly) {
        polygon = poly;
    }

    public Polygon getPolygon() {
        return polygon;
    }
}
