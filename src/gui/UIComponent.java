package gui;

import java.awt.*;
import java.util.ArrayList;

public abstract class UIComponent {
    private ArrayList<UIComponent> children = new ArrayList<>();

    private UIConstraints constraints = new UIConstraints();

    private UIComponent parent;
    private boolean initialized = false;

    public void add(UIComponent component) {
        if (children.contains(component)) return;

        if (!component.initialized) {
            component.init();
            component.initialized = true;
        }

        component.parent = this;
        children.add(component);
    }

    public void remove(UIComponent component) {
        children.remove(component);
    }

    protected void updateInternally() {
        constraints.calculate(this);
        update();
        for (UIComponent child : children) {
            child.updateInternally();
        }
    }

    protected void renderInternally(Graphics2D g) {
        render(g);
        for (UIComponent child : children) {
            child.renderInternally(g);
        }
    }

    protected void render(Graphics2D g) {}

    protected void update() {}

    protected void init() {}


    public UIComponent getParent() {
        return parent;
    }

    public UIComponent getChild(int index) {
        return children.get(index);
    }

    public UIConstraints getConstraints() {
        return constraints;
    }

    public void setConstraints(UIConstraints constraints) {
        this.constraints = constraints;
    }

    protected int getWidth() {
        return getConstraints().width;
    }

    protected int getHeight() {
        return getConstraints().height;
    }

    protected int getX() {
        return getConstraints().x;
    }

    protected int getY() {
        return getConstraints().y;
    }
}
