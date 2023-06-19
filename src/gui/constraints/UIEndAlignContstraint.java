package gui.constraints;

import gui.UIComponent;
import gui.UIConstraint;

public class UIEndAlignContstraint extends UIConstraint {
    // Rückblickend hätte man sowas Ähnliches, wie z.B. ein add(constraints...) Constraint machen können.
    // Dann könnte man anstatt so: new UIEndAlignConstraint(new UIUnitConstraint(2))
    // so machen: new UIAddConstraint(new UIRelativeConstraint(1), new UIUnitConstraint(-2))
    // dann wäre auch der UIUnitConstraint schöner, welcher gerade ja ein bisschen ein Chaos ist

    private UIConstraint constraint;

    public UIEndAlignContstraint() {
        this.constraint = new UIPixelConstraint(0);
    }

    public UIEndAlignContstraint(UIConstraint constraint) {
        this.constraint = constraint;
    }

    @Override
    public int getValue(UIComponent component, boolean vertical) {
        if (vertical) {
            return component.getParent().getConstraints().height - component.getConstraints().heightCt.getValue(component, true) - constraint.getValue(component, true);
        } else {
            return component.getParent().getConstraints().width - component.getConstraints().widthCt.getValue(component, false) - constraint.getValue(component, false);
        }
    }
}
