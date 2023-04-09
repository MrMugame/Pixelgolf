package gui;

import graphics.GameWindow;
import gui.components.UIContainer;
import gui.constraints.UIPixelConstraint;

import java.awt.*;

public class UIRenderer {
    private final UIContainer container = new UIContainer();

    public UIRenderer() {}

    public void update(float dt) {
        container.getConstraints().addX(new UIPixelConstraint(0));
        container.getConstraints().addY(new UIPixelConstraint(0));
        container.getConstraints().addWidth(new UIPixelConstraint(GameWindow.get().WIDTH));
        container.getConstraints().addHeight(new UIPixelConstraint(GameWindow.get().HEIGHT));
        container.updateInternally(dt);
    }

    public void render(Graphics2D g) {
        container.renderInternally(g);
    }

    public UIContainer getContainer() {
        return container;
    }
}
