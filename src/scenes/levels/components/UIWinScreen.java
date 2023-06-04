package scenes.levels.components;

import assets.Assets;
import graphics.GameWindow;
import gui.UIComponent;
import gui.components.UIContainer;
import gui.components.UIImage;
import gui.constraints.*;
import scenes.levels.Level;
import scenes.levels.LevelLoader;
import scenes.mainmenu.MainMenu;
import state.GameState;

public class UIWinScreen extends UIComponent {

    private static String ACTIVE = "ui/win_star_active.png";
    private static String OFF = "ui/win_star_off.png";

    private int stars;
    private int self;

    public UIWinScreen(int stars, int self) {
        this.stars = stars;
        this.self = self;
    }

    @Override
    protected void init() {
        UIImage background = new UIImage("ui/win_background.png");
        background.getConstraints().addX(new UICenterConstraint());
        background.getConstraints().addY(new UICenterConstraint());
        background.getConstraints().addWidth(new UIImageAspectConstraint());
        background.getConstraints().addHeight(new UIUnitConstraint(25));
        add(background);

        // STARS

        UIContainer container = new UIContainer();
        container.getConstraints().addX(new UICenterConstraint());
        container.getConstraints().addY(new UICenterConstraint());
        container.getConstraints().addWidth(new UIUnitConstraint(30));
        container.getConstraints().addHeight(new UIPassthroughConstraint());
        background.add(container);

        UIImage starLeft = new UIImage(stars > 0 ? ACTIVE : OFF);
        starLeft.getConstraints().addX(new UIUnitConstraint(0));
        starLeft.getConstraints().addY(new UICenterConstraint());
        starLeft.getConstraints().addWidth(new UIImageAspectConstraint());
        starLeft.getConstraints().addHeight(new UIUnitConstraint(8));
        container.add(starLeft);

        UIImage starMiddle = new UIImage(stars > 1 ? ACTIVE : OFF);
        starMiddle.getConstraints().addX(new UICenterConstraint());
        starMiddle.getConstraints().addY(new UICenterConstraint());
        starMiddle.getConstraints().addWidth(new UIImageAspectConstraint());
        starMiddle.getConstraints().addHeight(new UIUnitConstraint(8));
        container.add(starMiddle);

        UIImage starRight = new UIImage(stars > 2 ? ACTIVE : OFF);
        starRight.getConstraints().addX(new UIEndAlignContstraint());
        starRight.getConstraints().addY(new UICenterConstraint());
        starRight.getConstraints().addWidth(new UIImageAspectConstraint());
        starRight.getConstraints().addHeight(new UIUnitConstraint(8));
        container.add(starRight);

        // WIN / LOSE

        UIImage infobox = new UIImage(stars > 0 ? "ui/win_win.png" : "ui/win_lose.png");
        infobox.getConstraints().addX(new UICenterConstraint());
        infobox.getConstraints().addY(new UIUnitConstraint(1));
        infobox.getConstraints().addWidth(new UIImageAspectConstraint());
        infobox.getConstraints().addHeight(new UIUnitConstraint(6.5f));
        background.add(infobox);

        // BUTTON

        UIContainer containerTwo = new UIContainer();
        containerTwo.getConstraints().addX(new UICenterConstraint());
        containerTwo.getConstraints().addY(new UIEndAlignContstraint(new UIUnitConstraint(2)));
        containerTwo.getConstraints().addWidth(new UIUnitConstraint(28));
        containerTwo.getConstraints().addHeight(new UIPassthroughConstraint());
        background.add(containerTwo);

        UIWinButton buttonMenu = new UIWinButton("ui/win_menu_button.png", false);
        buttonMenu.getConstraints().addX(new UIUnitConstraint(0));
        buttonMenu.getConstraints().addY(new UICenterConstraint());
        buttonMenu.getConstraints().addWidth(new UIPassthroughConstraint());
        buttonMenu.getConstraints().addHeight(new UIUnitConstraint(4.5f));
        buttonMenu.addListener(() -> {
            GameWindow.get().changeScene(new MainMenu());
        });
        containerTwo.add(buttonMenu);

        // TODO: Also calls retry than pressing exit in escape menu because the two buttons lie on top of each other (Works out because of traversal order isnt great tho)
        UIWinButton buttonRetry = new UIWinButton("ui/win_retry_button.png", false);
        buttonRetry.getConstraints().addX(new UICenterConstraint());
        buttonRetry.getConstraints().addY(new UICenterConstraint());
        buttonRetry.getConstraints().addWidth(new UIPassthroughConstraint());
        buttonRetry.getConstraints().addHeight(new UIUnitConstraint(4.5f));
        buttonRetry.addListener(() -> {
            GameWindow.get().changeScene(new Level(self));
        });
        containerTwo.add(buttonRetry);

        boolean next = Assets.getFile(LevelLoader.class, Assets.getLevelPath(self + 1)) != null;
        if (next) next = stars != 0;

        UIWinButton buttonNext = new UIWinButton(next ? "ui/win_next_button.png" : "ui/win_next_button_off.png", !next);
        buttonNext.getConstraints().addX(new UIEndAlignContstraint());
        buttonNext.getConstraints().addY(new UICenterConstraint());
        buttonNext.getConstraints().addWidth(new UIPassthroughConstraint());
        buttonNext.getConstraints().addHeight(new UIUnitConstraint(4.5f));
        if (next) {
            buttonNext.addListener(() -> {
                GameWindow.get().changeScene(new Level(self+1));
            });
        }
        containerTwo.add(buttonNext);
    }


}
