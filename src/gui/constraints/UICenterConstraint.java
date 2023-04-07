package gui.constraints;

import gui.UIComponent;
import gui.UIConstraint;

public class UICenterConstraint extends UIConstraint {

    public UICenterConstraint() {}

    @Override
    public int getValue(UIComponent component, boolean vertical) {
        if (vertical) {
            return component.getParent().getConstraints().height / 2 - component.getConstraints().height / 2;
        } else {
            return component.getParent().getConstraints().width / 2 - component.getConstraints().width / 2;
        }
    }
}
