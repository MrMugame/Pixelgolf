package scenes;

import game.GameObject;

import java.util.ArrayList;

public class Level extends Scene {
    private String path;

    public Level(String p) {
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
