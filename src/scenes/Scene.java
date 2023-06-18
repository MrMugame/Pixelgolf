package scenes;

import game.Component;
import graphics.Camera;
import game.GameObject;
import graphics.Renderer;
import gui.UIRenderer;
import physics.Physics;

import java.awt.*;
import java.util.ArrayList;

public abstract class Scene {
    private Renderer renderer = new Renderer();
    private UIRenderer uiRenderer = new UIRenderer();
    private Physics physics = new Physics();
    private Camera camera;
    private ArrayList<GameObject> pendingDelete = new ArrayList<>();
    private ArrayList<GameObject> pendingAdd = new ArrayList<>();
    private ArrayList<GameObject> objects = new ArrayList<>();

    private boolean paused = false;

    protected Scene(Camera cam) {
        camera = cam;
    }

    public void addGameObject(GameObject go) {
        if (pendingAdd.contains(go) || objects.contains(go)) return;
        pendingAdd.add(go);
    }

    public void removeGameObject(GameObject go) {
        if (pendingDelete.contains(go)) return;
        pendingDelete.add(go);
    }

    public GameObject getGameObject(String name) {
        for (GameObject object : objects) {
            if (object.getName().equals(name)) return object;
        }
        return null;
    }

    public <T extends Component> ArrayList<GameObject> getGameObjects(Class<T> component) {
        ArrayList<GameObject> list = new ArrayList<>();

        for (GameObject object : objects) {
            if (object.get(component) != null) {
                list.add(object);
            }
        }

        return list;
    }

    public void update(float dt) {
        uiRenderer.update();
        if (paused) return;
        physics.update();
        camera.update(dt);

        for (GameObject object : objects) {
            object.update(dt);
        }

        for (GameObject object : pendingDelete) {
            renderer.remove(object);
            physics.remove(object);

            objects.remove(object);
        }
        pendingDelete.clear();

        for (GameObject object : pendingAdd) {
            renderer.add(object);
            physics.add(object);

            objects.add(object);
        }
        pendingAdd.clear();
    }

    public void render(Graphics2D g) {
        renderer.render(g);
        uiRenderer.render(g);
    }

    public abstract void init();

    public Camera getCamera() {
        return camera;
    }

    public UIRenderer getUiRenderer() {
        return uiRenderer;
    }

    public void pause() {
        this.paused = true;
    }

    public void pursue() {
        this.paused = false;
    }
}
