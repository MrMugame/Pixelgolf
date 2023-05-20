package gui.constraints;

import gui.UIComponent;
import gui.UIConstraint;

public class UIEndAlignContstraint extends UIConstraint {

    public UIEndAlignContstraint() {}

    @Override
    public int getValue(UIComponent component, boolean vertical) {
        if (vertical) {
            return component.getParent().getConstraints().height - component.getConstraints().heightCt.getValue(component, true);
        } else {
            return component.getParent().getConstraints().width - component.getConstraints().widthCt.getValue(component, false);
        }
    }
}
