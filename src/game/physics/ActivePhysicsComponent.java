package game.physics;

import game.Component;
import input.KeyboardListener;
import input.MouseListener;
import physics.Polygon;
import physics.Vector2D;

import java.util.ArrayList;

import static java.awt.event.KeyEvent.*;

public abstract class ActivePhysicsComponent extends PhysicsComponent {
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

    public abstract void testCollision(ArrayList<Polygon> p);

    public void applyForce(Vector2D f) {
        force = force.add(f);
    }
}