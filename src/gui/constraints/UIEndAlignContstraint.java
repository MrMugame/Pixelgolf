package gui.constraints;

import gui.UIComponent;
import gui.UIConstraint;

public class UIEndAlignContstraint extends UIConstraint {
    // TODO: In Hindsight something like an add(constraints...) constraint would have probably made more sense but its to late now i think
    // So you could do stuff like this: new AddConstraint(new UIRelativeConstraint(1), new UIUnitConstraint(-2));

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
