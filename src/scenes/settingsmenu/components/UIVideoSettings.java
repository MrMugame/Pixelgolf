package scenes.settingsmenu.components;

import assets.Assets;
import graphics.GameWindow;
import gui.ConstraintFactory;
import gui.UIComponent;
import gui.components.UIText;

import java.awt.*;

public class UIVideoSettings extends UIComponent {

    public UIVideoSettings() {}

    @Override
    protected void init() {
/*        UIText text = new UIText("Video", new Color(0,0,0), Assets.DEFAULT_FONT, 30, false);
        text.setConstraints(ConstraintFactory.unitConstrains(0, 0, 20, 3));
        add(text);*/

        UIDropDown dropDown = new UIDropDown(0, "Fenster", "Vollbild");
        dropDown.setConstraints(ConstraintFactory.unitConstrains(-8, 0, 8, 1.5f));
        dropDown.addListener((i) -> {
            System.out.println(i);
            switch (i) {
                case 0:
                    GameWindow.get().setWindowed();
                    break;
                case 1:
                    GameWindow.get().setFullscreen();
                    break;
            }
        });
        add(dropDown);

        UIText text = new UIText("Videomodus", new Color(1f, 1f, 1f), Assets.DEFAULT_FONT, 22, false);
        text.setConstraints(ConstraintFactory.unitConstrains(0, 0, 10, 1.5f));
        add(text);
    }
}
