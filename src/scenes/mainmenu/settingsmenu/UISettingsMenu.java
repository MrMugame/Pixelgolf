package scenes.mainmenu.settingsmenu;

import graphics.GameWindow;
import gui.ConstraintFactory;
import gui.UIComponent;
import gui.components.*;
import gui.constraints.*;
import scenes.mainmenu.settingsmenu.components.UIBackButton;
import scenes.mainmenu.settingsmenu.components.UISelectButton;

import java.awt.*;

public class UISettingsMenu extends UIComponent {

    public UISettingsMenu() {}

    @Override
    protected void init() {
        UIComponent background = new UIImage("ui/background_2.png");
        background.setConstraints(ConstraintFactory.fullscreenAspect());
        add(background);

        UIBackButton backButton = new UIBackButton();
        backButton.setConstraints(ConstraintFactory.unitConstrains(2, 2, 6, 0));
        backButton.getConstraints().addHeight(new UIPassthroughConstraint());
        backButton.addListener(() -> {
            ((UIPage) getParent()).switchPage(0);
        });
        add(backButton);

        UIComponent block = new UIImage("ui/settings_container.png");
        block.getConstraints().addX(new UICenterConstraint());
        block.getConstraints().addY(new UICenterConstraint());
        block.getConstraints().addWidth(new UIUnitConstraint(50));
        block.getConstraints().addHeight(new UIImageAspectConstraint());

        UIContainer container = new UIContainer();
        container.getConstraints().addX(new UICenterConstraint());
        container.getConstraints().addY(new UICenterConstraint());
        container.getConstraints().addWidth(new UIRelativeConstraint(0.85f));
        container.getConstraints().addHeight(new UIRelativeConstraint(0.80f));
        block.add(container);

        UISelectButton button_1 = new UISelectButton(GameWindow.get().isFullscreen() ? 1 : 0, "Fullscreen Off", "Fullscreen On");
        button_1.getConstraints().addX(new UIEndAlignContstraint());
        button_1.getConstraints().addY(new UIUnitConstraint(0));
        button_1.getConstraints().addWidth(new UIUnitConstraint(15));
        button_1.getConstraints().addHeight(new UIPassthroughConstraint());
        button_1.addListener((i) -> {
            switch (i) {
                case 0:
                    GameWindow.get().setWindowed();
                    break;
                case 1:
                    GameWindow.get().setFullscreen();
                    break;
            }
        });
        container.add(button_1);




        add(block);
    }
}