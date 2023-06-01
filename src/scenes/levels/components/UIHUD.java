package scenes.levels.components;

import assets.Assets;
import gui.ConstraintFactory;
import gui.UIComponent;
import gui.components.UIImage;
import gui.components.UIText;
import gui.constraints.UIEndAlignContstraint;
import gui.constraints.UIImageAspectConstraint;
import gui.constraints.UIUnitConstraint;

import java.awt.*;

public class UIHUD extends UIComponent {

    public UIHUD() {}

    @Override
    protected void init() {

        UIImage image = new UIImage("ui/hud_star_0.png");
        image.getConstraints().addX(new UIEndAlignContstraint());
        image.getConstraints().addY(new UIUnitConstraint(0));
        image.getConstraints().addWidth(new UIUnitConstraint(10));
        image.getConstraints().addHeight(new UIImageAspectConstraint());
        add(image);
    }
}
