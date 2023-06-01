package scenes.levels;

import game.GameObject;
import game.graphics.StaticGraphic;
import game.input.BallInput;
import game.physics.Flagpole;
import game.physics.BallPhysics;
import game.physics.Wall;
import graphics.GameWindow;
import graphics.LevelCamera;
import gui.ConstraintFactory;
import gui.UIComponent;
import physics.Vector2D;
import scenes.Scene;
import scenes.levels.components.UIEscapeMenu;
import scenes.levels.components.UIHUD;
import scenes.levels.components.UIWinScreen;

public class Level extends Scene {

    private final LevelLoader loader;

    public Level(String path) {
        super(new LevelCamera());
        loader = new LevelLoader(path);
    }

    @Override
    public void init() {
        try {
            loader.load();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        GameObject background = loader.renderBackground();
        addGameObject(background);

        GameObject walls = new GameObject("walls", new Vector2D(0, 0), new Vector2D(loader.getMap().width, loader.getMap().height), 0);
        walls.add(new Wall(loader.getMap().track));
        addGameObject(walls);

        for (GameObject object : loader.getDynamicObjects()) {
            addGameObject(object);
        }

        UIComponent HUD = new UIHUD();
        HUD.setConstraints(ConstraintFactory.fullscreen());
        getUiRenderer().getContainer().add(HUD);

        UIComponent escapeMenu = new UIEscapeMenu();
        escapeMenu.setConstraints(ConstraintFactory.fullscreen());
        getUiRenderer().getContainer().add(escapeMenu);
    }

    public void won() {
        // TODO: Maybe do this with an event system
        pause();

        UIComponent winScreen = new UIWinScreen(2, loader.getPath(), loader.getNextPath());
        winScreen.setConstraints(ConstraintFactory.fullscreen());
        getUiRenderer().getContainer().add(winScreen);
    }

    public float getMapWidth() {
        return loader.getMap().width;
    }

    public float getMapHeight() {
        return loader.getMap().height;
    }
}
