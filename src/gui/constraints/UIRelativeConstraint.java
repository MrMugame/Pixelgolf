package gui.constraints;

import gui.UIComponent;
import gui.UIConstraint;

public class UIRelativeConstraint extends UIConstraint {
    private float percent;

    public UIRelativeConstraint(float p) {
        percent = p;
    }

    @Override
    public int getValue(UIComponent component, boolean vertical) {
        if (vertical) {
            return (int)(component.getParent().getConstraints().height * percent);
        } else {
            return (int)(component.getParent().getConstraints().width * percent);
        }
    }
}
