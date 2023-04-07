package game.physics;

import input.KeyboardListener;
import input.MouseListener;
import physics.Collision;
import physics.Polygon;
import physics.Vector2D;

import java.util.ArrayList;

import static java.awt.event.KeyEvent.VK_SPACE;

public class BallPhysics extends ActivePhysicsComponent {
    public ArrayList<Collision> collisions = new ArrayList<>();

    public BallPhysics(float mass) {
        super(mass);
    }

    @Override
    public void update(float dt) {
        // Air Resistance
        //applyForce(velocity.scale(-1 * 0.5f * 1.2f * velocity.magnitude() * 0.47f * 0.554f));
        // Friction
        //applyForce(velocity.normalize().scale(-0.3f));

        super.update(dt);

    }

    @Override
    public void testCollision(ArrayList<Polygon> polygons) {
        Vector2D center = parent.getTransform().getCenter();

        // TODO: Refactor cause this is awful
        for (Polygon p : polygons) {
            ArrayList<Collision> c = p.collisionCircle(center, parent.getTransform().size.x / 2);
            if (!c.isEmpty()) {
                for (Collision collision : c) {
                    if (!isColliding(collision)) {
                        Vector2D n = collision.getNormal().normalize();
                        velocity = velocity.sub(n.scale(2.0f * velocity.dot(n)));
                        collisions.add(collision);
                    }
                }

                ArrayList<Collision> found = new ArrayList<>();
                outer: for (Collision collision : collisions) {
                    if (!collision.collider.equals(p)) continue;
                    for (Collision k : c) {
                        if (collision.matches(k)) continue outer;
                    }
                    found.add(collision);
                }
                collisions.removeAll(found);
            } else {
                clearCollisionsPolygon(p);
            }
        }
    }

    public void clearCollisionsPolygon(Polygon p) {
        ArrayList<Collision> found = new ArrayList<>();
        for (Collision c : collisions) {
            if (c.collider.equals(p)) found.add(c);
        }
        collisions.removeAll(found);
    }

    public boolean isColliding(Collision other) {
        for (Collision c : collisions) {
            if (c.matches(other)) return true;
        }
        return false;
    }
}
