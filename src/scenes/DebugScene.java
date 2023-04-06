package scenes;

import game.graphics.StaticGraphic;
import game.input.BallInput;
import game.GameObject;
import game.input.NoInput;
import game.physics.BallPhysics;
import game.physics.NoPhysics;
import game.physics.Wall;
import physics.Polygon;
import physics.Vector2D;

public class DebugScene extends Scene {
    public DebugScene() {
        Polygon p = new Polygon(true);
        p.addPoint(new Vector2D(0, 0));
        p.addPoint(new Vector2D(0, -5));
        p.addPoint(new Vector2D(1, -5));
        p.addPoint(new Vector2D(1, 0));
        Polygon p2 = new Polygon(true);
        p2.addPoint(new Vector2D(0, 0));
        p2.addPoint(new Vector2D(0, -7));
        p2.addPoint(new Vector2D(3, -10));
        p2.addPoint(new Vector2D(5, -10));
        p2.addPoint(new Vector2D(5, 0));
        Polygon p3 = new Polygon(true);
        int accuracy = 100;
        for (int i = 1; i < accuracy; i++) {
            Vector2D pp = new Vector2D((float) Math.cos(2 * Math.PI * ((float) i / accuracy)) * 2.5f, (float) Math.sin(2 * Math.PI * ((float) i / accuracy)) * 2.5f).add(new Vector2D(2.5f, -2.5f));
            p3.addPoint(pp);
        }
        addGameObject(new GameObject("Pepe", new BallInput(), new StaticGraphic("ball.png"), new BallPhysics(10f), new Vector2D(-1f, -2.0f), new Vector2D(0.5f, 0.5f), 5));
        //addGameObject(new GameObject("Pepe2", new NoInput(), new StaticGraphic("pepe.png"), new NoPhysics(), new Vector2D(-0.5f, 0.5f), new Vector2D(1, 1), 10));
        addGameObject(new GameObject("Pepe3", new NoInput(), new StaticGraphic("img.png"), new Wall(p), new Vector2D(1f, 0.5f), new Vector2D(1, 5), 1));
        addGameObject(new GameObject("Pepe4", new NoInput(), new StaticGraphic("img.png"), new Wall(p2), new Vector2D(-5f, 0.5f), new Vector2D(5, 10), 1));
        addGameObject(new GameObject("Pepe5", new NoInput(), new StaticGraphic("ball_black.png"), new Wall(p3), new Vector2D(-14f, 0.5f), new Vector2D(5, 5), 1));

    }
}
