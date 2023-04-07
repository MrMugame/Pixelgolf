package gui;

import input.MouseListener;
import physics.Vector2D;

import java.awt.*;
import java.util.ArrayList;

public abstract class UIComponent {
    private ArrayList<UIComponent> childs = new ArrayList<>();
    private UIConstraints constraints = new UIConstraints();
    private UIComponent parent;

    private boolean isHovered = false;

    public void add(UIComponent component) {
        component.parent = this;
        component.init();
        childs.add(component);
    }

    protected void updateInternally() {
        constraints.calculate(this);
        checkHover();
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

    private void checkHover() {
        Vector2D mouse = MouseListener.get().getMousePositionScreen();

        boolean vertical = getConstraints().y < mouse.y && mouse.y < (getConstraints().y + getConstraints().height);
        boolean horizontal = getConstraints().x < mouse.x && mouse.x < (getConstraints().x + getConstraints().width);

        if (vertical && horizontal && !isHovered) {
            isHovered = true;
            onHoverEnter();
        } else if (!(vertical && horizontal) && isHovered) {
            isHovered = false;
            onHoverLeave();
        }
    }

    protected void render(Graphics2D g) {}

    protected void update() {}

    protected void init() {};

    protected void onHoverEnter() {};
    protected void onHoverLeave() {};


    public UIComponent getParent() {
        return parent;
    }

    public UIConstraints getConstraints() {
        return constraints;
    }
}
