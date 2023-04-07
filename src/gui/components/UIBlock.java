package gui.components;

import gui.UIComponent;

import java.awt.*;

public class UIBlock extends UIComponent {

    private Color color;

    public UIBlock(float r, float g, float b) {
        color = new Color(r, g, b);
    }

    public UIBlock(float grey) {
        color = new Color(grey, grey, grey);
    }

    public UIBlock(Color color) {
        this.color = color;
    }

    @Override
    protected void render(Graphics2D graphics) {
        graphics.setColor(color);
        graphics.fillRect(getConstraints().x, getConstraints().y, getConstraints().width, getConstraints().height);
    }
}
