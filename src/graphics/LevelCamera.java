package graphics;

import input.KeyboardListener;
import physics.Vector2D;
import scenes.levels.Level;

import static java.awt.event.KeyEvent.*;

public class LevelCamera extends Camera {
    public LevelCamera() {}

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

        Vector2D oldPosition = position.scale(1);
        position = position.add(movement.normalize().scale(offset));

        if (-position.y > ((Level) GameWindow.get().getScene()).getMapHeight() || -position.y < 0) {
            position.y = oldPosition.y;
        }
        if (position.x > ((Level) GameWindow.get().getScene()).getMapWidth() || position.x < 0) {
            position.x = oldPosition.x;
        }
    }
}
