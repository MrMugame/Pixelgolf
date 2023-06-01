package game.physics;

import game.GameObject;
import graphics.GameWindow;
import physics.Collider;
import physics.Collision;
import physics.Polygon;
import physics.Vector2D;
import scenes.levels.Level;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class BallPhysics extends ActivePhysicsComponent {
    public ArrayList<Collision> collisions = new ArrayList<>();

    public BallPhysics(float mass) {
        super(mass);
    }

    @Override
    public void update(float dt) {
        // -- Patrick
        // Luftwiederstand
        applyForce(velocity.scale(-1 * 0.5f * 1.2f * velocity.magnitude() * 0.47f * 0.554f));
        // Reibung
        if (!velocity.isNullVector()) {
            applyForce(velocity.normalize().scale(-5.5f));
        }

        super.update(dt);

    }

    @Override
    public void testCollision(ArrayList<Collider> colliders) {
        Vector2D center = parent.getTransform().getCenter();
        // TODO: URGH
        ArrayList<Collision> currentCollisions = new ArrayList<>();
        for (Collider c : colliders) {
            ArrayList<Collision> newCollisions = c.polygon.collisionCircle(center, parent.getTransform().size.x / 2);

            for (Collision collision : newCollisions) {
                if (!collisions.contains(collision)) {
                    if (c.gameObject.get(Flagpole.class) != null) {
                        ((Level) GameWindow.get().getScene()).won();
                        continue;
                    }
                    Vector2D n = collision.getNormal().normalize();
                    velocity = velocity.sub(n.scale(2.0f * velocity.dot(n)));
                    velocity = velocity.scale(0.75f); // -- Patrick
                }
                currentCollisions.add(collision);
            }
        }

        collisions = currentCollisions;
    }
}
