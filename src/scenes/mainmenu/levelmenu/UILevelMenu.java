package scenes.mainmenu.levelmenu;

import assets.Assets;
import graphics.GameWindow;
import gui.ConstraintFactory;
import gui.UIComponent;
import gui.components.UIContainer;
import gui.components.UIImage;
import gui.components.UIPage;
import gui.constraints.*;
import scenes.levels.Level;
import scenes.levels.LevelLoader;
import scenes.mainmenu.levelmenu.components.UILevel;
import scenes.mainmenu.settingsmenu.components.UIBackButton;
import state.GameState;

import java.io.InputStream;

public class UILevelMenu extends UIComponent {

    public UILevelMenu() {}

    @Override
    protected void init() {
        UIComponent background = new UIImage("ui/background_2.png");
        background.setConstraints(ConstraintFactory.fullscreenFitImage());
        add(background);

        UIBackButton backButton = new UIBackButton();
        backButton.setConstraints(ConstraintFactory.unitConstrains(2, 2, 6, 0));
        backButton.getConstraints().addHeight(new UIPassthroughConstraint());
        backButton.addListener(() -> {
            ((UIPage) getParent()).switchPage(0);
        });
        add(backButton);

        UIContainer container = new UIContainer();
        container.getConstraints().addX(new UICenterConstraint());
        container.getConstraints().addY(new UICenterConstraint());
        container.getConstraints().addWidth(new UIUnitConstraint(40));
        container.getConstraints().addHeight(new UIAspectConstraint(1));

        for (int i = 0; i < 9; i++) { // Momentan werden nicht mehr als 9 Level unterstützt
            InputStream stream = Assets.loadLevel(i+1);
            if (stream == null) break;


            boolean locked = i != 0 && GameState.get().getLevel(i).getStars() == 0;
            UILevel level = new UILevel(i+1, GameState.get().getLevel(i+1).getStars(), locked);
            level.getConstraints().addWidth(new UIUnitConstraint(10));
            level.getConstraints().addHeight(new UIPassthroughConstraint());

            int finalI = i+1;
            if (!locked) {
                level.addListener(() -> {
                    GameWindow.get().changeScene(new Level(finalI));
                });
            }

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
                // top
                level.getConstraints().addY(new UIUnitConstraint(0));
            } else if (i / 3 == 1) {
                // middle
                level.getConstraints().addY(new UICenterConstraint());
            } else if (i / 3 == 2) {
                // bottom
                level.getConstraints().addY(new UIEndAlignContstraint());
            }

            container.add(level);
        }

        add(container);
    }
}
