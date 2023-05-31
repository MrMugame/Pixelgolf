package scenes.mainmenu.startmenu.components;

import gui.UIComponent;
import gui.components.UIClickable;
import gui.components.UIContainer;
import gui.components.UIImage;
import gui.constraints.*;


public class UIStartMenuButton extends UIClickable {

    private String path;

    private UIComponent arrowLeft, arrowRight;

    public UIStartMenuButton(String path) {
        this.path = path;
    }

    @Override
    protected void init() {
        UIImage background = new UIImage(path);
        background.getConstraints().addX(new UIPixelConstraint(0));
        background.getConstraints().addY(new UIPixelConstraint(0));
        background.getConstraints().addWidth(new UIImageAspectConstraint());
        background.getConstraints().addHeight(new UIRelativeConstraint(1));

        add(background);

        arrowLeft = new UIImage("ui/arrow_left.png");
        arrowLeft.getConstraints().addX(new UIUnitConstraint(-2f, false));
        arrowLeft.getConstraints().addY(new UICenterConstraint());
        arrowLeft.getConstraints().addWidth(new UIImageAspectConstraint());
        arrowLeft.getConstraints().addHeight(new UIRelativeConstraint(0.40f));

        arrowRight = new UIContainer();
        arrowRight.getConstraints().addX(new UIUnitConstraint(2f, true));
        arrowRight.getConstraints().addY(new UICenterConstraint());
        arrowRight.getConstraints().addWidth(new UIPassthroughConstraint());
        arrowRight.getConstraints().addHeight(new UIRelativeConstraint(0.40f));
        UIImage img = new UIImage("ui/arrow_right.png");
        img.getConstraints().addX(new UIRelativeConstraint(-1));
        img.getConstraints().addY(new UIPixelConstraint(0));
        img.getConstraints().addWidth(new UIImageAspectConstraint());
        img.getConstraints().addHeight(new UIRelativeConstraint(1));
        arrowRight.add(img);
    }

    @Override
    protected void onHoverEnter() {
        add(arrowLeft);
        add(arrowRight);
    }

    @Override
    protected void onHoverLeave() {
        remove(arrowLeft);
        remove(arrowRight);
    }
}
