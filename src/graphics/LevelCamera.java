package graphics;

import game.GameObject;
import input.KeyboardListener;
import physics.Vector2D;
import scenes.levels.Level;

import static java.awt.event.KeyEvent.*;

public class LevelCamera extends Camera {
    private Vector2D velocity = new Vector2D();
    private boolean centering = false;
    private boolean startAnimation = false;

    public LevelCamera() {}

    @Override
    public void update(float dt) {
/*        if (startAnimation) {
            Vector2D diagonal = new Vector2D();
            diagonal.x = ((Level) GameWindow.get().getScene()).getMapWidth();
            diagonal.y = -((Level) GameWindow.get().getScene()).getMapHeight();
            if (velocity.isNullVector()) position = diagonal.scale(0.5f);


            zoom = 1/diagonal.magnitude()*10;
        }*/

        GameObject ball = GameWindow.get().getScene().getGameObject("ball");
        if (ball == null) return;

        Vector2D distance = ball.getTransform().position.sub(position);
        if (distance.magnitude() > 2f || centering) {
            centering = true;
            velocity = distance.scale(2f);
        }
        if (centering && distance.magnitude() < 0.1f) {
            centering = false;
            velocity = new Vector2D();
        }

        position = position.add(velocity.scale(dt/1000));

/*        float offset = 0.005f * dt;
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
        }*/

/*        if (movement.isNullVector()) return;*/

/*        Vector2D oldPosition = position.scale(1);
        position = position.add(movement.normalize().scale(offset));*/

/*        if (-position.y > ((Level) GameWindow.get().getScene()).getMapHeight() || -position.y < 0) {
            position.y = oldPosition.y;
        }
        if (position.x > ((Level) GameWindow.get().getScene()).getMapWidth() || position.x < 0) {
            position.x = oldPosition.x;
        }*/
    }

    public void startAnimation() {
        startAnimation = true;
    }
}
