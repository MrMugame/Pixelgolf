package scenes.mainmenu.levelmenu.components;

import assets.Assets;
import gui.ConstraintFactory;
import gui.UIComponent;
import gui.components.UIClickable;
import gui.components.UIContainer;
import gui.components.UIImage;
import gui.components.UIText;
import gui.constraints.*;

import java.awt.*;

public class UILevel extends UIClickable {

    private int stars;
    private int number;
    private boolean locked = false;

    private UIComponent selecter;

    public UILevel(int number, int stars, boolean locked) {
        this.number = number;
        this.stars = stars;
        this.locked = locked;
    }

    @Override
    protected void init() {
        String path = "";
        if (locked) {
            path = "ui/level_locked.png";
        } else if (stars == 0) {
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

        if (locked) {
            // Dummy
            selecter = new UIContainer();
            selecter.setConstraints(ConstraintFactory.fullscreen());
            return;
        }

        UIText text = new UIText(Integer.toString(number), new Color(0,0,0), Assets.DEFAULT_FONT, 34, true);
        text.getConstraints().addX(new UIPixelConstraint(0));
        text.getConstraints().addY(new UIPixelConstraint(0));
        text.getConstraints().addWidth(new UIRelativeConstraint(1));
        text.getConstraints().addHeight(new UIRelativeConstraint(0.7f));
        add(text);

        selecter = new UIImage(stars == 0 ? "ui/level_selector_unplayed.png" : "ui/level_selector_played.png");
        selecter.getConstraints().addX(new UICenterConstraint());
        selecter.getConstraints().addY(new UIUnitConstraint(-1.5f));
        selecter.getConstraints().addWidth(new UIRelativeConstraint(1.1f));
        selecter.getConstraints().addHeight(new UIImageAspectConstraint());

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
