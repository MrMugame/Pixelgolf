package scenes.mainmenu.levelmenu.components;

import assets.Assets;
import graphics.GameWindow;
import gui.ConstraintFactory;
import gui.UIComponent;
import gui.components.UIClickable;
import gui.components.UIContainer;
import gui.components.UIImage;
import gui.components.UIText;
import gui.constraints.*;
import scenes.levels.Level;

import java.awt.*;

public class UILevel extends UIClickable {

    private int stars;
    private int number;

    private UIComponent selecter;

    public UILevel(int number, int stars) {
        this.number = number;
        this.stars = stars;
    }

    @Override
    protected void init() {
        String path = "";

        if (stars == 0) {
            path = "ui/level_unplayed.png";
        } else if (stars == 1) {
            path = "ui/level_onestar.png";
        } else if (stars == 2) {
            path = "ui/level_twostar.png";
        } else {
            path = "ui/level_threestar.png";
        }

        UIImage img = new UIImage(path);
        img.getConstraints().addX(new UIPixelConstraint(0));
        img.getConstraints().addY(new UIPixelConstraint(0));
        img.getConstraints().addWidth(new UIRelativeConstraint(1));
        img.getConstraints().addHeight(new UIImageAspectConstraint());
        add(img);

        UIText text = new UIText(Integer.toString(number), new Color(0,0,0), Assets.DEFAULT_FONT, 56, true);
        text.getConstraints().addX(new UIPixelConstraint(0));
        text.getConstraints().addY(new UIPixelConstraint(0));
        text.getConstraints().addWidth(new UIRelativeConstraint(1));
        text.getConstraints().addHeight(new UIRelativeConstraint(0.6f));
        add(text);

        selecter = new UIContainer();
        selecter.getConstraints().addX(new UICenterConstraint());
        selecter.getConstraints().addY(new UIUnitConstraint(0));
        selecter.getConstraints().addWidth(new UIRelativeConstraint(1.1f));
        selecter.getConstraints().addHeight(new UIPassthroughConstraint());

        UIImage selectorImg = new UIImage(stars == 0 ? "ui/level_selector_unplayed.png" : "ui/level_selector_played.png");
        selectorImg.setConstraints(ConstraintFactory.fullscreen());
        selectorImg.getConstraints().addY(new UIRelativeConstraint(-0.8f));
        selectorImg.getConstraints().addHeight(new UIImageAspectConstraint());
        selecter.add(selectorImg);


        addListener(() -> {
            GameWindow.get().changeScene(new Level("maps/level_" + number + ".xml"));
        });
    }

    @Override
    protected void onHoverEnter() {
        add(selecter);
    }

    @Override
    protected void onHoverLeave() {
        remove(selecter);
    }
}
