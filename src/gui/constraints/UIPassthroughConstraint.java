package gui.constraints;

import gui.UIComponent;
import gui.UIConstraint;

public class UIPassthroughConstraint extends UIConstraint {

    public UIPassthroughConstraint() {}

    @Override
    public int getValue(UIComponent component, boolean vertical) {
        // In den meisten Fällen will man eh nur die erste Komponente. Ist jedoch etwas obskur
        UIComponent child = component.getChild(0);
        if (vertical) {
            return child.getConstraints().heightCt.getValue(child, true);
        } else {
            return child.getConstraints().widthCt.getValue(child, false);
        }

    }
}
