package gui.constraints;

import gui.UIComponent;
import gui.UIConstraint;

public class UIAspectConstraint extends UIConstraint {
    private float ratio;

    public UIAspectConstraint(float ratio) {
        this.ratio = ratio;
    }


    @Override
    public int getValue(UIComponent component, boolean vertical) {
        if (vertical) {
            return (int)(component.getConstraints().widthCt.getValue(component, false) * ratio);
        } else {
            return (int)(component.getConstraints().heightCt.getValue(component, true) * ratio);
        }
    }
}
