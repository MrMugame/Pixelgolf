package gui.components;

import gui.UIComponent;
import input.MouseListener;
import physics.Vector2D;

public class UIClickable extends UIComponent {

    private boolean isHovered = false;

    @Override
    protected void updateInternally() {
        checkHover();
        super.updateInternally();
    }

    private void checkHover() {
        Vector2D mouse = MouseListener.get().getMousePositionScreen();

        boolean vertical = getConstraints().y < mouse.y && mouse.y < (getConstraints().y + getConstraints().height);
        boolean horizontal = getConstraints().x < mouse.x && mouse.x < (getConstraints().x + getConstraints().width);

        if (vertical && horizontal && !isHovered) {
            isHovered = true;
            onHoverEnter();
        } else if (!(vertical && horizontal) && isHovered) {
            isHovered = false;
            onHoverLeave();
        }
    }

    protected void onHoverEnter() {};
    protected void onHoverLeave() {};
}
