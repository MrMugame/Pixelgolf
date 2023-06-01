package game.physics;

import game.Component;
import physics.Polygon;

public abstract class StaticPhysicsComponent extends Component {
    public abstract Polygon getPolygon();
}
