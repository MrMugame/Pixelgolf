package scenes;

import game.GameObject;
import graphics.DebugCamera;

import java.util.ArrayList;

public class Level extends Scene {
    private String path;

    public Level(String p) {
        super(new DebugCamera());
        path = p;
    }

    @Override
    public void init() {
        ArrayList<GameObject> objects = LevelSerializer.loadLevel(path);
        for (GameObject o : objects) {
            addGameObject(o);
        }
    }
}
