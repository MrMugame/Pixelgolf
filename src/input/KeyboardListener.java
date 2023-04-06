package input;

import main.GameWindow;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardListener implements KeyListener {
    private static KeyboardListener instance;

    private boolean[] keys = new boolean[300];

    private KeyboardListener() {}

    public static KeyboardListener get() {
        if (instance == null) {
            instance = new KeyboardListener();
            GameWindow.get().getWindow().addKeyListener(instance);
        }
        return instance;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }

    public boolean isPressed(int keyCode) {
        return keys[keyCode];
    }
}
