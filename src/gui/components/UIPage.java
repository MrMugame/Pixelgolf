package gui.components;

import gui.ConstraintFactory;
import gui.UIComponent;
import gui.UIConstraints;
import gui.constraints.UIPixelConstraint;
import gui.constraints.UIRelativeConstraint;

import java.util.ArrayList;
import java.util.Arrays;

public class UIPage extends UIComponent {

    private int index = 0;

    private ArrayList<UIComponent> pages = new ArrayList<>();

    public UIPage(UIComponent... components) {
        pages.addAll(Arrays.asList(components));

        for (UIComponent component : pages) {
            component.setConstraints(ConstraintFactory.fullscreen());
        }

        add(pages.get(0));
    }

    public void switchPage(int page) {
        remove(pages.get(index));
        index = page;
        add(pages.get(index));
    }
}
