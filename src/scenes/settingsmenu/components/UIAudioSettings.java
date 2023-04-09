package scenes.settingsmenu.components;

import assets.Assets;
import gui.ConstraintFactory;
import gui.UIComponent;
import gui.components.UIText;

import java.awt.*;

public class UIAudioSettings extends UIComponent {

    public UIAudioSettings() {}

    @Override
    protected void init() {
        UIText text = new UIText("Audio", new Color(0,0,0), Assets.DEFAULT_FONT, 30, false);
        text.setConstraints(ConstraintFactory.unitConstrains(0, 0, 20, 20));
        add(text);
    }
}
