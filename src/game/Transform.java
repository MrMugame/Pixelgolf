package game;

import graphics.GameWindow;
import physics.Vector2D;

public class Transform {
    public static final int SCALE_FACTOR = 100;

    public Vector2D position;
    public Vector2D size;
    public int Zindex;

    public Transform() {
        position = new Vector2D();
        size = new Vector2D();
        Zindex = 0;
    }

    public Transform(Vector2D pos, Vector2D s, int z) {
        position = pos;
        size = s;
        Zindex = z;
    }

    public Transform(int z) {
        position = new Vector2D();
        size = new Vector2D();
        Zindex = z;
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
}
