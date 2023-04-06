package scenes;

import main.Camera;
import game.GameObject;
import main.Renderer;
import physics.Physics;

import java.awt.*;
import java.util.ArrayList;

public abstract class Scene {
    private Renderer renderer = new Renderer();
    private Physics physics = new Physics();
    private Camera camera = new Camera();
    private ArrayList<GameObject> objects = new ArrayList<>();
    private ArrayList<GameObject> waiting = new ArrayList<>();

    public void addGameObject(GameObject go) {
        if (waiting.contains(go) || objects.contains(go)) return;
        waiting.add(go);
    }

    public GameObject getGameObject(String name) {
        for (GameObject object : objects) {
            if (object.getName().equals(name)) return object;
        }
        return null;
    }

    public void update(float dt) {
        physics.update();
        camera.update(dt);

        for (GameObject object : objects) {
            object.update(dt);
        }

        for (GameObject object : waiting) {
            renderer.add(object);
            physics.add(object);

            objects.add(object);
        }
        waiting.clear();
    }

    public void render(Graphics2D g) {
        renderer.render(g);
    }

    public abstract void init();

    public Camera getCamera() {
        return camera;
    }
}
