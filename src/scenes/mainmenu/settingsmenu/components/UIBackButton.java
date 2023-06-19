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
        // Hitbox von den Knöpfen ist, dadurch dass es nur quadratische Hitboxen gibt, etwas größer als die Texture, aber damit fange ich jetzt nicht an
        hovered = new UIImage("ui/back_button_hover.png");
        hovered.setConstraints(ConstraintFactory.fullscreen());
        hovered.getConstraints().addHeight(new UIImageAspectConstraint());

        pressed = new UIImage("ui/back_button_press.png");
        pressed.setConstraints(ConstraintFactory.fullscreen());
        pressed.getConstraints().addHeight(new UIImageAspectConstraint());

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
