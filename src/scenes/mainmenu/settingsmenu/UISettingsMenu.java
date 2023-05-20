package scenes.mainmenu.settingsmenu;

import gui.ConstraintFactory;
import gui.UIComponent;
import gui.components.UIBlock;
import gui.components.UIClickable;
import gui.components.UIContainer;
import gui.components.UIPage;
import gui.constraints.UICenterConstraint;
import gui.constraints.UIUnitConstraint;
import gui.components.specific.UITextButton;
import scenes.mainmenu.settingsmenu.components.UIAudioSettings;
import scenes.mainmenu.settingsmenu.components.UIVideoSettings;

import java.awt.*;

public class UISettingsMenu extends UIComponent {

    public UISettingsMenu() {}

    @Override
    protected void init() {
        UIBlock background = new UIBlock(new Color(0, 0, 0, 0.5f));
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

        UIClickable escape = new UITextButton("Zurück");
        escape.setConstraints(ConstraintFactory.unitConstrains(0, 0, 8.0f, 2.5f));
        escape.addListener(() -> {
            ((UIPage) getParent()).switchPage(0);
        });
        add(escape);

        UIClickable videosettings = new UITextButton("Video");
        videosettings.setConstraints(ConstraintFactory.unitConstrains(0, 0, 6.5f, 2.5f));
        videosettings.addListener(() -> {
            page.switchPage(0);
        });
        navbar.add(videosettings);

        UIClickable audiosettings = new UITextButton("Audio");
        audiosettings.setConstraints(ConstraintFactory.unitConstrains(10f, 0, 6.5f, 2.5f));
        audiosettings.addListener(() -> {
            page.switchPage(1);
        });
        navbar.add(audiosettings);

        add(navbar);
    }
}