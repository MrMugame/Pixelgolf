package scenes.settingsmenu.components;

import gui.ConstraintFactory;
import gui.UIComponent;
import gui.components.UIBlock;
import gui.components.UIClickable;
import gui.components.UIContainer;
import gui.components.UIPage;
import gui.constraints.UICenterConstraint;
import gui.constraints.UIPixelConstraint;
import gui.constraints.UIRelativeConstraint;
import gui.constraints.UIUnitConstraint;
import scenes.mainmenu.components.UIMenuButton;

import java.awt.*;

public class UISettingsMenu extends UIComponent {

    public UISettingsMenu() {}

    @Override
    protected void init() {
        UIBlock background = new UIBlock(new Color(0, 0, 0, 0.50f));
        background.setConstraints(ConstraintFactory.fullscreen());
        add(background);

        UIPage page = new UIPage(new UIVideoSettings(), new UIAudioSettings());
        page.getConstraints().addX(new UICenterConstraint());
        page.getConstraints().addY(new UIUnitConstraint(8f));
        page.getConstraints().addWidth(new UIUnitConstraint(30f));
        page.getConstraints().addHeight(new UIUnitConstraint(25f));

        add(page);


        UIComponent navbar = new UIContainer();
        navbar.getConstraints().addX(new UICenterConstraint());
        navbar.getConstraints().addY(new UIUnitConstraint(2));
        navbar.getConstraints().addWidth(new UIUnitConstraint(16.5f));
        navbar.getConstraints().addHeight(new UIUnitConstraint(2.5f));

        UIClickable videosettings = new UIMenuButton("Video");
        videosettings.setConstraints(ConstraintFactory.unitConstrains(0, 0, 6.5f, 2.5f));
        videosettings.addListener(() -> {
            page.switchPage(0);
        });
        navbar.add(videosettings);

        UIClickable audiosettings = new UIMenuButton("Audio");
        audiosettings.setConstraints(ConstraintFactory.unitConstrains(10f, 0, 6.5f, 2.5f));
        audiosettings.addListener(() -> {
            page.switchPage(1);
        });
        navbar.add(audiosettings);

        add(navbar);
    }
}
