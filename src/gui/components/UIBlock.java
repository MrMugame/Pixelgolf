package gui.components;

import gui.UIComponent;

import java.awt.*;

public class UIBlock extends UIComponent {

    private Color color;
    private Stroke stroke;

    public UIBlock(Color color) {
        this.color = color;
    }

    public UIBlock(Color color, Stroke stroke) {
        this.color = color;
        this.stroke = stroke;
    }

    @Override
    protected void render(Graphics2D g) {
        if (stroke != null) {
            g.setColor(new Color(0,0,0));
            g.setStroke(stroke);
            g.drawRect(getConstraints().x, getConstraints().y, getConstraints().width, getConstraints().height);
        }

        g.setColor(color);
        g.fillRect(getConstraints().x, getConstraints().y, getConstraints().width, getConstraints().height);
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
