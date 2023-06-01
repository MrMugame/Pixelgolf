package scenes.levels.components;

import assets.Assets;
import gui.ConstraintFactory;
import gui.UIComponent;
import gui.components.UIText;

import java.awt.*;

public class UIHUD extends UIComponent {

    public UIHUD() {}

    @Override
    protected void init() {
        UIText text = new UIText("HUD", new Color(0,0,0), Assets.DEFAULT_FONT, 20, true);
        text.setConstraints(ConstraintFactory.unitConstrains(0,0, 10, 10));
        add(text);
    }
}
