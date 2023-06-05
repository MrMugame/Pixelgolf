package game;

import graphics.Camera;
import graphics.GameWindow;
import physics.Vector2D;

public class Transform {
    public static final int SCALE_FACTOR = 100;

    public Vector2D position;
    public float rotation; // Radians / Bogenmaß
    public Vector2D size;
    public int Zindex;

    public Transform(Vector2D position, Vector2D size, float rotation, int Zindex) {
        this.position = position;
        this.size = size;
        this.Zindex = Zindex;
        this.rotation = rotation;
    }

    public void translate(Vector2D add) {
        position = position.add(add);
    }

    public static Vector2D toScreenSize(Vector2D size) {
        return size.scale(SCALE_FACTOR).scale(GameWindow.get().getScene().getCamera().getZoom());
    }

    public static Vector2D toScreenPosition(Vector2D position) {
        Camera camera = GameWindow.get().getScene().getCamera();
        Vector2D w = new Vector2D((float) GameWindow.get().WIDTH/2, (float) GameWindow.get().HEIGHT/2);
        return position.add(camera.getTranslation()).scale(camera.getZoom()).invertY().scale(SCALE_FACTOR).add(w);
    }

    public static float fromPositionToZoom(Vector2D position) {
        // Geht von der Kamera bei (0, 0) aus und rechnet den Zoom aus der nötig ist, dass der Punkt position in der oberen linken Ecke liegt (0, 0)

        // position.scale(camera.getZoom()).invertY().scale(SCALE_FACTOR).add(w) = new Vector(0, 0);
        // position.scale(camera.getZoom()) = new Vector(0, 0).sub(w).scale(1f/SCALE_FACTOR).invertY();
        // camera.getZoom() = new Vector(0, 0).sub(w).scale(1f/SCALE_FACTOR).invertY().magnitude() / position.magnitude();
        Vector2D w = new Vector2D((float) GameWindow.get().WIDTH/2, (float) GameWindow.get().HEIGHT/2);
        return new Vector2D(0, 0).sub(w).scale(1f/SCALE_FACTOR).invertY().magnitude() / position.magnitude();
    }


    public static Vector2D fromScreenPosition(Vector2D position) {
        Camera camera = GameWindow.get().getScene().getCamera();
        Vector2D w = new Vector2D((float) GameWindow.get().WIDTH/2, (float) GameWindow.get().HEIGHT/2);
        return position.sub(w).scale((float) 1/SCALE_FACTOR).invertY().scale(1.0f/camera.getZoom()).sub(camera.getTranslation());
    }
}
