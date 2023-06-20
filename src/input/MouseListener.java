package input;

import game.Transform;
import graphics.GameWindow;
import physics.Vector2D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class MouseListener implements java.awt.event.MouseListener, MouseWheelListener {
    private static MouseListener instance;

    private boolean[] buttons = new boolean[20];

    private MouseListener() {}

    public static MouseListener get() {
        if (instance == null) {
            instance = new MouseListener();
            GameWindow.get().getWindow().addMouseListener(instance);
        }
        return instance;
    }

    public Vector2D getMousePosition() {
        GameWindow w = GameWindow.get();
        Point mouse = MouseInfo.getPointerInfo().getLocation();
        SwingUtilities.convertPointFromScreen(mouse, w.getWindow());
        Insets insets = w.getWindow().getInsets();
        mouse.x -= insets.left;
        mouse.y -= insets.top;

        // Clamp to window size
        mouse.x = Math.max(0, Math.min(w.WIDTH, mouse.x));
        mouse.y = Math.max(0, Math.min(w.HEIGHT, mouse.y));

        // Translate to the center
        Vector2D pos = new Vector2D(mouse.x, mouse.y);

        return Transform.fromScreenPosition(pos);
    }

    public Vector2D getMousePositionScreen() {
        GameWindow w = GameWindow.get();
        Point mouse = MouseInfo.getPointerInfo().getLocation();
        SwingUtilities.convertPointFromScreen(mouse, w.getWindow());
        Insets insets = w.getWindow().getInsets();
        mouse.x -= insets.left;
        mouse.y -= insets.top;

        return new Vector2D(mouse.x, mouse.y);
    }

    public void reset() {
        buttons = new boolean[20];
    }

    public boolean isPressed(int code) {
        return buttons[code];
    }

    @Override
    public void mousePressed(MouseEvent e) {
        buttons[e.getButton()] = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        buttons[e.getButton()] = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {}
}
