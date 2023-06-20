package scenes.levels.components;

import gui.ConstraintFactory;
import gui.UIComponent;
import gui.components.UIClickable;
import gui.components.UIImage;
import gui.constraints.UIImageAspectConstraint;

public class UIWinButton extends UIClickable {

    private String path;
    private UIComponent hover;
    private boolean disabled;

    public UIWinButton(String path, boolean disabled) {
        this.path = path;
        this.disabled = disabled;
    }

    @Override
    protected void init() {
        UIImage image = new UIImage(path);
        image.setConstraints(ConstraintFactory.fullscreen());
        image.getConstraints().addWidth(new UIImageAspectConstraint());
        add(image);

        hover = new UIImage("ui/win_button_hover.png");
        hover.setConstraints(ConstraintFactory.fullscreen());
        hover.getConstraints().addWidth(new UIImageAspectConstraint());
    }

    @Override
    protected void onHoverEnter() {
        if (disabled) return;
        add(hover);
    }

    @Override
    protected void onHoverLeave() {
        if (disabled) return;
        remove(hover);
    }
}
