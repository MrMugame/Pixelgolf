package scenes.settingsmenu.components;

import assets.Assets;
import gui.ConstraintFactory;
import gui.UIConstraints;
import gui.components.UIBlock;
import gui.components.UIClickable;
import gui.components.UIText;
import gui.constraints.UIPixelConstraint;
import gui.constraints.UIRelativeConstraint;
import gui.constraints.UIUnitConstraint;

import java.awt.*;

public class UIDropOption extends UIClickable {
    private String text;

    public UIDropOption(String text) {
        this.text = text;
    }

    // TODO: FIX constraints not being calculated between update and render step
    @Override
    protected void init() {
        UIText text = new UIText(this.text, new Color(0, 0, 0), Assets.DEFAULT_FONT, 20, false);
        UIBlock block = new UIBlock(Assets.COLOR_HIGHLIGHT, new BasicStroke(4));

        text.setConstraints(ConstraintFactory.fullscreen());
        text.getConstraints().addX(new UIUnitConstraint(0.25f));
        block.setConstraints(ConstraintFactory.fullscreen());

        add(block);
        add(text);
    }

    public void updateText(String text) {
        this.text = text;
        removeAll();
        init();
    }
}
