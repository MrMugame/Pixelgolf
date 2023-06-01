package game.physics;

import game.Component;
import game.GameObject;
import physics.Collider;
import physics.Collision;
import physics.Polygon;
import physics.Vector2D;

import java.util.ArrayList;

public abstract class ActivePhysicsComponent extends Component {
    public Vector2D velocity = new Vector2D();
    public Vector2D force = new Vector2D();
    public float mass;

    public ActivePhysicsComponent(float m) {
        mass = m;
    }

    @Override
    public void update(float dt) {
        Vector2D acceleration = force.scale(1/mass);
        velocity = velocity.add(acceleration.scale(dt/1000));
        parent.getTransform().translate(velocity.scale(dt/1000));

        force = new Vector2D();
    }

    public abstract void testCollision(ArrayList<Collider> colliders);

    public void applyForce(Vector2D f) {
        force = force.add(f);
    }
}