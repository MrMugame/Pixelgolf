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

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

        List<String> levels = scanLevels();

        int i = 0;
        for (String path : levels) {
            if (i == 9) break; // Momentan werden nicht mehr als 9 Level unterstützt
            if (!path.matches("^level_\\d+\\.xml$")) continue;
            int number = Integer.parseInt(path.split("_")[1].split("\\.")[0]);

            UILevel level = new UILevel(number, 0);
            level.getConstraints().addWidth(new UIUnitConstraint(10));
            level.getConstraints().addHeight(new UIPassthroughConstraint());

            level.addListener(() -> {
                GameWindow.get().changeScene(new Level(number));
            });


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
            i += 1;
        }

        add(container);
    }

    private List<String> scanLevels() {
        return Stream.of(Assets.getFile(LevelLoader.class, "maps/").listFiles())
                .filter(file -> !file.isDirectory())
                .map(File::getName)
                .sorted()
                .collect(Collectors.toList());
    }
}
