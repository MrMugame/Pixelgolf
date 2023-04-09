package gui;

import gui.constraints.UIPixelConstraint;
import gui.constraints.UIRelativeConstraint;
import gui.constraints.UIUnitConstraint;

public class ConstraintFactory {

    public static UIConstraints unitConstrains(float x, float y, float width, float height) {
        UIConstraints constraints = new UIConstraints();
        constraints.addX(new UIUnitConstraint(x));
        constraints.addY(new UIUnitConstraint(y));
        constraints.addWidth(new UIUnitConstraint(width));
        constraints.addHeight(new UIUnitConstraint(height));

        return constraints;
    }

    public static UIConstraints fullscreen() {
        UIConstraints constraints = new UIConstraints();
        constraints.addX(new UIPixelConstraint(0));
        constraints.addY(new UIPixelConstraint(0));
        constraints.addWidth(new UIRelativeConstraint(1));
        constraints.addHeight(new UIRelativeConstraint(1));

        return constraints;
    }
}
