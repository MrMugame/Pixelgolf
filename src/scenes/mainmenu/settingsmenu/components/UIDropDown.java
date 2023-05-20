package scenes.mainmenu.settingsmenu.components;

import gui.SelectListener;
import gui.UIComponent;
import gui.constraints.UIPixelConstraint;
import gui.constraints.UIRelativeConstraint;
import input.MouseListener;

import java.util.ArrayList;
import java.util.Arrays;

import static java.awt.event.MouseEvent.BUTTON1;

public class UIDropDown extends UIComponent {
    private ArrayList<String> options = new ArrayList<>();
    private ArrayList<SelectListener> listeners = new ArrayList<>();

    private boolean dropped = false;
    private boolean justSwitched = false;
    private int selected;

    public UIDropDown(int index, String... options) {
        selected = index;
        this.options.addAll(Arrays.asList(options));
    }

    @Override
    protected void init() {
        drop();
    }

    @Override
    protected void update() {
        if (justSwitched && !MouseListener.get().isPressed(BUTTON1)) {
            justSwitched = false;
        }
    }

    private void drop() {
        removeAll();

        generateOption(selected, 0);

        if (dropped) {
            for (int i = 0; i < options.size(); i++) {
                generateOption(i, i+1);
            }
        }
    }

    private void generateOption(int index, int offset) {
        UIDropOption option = new UIDropOption(options.get(index));
        option.getConstraints().addX(new UIPixelConstraint(0));
        option.getConstraints().addY(new UIRelativeConstraint(offset));
        option.getConstraints().addWidth(new UIRelativeConstraint(1));
        option.getConstraints().addHeight(new UIRelativeConstraint(1));
        // TODO: ugly variable passing
        option.addListener(() -> {
            if (justSwitched) return;
            justSwitched = true;

            if (selected != index) notifyListeners(index);
            selected = index;
            dropped = !dropped;
            drop();
        });
        add(option);
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
