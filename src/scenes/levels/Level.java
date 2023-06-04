package scenes.levels;

import game.GameObject;
import game.physics.Wall;
import graphics.LevelCamera;
import gui.ConstraintFactory;
import gui.UIComponent;
import gui.components.UIContainer;
import physics.Vector2D;
import scenes.Scene;
import scenes.levels.components.UIEscapeMenu;
import scenes.levels.components.UIHUD;
import scenes.levels.components.UIWinScreen;

public class Level extends Scene {

    private UIContainer container;
    private final LevelLoader loader;
    private final LevelLogic logic;

    public Level(int number) {
        super(new LevelCamera());
        loader = new LevelLoader(number);
        logic = new LevelLogic();
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

        GameObject walls = new GameObject("walls", new Vector2D(0, 0), new Vector2D(loader.getMap().width, loader.getMap().height), 0, 0);
        walls.add(new Wall(loader.getMap().track));
        addGameObject(walls);

        for (GameObject object : loader.getDynamicObjects()) {
            addGameObject(object);
        }

        ((LevelCamera) getCamera()).startAnimation();

        container = new UIContainer();
        container.setConstraints(ConstraintFactory.fullscreen());
        getUiRenderer().getContainer().add(container);

        UIComponent HUD = new UIHUD();
        HUD.setConstraints(ConstraintFactory.fullscreen());
        container.add(HUD);

        UIComponent escapeMenu = new UIEscapeMenu();
        escapeMenu.setConstraints(ConstraintFactory.fullscreen());
        getUiRenderer().getContainer().add(escapeMenu);
    }

    public void won() {
        pause();

        UIComponent winScreen = new UIWinScreen(2, loader.getNumber());
        winScreen.setConstraints(ConstraintFactory.fullscreen());
        container.add(winScreen);
    }

    public float getMapWidth() {
        return loader.getMap().width;
    }

    public float getMapHeight() {
        return loader.getMap().height;
    }

    public LevelLogic getLogic() {
        return logic;
    }
}
