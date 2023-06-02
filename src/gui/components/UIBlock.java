package gui.components;

import gui.UIComponent;

import java.awt.*;

public class UIBlock extends UIComponent {

    private Color color;

    public UIBlock(Color color) {
        this.color = color;
    }

    @Override
    protected void render(Graphics2D g) {
        g.setColor(color);
        g.fillRect(getX(), getY(), getWidth(), getHeight());
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
