package gui.components;

import gui.UIComponent;
import gui.constraints.UICenterConstraint;
import gui.constraints.UIPixelConstraint;
import gui.constraints.UIRelativeConstraint;

import java.awt.*;

public class UIButton extends UIComponent {
    private String text;
    private Color background, foreground;

    private int fontSize;

    public UIButton(String text, Color background, Color foreground, int fontSize) {
        this.text = text;
        this.background = background;
        this.foreground = foreground;
        this.fontSize = fontSize;
    }

    @Override
    protected void init() {
        // TODO: Add some kind of default font in assets
        UIComponent label = new UIText(text, foreground, "roboto.ttf", fontSize, true);
        label.getConstraints().addX(new UICenterConstraint());
        label.getConstraints().addY(new UICenterConstraint());
        label.getConstraints().addWidth(new UIRelativeConstraint(0.75f));
        label.getConstraints().addHeight(new UIRelativeConstraint(1));

        UIComponent block = new UIBlock(background);
        block.getConstraints().addX(new UIRelativeConstraint(0));
        block.getConstraints().addY(new UIRelativeConstraint(0));
        block.getConstraints().addWidth(new UIRelativeConstraint(1));
        block.getConstraints().addHeight(new UIRelativeConstraint(1));

        block.add(label);
        super.add(block);
    }

    @Override
    protected void onHoverEnter() {
        System.out.println("Hovered");
    }
}
