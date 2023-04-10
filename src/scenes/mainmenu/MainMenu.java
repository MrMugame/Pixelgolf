package scenes.mainmenu;

import game.GameObject;
import game.graphics.StaticGraphic;
import graphics.Camera;
import gui.UIComponent;
import gui.components.UIPage;
import gui.constraints.UIPixelConstraint;
import gui.constraints.UIRelativeConstraint;
import physics.Vector2D;
import scenes.Scene;
import scenes.mainmenu.components.UIMainScreen;
import scenes.settingsmenu.components.UISettingsMenu;

public class MainMenu extends Scene {

    public MainMenu() {
        super(new Camera());
    }

    @Override
    public void init() {
        UIComponent page = new UIPage(new UIMainScreen(), new UISettingsMenu());
        page.getConstraints().addX(new UIPixelConstraint(0));
        page.getConstraints().addY(new UIPixelConstraint(0));
        page.getConstraints().addWidth(new UIRelativeConstraint(1));
        page.getConstraints().addHeight(new UIRelativeConstraint(1));
        getUiRenderer().getContainer().add(page);

        GameObject go = new GameObject("Background", new Vector2D(-6, 4), new Vector2D(12, 8), 0);
        go.add(new StaticGraphic("minecraft.png"));
        addGameObject(go);
    }
}
