package game;

import physics.Vector2D;

import java.util.ArrayList;

public class GameObject {
    private final String name;
    private Transform transform;
    private ArrayList<Component> components = new ArrayList<>();


    public GameObject(String n, Vector2D pos, Vector2D size, int z) {
        name = n;
        transform = new Transform(pos, size, z);
    }

    public void update(float dt) {
        for (Component component : components) {
            component.update(dt);
        }
    }

    public void add(Component component) {
        component.parent = this;
        components.add(component);
    }

    public <T extends Component> T get(Class<T> c) {
        for (Component component : components) {
            if (c.isAssignableFrom(component.getClass())) {
                return c.cast(component);
            }
        }
        return null;
    }

    public Transform getTransform() {
        return transform;
    }

    public String getName() {
        return name;
    }
}
