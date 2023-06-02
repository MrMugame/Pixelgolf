package gui.constraints;

import gui.UIComponent;
import gui.UIConstraint;
import gui.components.UIImage;

import java.awt.image.BufferedImage;

public class UIImageFitConstraint extends UIConstraint {
    @Override
    public int getValue(UIComponent component, boolean vertical) {
        BufferedImage image = ((UIImage) component).getImage();
        float aspectImage = (float) image.getWidth() / image.getHeight();
        float aspectParent = (float) component.getParent().getConstraints().width / component.getParent().getConstraints().height;

        if (vertical) {
            if (aspectImage > aspectParent) {
                return component.getParent().getConstraints().height;
            } else {
                return (int)(component.getConstraints().widthCt.getValue(component, false) / aspectImage);
            }
        } else {
            if (aspectImage > aspectParent) {
                return (int)(component.getConstraints().heightCt.getValue(component, true) * aspectImage);
            } else {
                return component.getParent().getConstraints().width;
            }
        }
    }
}
