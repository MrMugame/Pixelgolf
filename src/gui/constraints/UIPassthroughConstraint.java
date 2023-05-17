package gui.constraints;

import gui.UIComponent;
import gui.UIConstraint;

public class UIPassthroughConstraint extends UIConstraint {

    public UIPassthroughConstraint() {}

    // TODO: Change to somehow get specific child

    @Override
    public int getValue(UIComponent component, boolean vertical) {
        UIComponent child = component.getChild(0);
        if (vertical) {
            return child.getConstraints().heightCt.getValue(child, true);
        } else {
            return child.getConstraints().widthCt.getValue(child, false);
        }

    }
}
