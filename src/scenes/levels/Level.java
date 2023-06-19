package scenes.levels;

import event.Event;
import event.EventSystem;
import event.EventType;
import event.Observer;
import game.GameObject;
import game.physics.Custom;
import game.physics.Material;
import game.physics.MaterialType;
import graphics.GameWindow;
import graphics.LevelCamera;
import gui.ConstraintFactory;
import gui.UIComponent;
import gui.components.UIContainer;
import physics.Vector2D;
import scenes.Scene;
import scenes.levels.components.UIEscapeMenu;
import scenes.levels.components.UIHUD;
import scenes.levels.components.UITutorial;
import scenes.levels.components.UIWinScreen;
import scenes.mainmenu.MainMenu;
import sound.Playlist;
import sound.SoundSystem;
import state.GameState;
import state.LevelState;

public class Level extends Scene implements Observer {
    private UIContainer container;
    private final LevelLoader loader;
    private final LevelLogic logic;
    private boolean finished = false;
    private UITutorial tutorial;

    public Level(int number) {
        super(new LevelCamera());
        loader = new LevelLoader(number);
        logic = new LevelLogic();

        EventSystem.addObserver(this);

        tutorial = number == 1 && !GameState.get().getProperty("tutorialPlayed") ? new UITutorial() : null; ;
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

        SoundSystem.get().play(new Playlist(loader.getMap().music.toArray(new String[0])));

        GameObject background = loader.renderBackground();
        addGameObject(background);

        for (int i = 0; i < loader.getMap().tracks.size(); i++) {
            GameObject walls = new GameObject("walls_" + i, new Vector2D(0, 0), new Vector2D(loader.getMap().width, loader.getMap().height), 0, 0);
            walls.add(new Custom(loader.getMap().tracks.get(i)));
            walls.add(new Material(MaterialType.WALL));
            addGameObject(walls);
        }

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

        if (tutorial != null) {
            tutorial.setConstraints(ConstraintFactory.fullscreen());
            container.add(tutorial);
        }

        UIComponent escapeMenu = new UIEscapeMenu();
        escapeMenu.setConstraints(ConstraintFactory.fullscreen());
        getUiRenderer().getContainer().add(escapeMenu);
    }

    public void won() {
        if (finished) return; // Bei der Kollision kann es leicht passieren, dass der Ball mehrere Wände des Lochs berührt und dann würde diese Funktion mehrmals aufgerufen werden
        finished = true;

        EventSystem.notify(new Event<>(EventType.TUTORIAL, "finished"));

        EventSystem.notify(new Event<>(EventType.GAME, "pause"));

        if (GameState.get().getLevel(loader.getNumber()).getStars() < logic.getStars()) {
            GameState.get().setLevel(loader.getNumber(), new LevelState(logic.getStars()));
        }

        UIComponent winScreen = new UIWinScreen(logic.getStars(), loader.getNumber());
        winScreen.setConstraints(ConstraintFactory.fullscreen());
        container.add(winScreen);
    }

    @Override
    public void onNotify(Event<?> e) {
        if (e.getType() == EventType.GAME_LOGIC) {
            switch ((String) e.getData()) {
                case "stroke":
                    logic.addStroke();
                    break;
                case "reset":
                    logic.reset();
                    break;
                case "win":
                    won();
                    break;
            }
        }
        super.onNotify(e);
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

    public UITutorial getTutorial() {
        return tutorial;
    }

}
