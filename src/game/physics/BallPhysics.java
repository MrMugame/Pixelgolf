package game.physics;

import assets.Assets;
import game.GameObject;
import graphics.GameWindow;
import physics.Collider;
import physics.Collision;
import physics.Polygon;
import physics.Vector2D;
import scenes.levels.Level;
import sound.SoundSystem;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class BallPhysics extends ActivePhysicsComponent {
    public ArrayList<Collision> collisions = new ArrayList<>();
    private GameObject justTPd; // Hässlich

    public BallPhysics(float mass) {
        super(mass);
    }

    @Override
    public void update(float dt) {
        // -- Patrick // TODO
        // Luftwiederstand
        applyForce(velocity.scale(-1 * 0.5f * 1.2f * velocity.magnitude() * 0.47f * 0.554f));
        // Reibung
        if (!velocity.isNullVector()) {
            applyForce(velocity.normalize().scale(-5.5f));
        }

        if (justTPd != null) {
            justTPd = justTPd.getTransform().getCenter().sub(parent.getTransform().position).magnitudeSquared() > justTPd.getTransform().size.magnitudeSquared() * 0.3f ? null : justTPd;
        }

        super.update(dt);
    }

    @Override
    public void testCollision(ArrayList<Collider> colliders) {
        ArrayList<Collision> currentCollisions = new ArrayList<>();
        for (Collider c : colliders) {
            ArrayList<Collision> newCollisions = c.polygon.collisionCircle(parent.getTransform().position, parent.getTransform().size.x / 2);

            for (Collision collision : newCollisions) {
                if (!collisions.contains(collision)) {
                    switch (c.gameObject.get(Material.class).getMaterial()) {
                        case WALL:
                            Vector2D n = collision.getNormal().normalize();
                            velocity = velocity.sub(n.scale(2.0f * velocity.dot(n)));
                            velocity = velocity.scale(0.75f); // -- Patrick

                            SoundSystem.get().play(Assets.loadSound("sound/wall_bump.wav"));
                            break;
                        case FLAGPOLE:
                            SoundSystem.get().play(Assets.loadSound("sound/win_sound.wav"));

                            ((Level) GameWindow.get().getScene()).won();
                            break;
                        case SINKHOLE:
                            velocity = new Vector2D();
                            parent.getTransform().position = parent.get(Resetpoint.class).getReset();
                            ((Level) GameWindow.get().getScene()).getLogic().reset();

                            SoundSystem.get().play(Assets.loadSound("sound/die_sound.wav"));
                            break;
                        case ICEPUDDLE:
                            // TODO
                            float range = 0.075f * (float)Math.PI;
                            velocity = velocity.rotate((float) Math.random() * (range*2) - range);
                            break;
                        case SLOWPUDDLE:
                            velocity = velocity.scale(0.85f);
                            break;
                        case PORTAL:
                            if (justTPd == null) {
                                // Sehr, alles sehr unschön // TODO

                                ArrayList<GameObject> objects = GameWindow.get().getScene().getGameObjects(Portal.class);
                                for (GameObject object : objects) {
                                    if (object.get(Portal.class).getId() == c.gameObject.get(Portal.class).getId() && object != c.gameObject) {
                                        parent.getTransform().position = object.getTransform().getCenter();
                                        justTPd = object;

                                        SoundSystem.get().play(Assets.loadSound("sound/die_sound.wav"));
                                        break;
                                    }
                                }

                                velocity = new Vector2D();
                            }
                            break;
                    }
                }
                currentCollisions.add(collision);
            }
        }

        collisions = currentCollisions;
    }
}
