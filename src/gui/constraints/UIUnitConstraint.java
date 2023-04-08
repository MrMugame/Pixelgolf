package gui.constraints;

import gui.UIComponent;
import gui.UIConstraint;

public class UIUnitConstraint extends UIConstraint {

    // TODO: Do Some responsive way of defining units (or maybe make it an option)
    private static final float UNIT = 16;

    private float units;

    public UIUnitConstraint(float units) {
        this.units = units;
    }

    @Override
    public int getValue(UIComponent component, boolean vertical) {
        return (int)(units * UNIT);
    }
}
