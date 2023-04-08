package gui;

import java.awt.*;
import java.util.ArrayList;

public abstract class UIComponent {
    private ArrayList<UIComponent> childs = new ArrayList<>();
    private UIConstraints constraints = new UIConstraints();
    private UIComponent parent;

    public void add(UIComponent component) {
        component.parent = this;
        component.init();
        childs.add(component);
    }

    protected void updateInternally() {
        constraints.calculate(this);
        update();
        for (UIComponent child : childs) {
            child.updateInternally();
        }
    }

    protected void renderInternally(Graphics2D g) {
        render(g);
        for (UIComponent child : childs) {
            child.renderInternally(g);
        }
    }

    protected void render(Graphics2D g) {}

    protected void update() {}

    protected void init() {}


    public UIComponent getParent() {
        return parent;
    }

    public UIConstraints getConstraints() {
        return constraints;
    }
}
