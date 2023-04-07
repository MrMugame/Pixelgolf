package gui.constraints;

import gui.UIComponent;
import gui.UIConstraint;

public class UIPixelConstraint extends UIConstraint {
    private int pixels;

    public UIPixelConstraint(int pixels) {
        this.pixels = pixels;
    }

    @Override
    public int getValue(UIComponent component, boolean vertical) {
        return pixels;
    }
}
