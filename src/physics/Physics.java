package physics;

import game.GameObject;
import game.physics.ActivePhysicsComponent;
import game.physics.StaticPhysicsComponent;

import java.util.ArrayList;

public class Physics {
    private ArrayList<ActivePhysicsComponent> activeComponents = new ArrayList<>();
    private ArrayList<StaticPhysicsComponent> staticComponents = new ArrayList<>();

    public Physics() {}

    public void add(GameObject object) {
        ActivePhysicsComponent active = object.get(ActivePhysicsComponent.class);
        if (active != null) activeComponents.add(active);

        StaticPhysicsComponent passiv = object.get(StaticPhysicsComponent.class);
        if (passiv != null) staticComponents.add(passiv);
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
