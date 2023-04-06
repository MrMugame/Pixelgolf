package physics;

import game.GameObject;
import game.physics.ActivePhysicsComponent;
import game.physics.PhysicsComponent;
import game.physics.StaticPhysicsComponent;

import java.util.ArrayList;

public class Physics {
    private ArrayList<ActivePhysicsComponent> activeComponents = new ArrayList<>();
    private ArrayList<StaticPhysicsComponent> staticComponents = new ArrayList<>();

    public Physics() {}

    public void add(GameObject c) {
        PhysicsComponent p = c.getPhysicsComponent();

        if (p instanceof ActivePhysicsComponent) {
            activeComponents.add((ActivePhysicsComponent) p);
        } else if (p instanceof StaticPhysicsComponent) {
            staticComponents.add((StaticPhysicsComponent) p);
        }
    }

    public void update() {
        ArrayList<Polygon> polygons = new ArrayList<>();
        for (StaticPhysicsComponent passive : staticComponents) {
            passive.getPolygon().setTranslation(passive.parent.getTransform().position);
            polygons.add(passive.getPolygon());
        }

        for (ActivePhysicsComponent active : activeComponents) {
            active.testCollision(polygons);
        }
    }
}
