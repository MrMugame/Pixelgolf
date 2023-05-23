package scenes.levels;

import game.GameObject;
import game.graphics.StaticGraphic;
import game.input.BallInput;
import game.physics.BallPhysics;
import graphics.DebugCamera;
import physics.Vector2D;
import scenes.Scene;

public class Level extends Scene {

    private final LevelLoader loader;

    private GameObject background;

    public Level(String path) {
        super(new DebugCamera());
        loader = new LevelLoader(path);
    }

    @Override
    public void init() {
        try {
            loader.load();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        background = loader.renderBackground();
        addGameObject(background);

        GameObject g1 = new GameObject("ball", new Vector2D(3f, -2.5f), new Vector2D(0.5f, 0.5f), 5);
        g1.add(new StaticGraphic("ball.png"));
        g1.add(new BallPhysics(10f));
        g1.add(new BallInput());
        addGameObject(g1);
    }
}
