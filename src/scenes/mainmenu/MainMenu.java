package scenes.mainmenu;

import graphics.Camera;
import gui.UIComponent;
import gui.components.UIPage;
import gui.constraints.UIPixelConstraint;
import gui.constraints.UIRelativeConstraint;
import scenes.Scene;
import scenes.mainmenu.levelmenu.UILevelMenu;
import scenes.mainmenu.settingsmenu.UISettingsMenu;
import scenes.mainmenu.startmenu.UIMainScreen;
import sound.Playlist;
import sound.SoundSystem;

public class MainMenu extends Scene {

    public MainMenu() {
        super(new Camera());
    }

    @Override
    public void init() {
        UIComponent page = new UIPage(new UIMainScreen(), new UISettingsMenu(), new UILevelMenu());
        page.getConstraints().addX(new UIPixelConstraint(0));
        page.getConstraints().addY(new UIPixelConstraint(0));
        page.getConstraints().addWidth(new UIRelativeConstraint(1));
        page.getConstraints().addHeight(new UIRelativeConstraint(1));
        getUiRenderer().getContainer().add(page);

        SoundSystem.get().play(new Playlist("sound/level_1.wav"));
    }
}
