package gui.components.specific;

import assets.Assets;
import gui.ConstraintFactory;
import gui.components.UIClickable;
import gui.components.UIText;

import java.awt.*;

public class UITextButton extends UIClickable {

    private String text;
    private UIText label;

    private static final Color COLOR = new Color(1f, 1f, 1f);

    public UITextButton(String text) {
        this.text = text;
    }

    @Override
    protected void init() {
        label = new UIText(text, COLOR, Assets.DEFAULT_FONT_BOLD, 38, false);
        label.setConstraints(ConstraintFactory.fullscreen());

/*        UIComponent block = new UIBlock(new Color(0,0,1));
        block.getConstraints().addX(new UIRelativeConstraint(0));
        block.getConstraints().addY(new UIRelativeConstraint(0));
        block.getConstraints().addWidth(new UIRelativeConstraint(1));
        block.getConstraints().addHeight(new UIRelativeConstraint(1));*/

        //super.add(block);

        super.add(label);
    }

    @Override
    protected void onHoverEnter() {
        label.setColor(Assets.COLOR_HIGHLIGHT);
    }

    @Override
    protected void onHoverLeave() {
        label.setColor(COLOR);
    }
}
