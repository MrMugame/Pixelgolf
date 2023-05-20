package graphics;

import input.KeyboardListener;
import physics.Vector2D;

import static java.awt.event.KeyEvent.*;
import static java.awt.event.KeyEvent.VK_D;

public class DebugCamera extends Camera {
    // TODO: Make Camera a GameObject maybe?
    public DebugCamera() {}

    @Override
    public void update(float dt) {
        KeyboardListener listener = KeyboardListener.get();

        float offset = 0.005f * dt;
        Vector2D movement = new Vector2D();

        if (listener.isPressed(VK_W)) {
            movement = movement.add(new Vector2D(0, 1));
        }
        if (listener.isPressed(VK_S)) {
            movement = movement.add(new Vector2D(0, -1));
        }
        if (listener.isPressed(VK_A)) {
            movement = movement.add(new Vector2D(-1, 0));
        }
        if (listener.isPressed(VK_D)) {
            movement = movement.add(new Vector2D(1, 0));
        }

        if (movement.isNullVector()) return;

        position = position.add(movement.normalize().scale(offset));
    }
}
