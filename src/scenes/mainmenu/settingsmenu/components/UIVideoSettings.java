package scenes.mainmenu.settingsmenu.components;

import assets.Assets;
import graphics.GameWindow;
import gui.ConstraintFactory;
import gui.UIComponent;
import gui.components.UIText;
import gui.constraints.UIEndAlignContstraint;
import gui.constraints.UIPixelConstraint;
import gui.constraints.UIUnitConstraint;

import java.awt.*;

public class UIVideoSettings extends UIComponent {

    public UIVideoSettings() {}

    @Override
    protected void init() {
/*        UIText text = new UIText("Video", new Color(0,0,0), Assets.DEFAULT_FONT, 30, false);
        text.setConstraints(ConstraintFactory.unitConstrains(0, 0, 20, 3));
        add(text);*/

        // UIDropDown dropDown = new UIDropDown(0, "Fenster", "Vollbild");
        UISelectButton button = new UISelectButton("Fenster", "Vollbild");
        button.setConstraints(ConstraintFactory.unitConstrains(0, 0, 8, 1.5f));
        button.getConstraints().addX(new UIEndAlignContstraint());
        button.addListener((i) -> {
            switch (i) {
                case 0:
                    GameWindow.get().setWindowed();
                    break;
                case 1:
                    GameWindow.get().setFullscreen();
                    break;
            }
        });
        add(button);

        UIText text = new UIText("Videomodus", new Color(1f, 1f, 1f), Assets.DEFAULT_FONT, 22, false);
        text.setConstraints(ConstraintFactory.unitConstrains(0, 0, 10, 1.5f));
        add(text);
    }
}
