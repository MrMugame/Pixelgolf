package scenes.levels;

import game.GameObject;
import game.physics.Wall;
import graphics.GameWindow;
import graphics.LevelCamera;
import gui.ConstraintFactory;
import gui.UIComponent;
import gui.components.UIContainer;
import physics.Vector2D;
import scenes.Scene;
import scenes.levels.components.UIEscapeMenu;
import scenes.levels.components.UIHUD;
import scenes.levels.components.UIWinScreen;
import scenes.mainmenu.MainMenu;
import state.GameState;
import state.LevelState;

public class Level extends Scene {
    private UIContainer container;
    private final LevelLoader loader;
    private final LevelLogic logic;
    private boolean finished = false;

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
            e.printStackTrace();
            System.err.println("Konnte Level nicht laden!");
            GameWindow.get().changeScene(new MainMenu());
            return;
        }

        logic.setStarBoundaries(loader.getMap().oneStar, loader.getMap().twoStar, loader.getMap().threeStar);

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
        if (finished) return; // Bei der Kollision kann es leicht passieren, dass der Ball mehrere Wände des Lochs berührt und dann würde diese Funktion mehrmals aufgerufen werden
        finished = true;

        pause();

        if (GameState.get().getLevel(loader.getNumber()).getStars() < logic.getStars()) {
            GameState.get().setLevel(loader.getNumber(), new LevelState(logic.getStars()));
        }

        UIComponent winScreen = new UIWinScreen(logic.getStars(), loader.getNumber());
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
