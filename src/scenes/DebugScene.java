package scenes;

import game.graphics.StaticGraphic;
import game.input.BallInput;
import game.GameObject;
import game.input.NoInput;
import game.physics.BallPhysics;
import game.physics.NoPhysics;
import game.physics.Wall;
import gui.UIComponent;
import gui.components.UIBlock;
import gui.components.UIButton;
import gui.components.UIText;
import gui.constraints.UIAspectConstraint;
import gui.constraints.UICenterConstraint;
import gui.constraints.UIPixelConstraint;
import gui.constraints.UIRelativeConstraint;
import physics.Polygon;
import physics.Vector2D;

import java.awt.*;

public class DebugScene extends Scene {
    public DebugScene() {

    }

    @Override
    public void init() {
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
        GameObject g1 = new GameObject("Pepe", new Vector2D(-1f, -2.0f), new Vector2D(0.5f, 0.5f), 5);
        GameObject g2 = new GameObject("Pepe4", new Vector2D(-5f, 0.5f), new Vector2D(5, 10), 1);

        g2.add(new StaticGraphic("img.png"));
        g2.add(new Wall(p2));

        g1.add(new StaticGraphic("ball.png"));
        g1.add(new BallPhysics(10f));
        g1.add(new BallInput());

        addGameObject(g2);
        addGameObject(g1);

/*        UIComponent c = new UIBlock(0.25f);
        c.getConstraints().addX(new UICenterConstraint());
        c.getConstraints().addY(new UIPixelConstraint(10));
        c.getConstraints().addWidth(new UIRelativeConstraint(0.25f));
        c.getConstraints().addHeight(new UIPixelConstraint(100));

        UIComponent d = new UIText("Cats like dogs", new Color(1.0f, 0, 0), "roboto.ttf", 12);
        d.getConstraints().addX(new UICenterConstraint());
        d.getConstraints().addY(new UICenterConstraint());
        d.getConstraints().addWidth(new UIRelativeConstraint(0.25f));
        d.getConstraints().addHeight(new UIAspectConstraint(1));*/

        UIComponent button = new UIButton("Press me!", new Color(0, 1f, 1f), new Color(0, 0, 0), 50);
        button.getConstraints().addX(new UICenterConstraint());
        button.getConstraints().addY(new UIPixelConstraint(10));
        button.getConstraints().addWidth(new UIRelativeConstraint(0.25f));
        button.getConstraints().addHeight(new UIPixelConstraint(100));

        getUiRenderer().getContainer().add(button);


        //addGameObject(new GameObject("Pepe3", new StaticGraphic("img.png"), new Wall(p), new Vector2D(1f, 0.5f), new Vector2D(1, 5), 1));
        //addGameObject(new GameObject("Pepe2", new NoInput(), new StaticGraphic("pepe.png"), new NoPhysics(), new Vector2D(-0.5f, 0.5f), new Vector2D(1, 1), 10));
        //addGameObject(new GameObject("Pepe5", new NoInput(), new StaticGraphic("ball_black.png"), new Wall(p3), new Vector2D(-14f, 0.5f), new Vector2D(5, 5), 1));

    }
}
