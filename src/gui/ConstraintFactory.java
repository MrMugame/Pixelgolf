package gui;

import gui.constraints.*;

// Könnte man deutlich besser und fähiger machen, es fehlt jedoch wie immer die Zeit und irgendwo auch der Sinn
public class ConstraintFactory {
    public static UIConstraints unitConstrains(float x, float y, float width, float height) {
        UIConstraints constraints = new UIConstraints();
        constraints.addX(new UIUnitConstraint(x));
        constraints.addY(new UIUnitConstraint(y));
        constraints.addWidth(new UIUnitConstraint(width));
        constraints.addHeight(new UIUnitConstraint(height));

        return constraints;
    }

    public static UIConstraints unitConstrains(float x, float y, float width, float height, boolean fullX, boolean fullY) {
        UIConstraints constraints = new UIConstraints();
        constraints.addX(new UIUnitConstraint(x, fullX));
        constraints.addY(new UIUnitConstraint(y, fullY));
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

    public static UIConstraints fullscreenFitImage() {
        UIConstraints constraints = new UIConstraints();
        constraints.addX(new UICenterConstraint());
        constraints.addY(new UICenterConstraint());
        constraints.addWidth(new UIImageFitConstraint());
        constraints.addHeight(new UIImageFitConstraint());

        return constraints;
    }
}
