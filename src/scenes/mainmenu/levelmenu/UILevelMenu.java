package scenes.mainmenu.levelmenu;

import gui.ConstraintFactory;
import gui.UIComponent;
import gui.UIConstraint;
import gui.components.UIContainer;
import gui.components.UIImage;
import gui.constraints.*;
import scenes.mainmenu.levelmenu.components.UILevel;

public class UILevelMenu extends UIComponent {

    public UILevelMenu() {}

    @Override
    protected void init() {
        UIComponent background = new UIImage("ui/background_2.png");
        background.setConstraints(ConstraintFactory.fullscreenAspect());
        add(background);

        UIContainer container = new UIContainer();
        container.getConstraints().addX(new UICenterConstraint());
        container.getConstraints().addY(new UICenterConstraint());
        container.getConstraints().addWidth(new UIUnitConstraint(40));
        container.getConstraints().addHeight(new UIAspectConstraint(1));

        for (int i = 0; i < 9; i++) {
            UILevel level = new UILevel(i + 1, 0);
            level.getConstraints().addWidth(new UIUnitConstraint(8));
            level.getConstraints().addHeight(new UIPassthroughConstraint());


            if (i % 3 == 0) {
                // left
                level.getConstraints().addX(new UIUnitConstraint(0));
            } else if (i % 3 == 1) {
                // middle
                level.getConstraints().addX(new UICenterConstraint());
            } else if (i % 3 == 2) {
                // right
                level.getConstraints().addX(new UIEndAlignContstraint());
            }

            if (i / 3 == 0) {
                level.getConstraints().addY(new UIUnitConstraint(0));
            } else if (i / 3 == 1) {
                // middle
                level.getConstraints().addY(new UICenterConstraint());
            } else if (i / 3 == 2) {
                // right
                level.getConstraints().addY(new UIEndAlignContstraint());
            }

            container.add(level);
        }

        add(container);
    }
}
