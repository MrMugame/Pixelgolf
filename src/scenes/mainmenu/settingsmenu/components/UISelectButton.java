package scenes.mainmenu.settingsmenu.components;

import assets.Assets;
import gui.ConstraintFactory;
import gui.SelectListener;
import gui.UIComponent;
import gui.components.UIBlock;
import gui.components.UIClickable;
import gui.components.UIImage;
import gui.components.UIText;
import gui.constraints.UIImageAspectConstraint;
import gui.constraints.UIUnitConstraint;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class UISelectButton extends UIClickable {
    private ArrayList<SelectListener> listeners = new ArrayList<>();
    private final ArrayList<String> options;
    private int selected = 0;

    private UIComponent text;

    private UIComponent hovered, pressed;

    public UISelectButton(int start, String... options) {
        this.options = new ArrayList<>(Arrays.asList(options));
        selected = start;
    }

    @Override
    protected void init() {
        UIImage block = new UIImage("ui/settings_button.png");
        block.setConstraints(ConstraintFactory.fullscreen());
        block.getConstraints().addHeight(new UIImageAspectConstraint());
        add(block);

        hovered = new UIImage("ui/settings_button_hover.png");
        hovered.setConstraints(ConstraintFactory.fullscreen());

        pressed = new UIImage("ui/settings_button_press.png");
        pressed.setConstraints(ConstraintFactory.fullscreen());

        updateText();

        super.addListener(() -> {
            selected += 1;
            if (selected == options.size()) selected = 0;
            notifyListeners(selected);
            updateText();
        });
    }

    private void updateText() {
        remove(text);
        text = new UIText(this.options.get(selected), new Color(0, 0, 0), Assets.DEFAULT_FONT, 14, true);
        text.setConstraints(ConstraintFactory.fullscreen());
        add(text);
    }

    private void notifyListeners(int index) {
        for (SelectListener listener : listeners) {
            listener.run(index);
        }
    }

    @Override
    protected void onHoverEnter() {
        add(hovered);
    }

    @Override
    protected void onHoverLeave() {
        remove(hovered);
    }

    @Override
    protected void onPressStart() {
        add(pressed);
    }

    @Override
    protected void onPressEnd() {
        remove(pressed);
    }

    public void addListener(SelectListener listener) {
        listeners.add(listener);
    }
}
