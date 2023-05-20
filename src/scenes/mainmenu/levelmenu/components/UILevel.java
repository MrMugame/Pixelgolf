package scenes.mainmenu.levelmenu.components;

import assets.Assets;
import gui.ConstraintFactory;
import gui.components.UIClickable;
import gui.components.UIImage;
import gui.components.UIText;
import gui.constraints.UICenterConstraint;
import gui.constraints.UIImageAspectConstraint;
import gui.constraints.UIPixelConstraint;
import gui.constraints.UIRelativeConstraint;

import java.awt.*;

public class UILevel extends UIClickable {

    private int stars;
    private int number;

    private UIImage selecter;

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

        UIText text = new UIText(Integer.toString(number), new Color(0,0,0), Assets.DEFAULT_FONT_BOLD, 44, true);
        text.getConstraints().addX(new UIPixelConstraint(0));
        text.getConstraints().addY(new UIPixelConstraint(0));
        text.getConstraints().addWidth(new UIRelativeConstraint(1));
        text.getConstraints().addHeight(new UIRelativeConstraint(0.6f));
        add(text);

        selecter = new UIImage("ui/level_selected.png");
        selecter.getConstraints().addX(new UICenterConstraint());
        selecter.getConstraints().addY(new UICenterConstraint());
        selecter.getConstraints().addWidth(new UIRelativeConstraint(1.25f));
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
