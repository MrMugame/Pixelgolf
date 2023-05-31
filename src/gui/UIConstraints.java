package gui;

public class UIConstraints {

    // Initialize to -100, so it initially renders offscreen to prevent graphics bug
    public int width, height, x, y = -100;

    public UIConstraint widthCt, heightCt, xCt, yCt;

    public UIConstraints() {}

    protected void calculate(UIComponent component) {
        width = widthCt.getValue(component, false);
        height = heightCt.getValue(component, true);
        x = xCt.getValue(component, false);
        y = yCt.getValue(component, true);

        if (component.getParent() != null) {
            x += component.getParent().getConstraints().x;
            y += component.getParent().getConstraints().y;
        }
    }

    public void addX(UIConstraint constraint) {
        xCt = constraint;
    }

    public void addY(UIConstraint constraint) {
        yCt = constraint;
    }

    public void addWidth(UIConstraint constraint) {
        widthCt = constraint;
    }

    public void addHeight(UIConstraint constraint) {
        heightCt = constraint;
    }

    public UIConstraints copy() {
        UIConstraints constraints = new UIConstraints();
        constraints.addX(xCt);
        constraints.addY(yCt);
        constraints.addWidth(widthCt);
        constraints.addHeight(heightCt);

        return constraints;
    }

}
