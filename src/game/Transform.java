package game;

import graphics.GameWindow;
import physics.Vector2D;

public class Transform {
    public static final int SCALE_FACTOR = 100;

    public Vector2D position;
    public float rotation; // Radians / Bogenmaß
    public Vector2D size;
    public int Zindex;
    public Vector2D origin = new Vector2D(0, 0); // TODO : not handled by physics

    public Transform(Vector2D position, Vector2D size, float rotation, int Zindex) {
        this.position = position;
        this.size = size;
        this.Zindex = Zindex;
        this.rotation = rotation;
    }

    public void translate(Vector2D add) {
        position = position.add(add);
    }

    public Vector2D getCenter() {
        return position.add(size.scale(0.5f).invertY());
    }

    public static Vector2D toScreenSize(Vector2D size) {
        return size.scale(SCALE_FACTOR).scale(GameWindow.get().getScene().getCamera().getZoom());
    }

    public static Vector2D toScreenPosition(Vector2D position) {
        Vector2D w = new Vector2D((float) GameWindow.get().WIDTH/2, (float) GameWindow.get().HEIGHT/2);
        Vector2D pos = position.scale(GameWindow.get().getScene().getCamera().getZoom()).invertY().scale(SCALE_FACTOR).add(w);
        return pos;
    }

    public static Vector2D fromScreenPosition(Vector2D position) {
        Vector2D w = new Vector2D((float) GameWindow.get().WIDTH/2, (float) GameWindow.get().HEIGHT/2);
        Vector2D pos = position.sub(w).scale((float) 1/SCALE_FACTOR).invertY().scale(1.0f/GameWindow.get().getScene().getCamera().getZoom()).add(GameWindow.get().getScene().getCamera().getPosition());
        return pos;
    }

    public static Vector2D rotateVector(Vector2D input, float radians) {
        float cos = (float) Math.cos(radians);
        float sin = (float) Math.sin(radians);

        // https://en.wikipedia.org/wiki/Rotation_matrix
        return new Vector2D(input.x * cos - input.y * sin,
                input.x * sin + input.y * cos);
    }
}
