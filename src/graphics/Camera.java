package graphics;

import input.KeyboardListener;
import physics.Vector2D;

import static java.awt.event.KeyEvent.*;

public class Camera {
    protected Vector2D position;
    protected float zoom = 1.0f;

    public Camera() {
        position = new Vector2D(0, 0);
    }

    public void update(float dt) {}

    public Vector2D getTranslation() {
        return position.invert();
    }

    public float getZoom() {
        return zoom;
    }

    public Vector2D getPosition() {
        return position;
    }

    public void setPosition(Vector2D position) {
        this.position = position;
    }
}
