package scenes.levels.components;

import gui.ConstraintFactory;
import gui.UIComponent;
import gui.components.UIClickable;
import gui.components.UIImage;
import gui.constraints.UIImageAspectConstraint;

public class UIEscapeButton extends UIClickable {

    private UIComponent hovered, pressed;

    private String path, pathHover, pathPress;

    public UIEscapeButton(String path, String pathHover, String pathPress) {
        this.path = path;
        this.pathHover = pathHover;
        this.pathPress = pathPress;
    }

    @Override
    protected void init() {
        hovered = new UIImage(pathHover);
        hovered.setConstraints(ConstraintFactory.fullscreen());

        pressed = new UIImage(pathPress);
        pressed.setConstraints(ConstraintFactory.fullscreen());

        UIImage image = new UIImage(path);
        image.setConstraints(ConstraintFactory.fullscreen());
        image.getConstraints().addWidth(new UIImageAspectConstraint());
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
