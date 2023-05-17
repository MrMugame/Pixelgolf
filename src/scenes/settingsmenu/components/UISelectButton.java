package scenes.settingsmenu.components;

import assets.Assets;
import gui.ConstraintFactory;
import gui.SelectListener;
import gui.UIComponent;
import gui.components.UIBlock;
import gui.components.UIClickable;
import gui.components.UIText;
import gui.constraints.UIUnitConstraint;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class UISelectButton extends UIClickable {
    private ArrayList<SelectListener> listeners = new ArrayList<>();
    private ArrayList<String> options;
    private int selected = 0;

    private UIComponent text;

    public UISelectButton(String... options) {
        this.options = new ArrayList<>(Arrays.asList(options));
    }

    @Override
    protected void init() {
        UIBlock block = new UIBlock(Assets.COLOR_HIGHLIGHT, new BasicStroke(4));
        block.setConstraints(ConstraintFactory.fullscreen());
        add(block);

        updateText();

        this.addListener(() -> {
            selected += 1;
            if (selected == options.size()) selected = 0;
            notifyListeners(selected);
            updateText();
        });
    }

    private void updateText() {
        remove(text);
        text = new UIText(this.options.get(selected), new Color(0, 0, 0), Assets.DEFAULT_FONT, 20, false);
        text.setConstraints(ConstraintFactory.fullscreen());
        text.getConstraints().addX(new UIUnitConstraint(0.25f));
        add(text);
    }

    private void notifyListeners(int index) {
        for (SelectListener listener : listeners) {
            listener.run(index);
        }
    }

    public void addListener(SelectListener listener) {
        listeners.add(listener);
    }
}
