package game;

import game.graphics.GraphicComponent;
import game.input.InputComponent;
import game.physics.PhysicsComponent;
import physics.Vector2D;

public class GameObject {
    private final String name;
    private Transform transform;
    private final InputComponent inputComponent;
    private final GraphicComponent graphicComponent;
    private final PhysicsComponent physicsComponent;


    public GameObject(String n, InputComponent i, GraphicComponent g, PhysicsComponent p, Vector2D pos, Vector2D size, int z) {
        name = n;
        transform = new Transform(pos, size, z);

        inputComponent = i;
        inputComponent.parent = this;
        graphicComponent = g;
        graphicComponent.parent = this;
        physicsComponent = p;
        physicsComponent.parent = this;
    }

    public void update(float dt) {
        inputComponent.update(dt);
        graphicComponent.update(dt);
        physicsComponent.update(dt);
    }

    public GraphicComponent getGraphicComponent() {
        return graphicComponent;
    }

    public InputComponent getInputComponent() {
        return inputComponent;
    }

    public PhysicsComponent getPhysicsComponent() {
        return physicsComponent;
    }

    public Transform getTransform() {
        return transform;
    }

    public String getName() {
        return name;
    }
}
