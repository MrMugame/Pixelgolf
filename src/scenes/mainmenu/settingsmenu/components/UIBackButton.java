package scenes.mainmenu.settingsmenu.components;

import gui.ConstraintFactory;
import gui.UIComponent;
import gui.components.UIClickable;
import gui.components.UIImage;
import gui.constraints.UIImageAspectConstraint;

public class UIBackButton extends UIClickable {

    private UIComponent hovered, pressed;

    public UIBackButton() {}

    @Override
    protected void init() {
        // TODO: Non-square hitbox (I really dont wanna do that so this is the last thing im gonna implement, if ever)
        hovered = new UIImage("ui/back_button_hover.png");
        hovered.setConstraints(ConstraintFactory.fullscreen());

        pressed = new UIImage("ui/back_button_press.png");
        pressed.setConstraints(ConstraintFactory.fullscreen());

        UIImage image = new UIImage("ui/back_button.png");
        image.setConstraints(ConstraintFactory.fullscreen());
        image.getConstraints().addHeight(new UIImageAspectConstraint());
        add(image);
    }

    @Override
    protected void onHoverEnter() {
        add(hovered);
    }

    @Override
    protected void onHoverLeave() {
        remove(hovered);
    }

    @Override
    protected void onPressStart() {
        add(pressed);
    }

    @Override
    protected void onPressEnd() {
        remove(pressed);
    }
}
