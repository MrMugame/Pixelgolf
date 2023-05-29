package scenes.levels.components;

import assets.Assets;
import graphics.GameWindow;
import gui.ConstraintFactory;
import gui.UIComponent;
import gui.components.UIContainer;
import gui.components.UIText;
import gui.constraints.UICenterConstraint;
import gui.constraints.UIUnitConstraint;
import input.KeyboardListener;

import java.awt.*;
import java.awt.event.KeyEvent;

public class EscapeMenuUI extends UIComponent {

    private boolean active = false;
    private boolean pressed = false;
    private UIComponent menu = new UIContainer();

    public EscapeMenuUI() {}

    @Override
    protected void update() {
        KeyboardListener listener = KeyboardListener.get();

        if (listener.isPressed(KeyEvent.VK_ESCAPE) && !active && !pressed) {
            add(menu);
            GameWindow.get().getScene().pause();
            pressed = true;
            active = true;
        } else if (listener.isPressed(KeyEvent.VK_ESCAPE) && active && !pressed) {
            remove(menu);
            GameWindow.get().getScene().pursue();
            pressed = true;
            active = false;
        } else if (!listener.isPressed(KeyEvent.VK_ESCAPE) && pressed) {
            pressed = false;
        }
    }

    @Override
    protected void init() {
        menu.getConstraints().addX(new UICenterConstraint());
        menu.getConstraints().addY(new UICenterConstraint());
        menu.getConstraints().addHeight(new UIUnitConstraint(10));
        menu.getConstraints().addWidth(new UIUnitConstraint(5));

        UIComponent text = new UIText("Escape", new Color(0,0,0), Assets.DEFAULT_FONT, 24, true);
        text.setConstraints(ConstraintFactory.fullscreen());
        menu.add(text);
    }
}
