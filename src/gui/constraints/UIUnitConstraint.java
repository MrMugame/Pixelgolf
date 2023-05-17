package gui.constraints;

import gui.UIComponent;
import gui.UIConstraint;

public class UIUnitConstraint extends UIConstraint {

    // TODO: Do Some responsive way of defining units (or maybe make it an option)
    private static final float UNIT = 16;

    private float units;
    private boolean full;

    public UIUnitConstraint(float units, boolean full) {
        this.units = units;
        this.full = full;
    }

    public UIUnitConstraint(float units) {
        this.units = units;
        this.full = false;
    }

    @Override
    public int getValue(UIComponent component, boolean vertical) {
        if (full) {
            int delta = vertical ? component.getParent().getConstraints().heightCt.getValue(component.getParent(), true) : component.getParent().getConstraints().widthCt.getValue(component.getParent(), false);
            return delta + (int)(units * UNIT);
        } else {
            return (int)(units * UNIT);
        }
    }
}
