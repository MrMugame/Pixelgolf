package gui.constraints;

import gui.UIComponent;
import gui.UIConstraint;
import gui.components.UIImage;

import java.awt.image.BufferedImage;

public class UIImageAspectConstraint extends UIConstraint {

    public UIImageAspectConstraint() {}

    @Override
    public int getValue(UIComponent component, boolean vertical) {
        BufferedImage image = ((UIImage) component).getImage();
        float ratio = (float) image.getWidth() / image.getHeight();
        if (vertical) {
            return (int)(component.getConstraints().widthCt.getValue(component, false) / ratio);
        } else {
            return (int)(component.getConstraints().heightCt.getValue(component, true) * ratio);
        }
    }
}

