package gui.components;

import assets.Assets;
import gui.ClickListener;
import gui.UIComponent;
import input.MouseListener;
import physics.Vector2D;
import sound.SoundSystem;

import java.util.ArrayList;

import static java.awt.event.MouseEvent.BUTTON1;

public class UIClickable extends UIComponent {
    private ArrayList<ClickListener> listeners = new ArrayList<>();
    private boolean isHovered = false;
    private boolean debounce = false;

    @Override
    protected void updateInternally() {
        checkHover();
        checkClick();
        super.updateInternally();
    }

    private void checkHover() {
        Vector2D mouse = MouseListener.get().getMousePositionScreen();

        boolean vertical = getY() < mouse.y && mouse.y < (getY() + getHeight());
        boolean horizontal = getX() < mouse.x && mouse.x < (getX() + getWidth());

        MouseListener listener = MouseListener.get();

        if (vertical && horizontal && !isHovered && !listener.isPressed(BUTTON1)) {
            isHovered = true;
            onHoverEnter();
        } else if (!(vertical && horizontal) && isHovered) {
            isHovered = false;
            onHoverLeave();
        }
    }

    private void checkClick() {
        MouseListener listener = MouseListener.get();

        if (listener.isPressed(BUTTON1) && isHovered && !debounce) {
            onPressStart();
            debounce = true;
        } else if (!listener.isPressed(BUTTON1) && debounce) {
            SoundSystem.get().play(Assets.loadSound("sound/menu_click.wav"));

            onPressEnd();

            for (ClickListener l : listeners) {
                l.run();
            }
            debounce = false;
        }
    }

    public void addListener(ClickListener listener) {
        listeners.add(listener);
    }

    protected void onHoverEnter() {};
    protected void onHoverLeave() {};

    protected void onPressStart() {};
    protected void onPressEnd() {};
}
