package main;

import game.GameObject;
import game.graphics.GraphicComponent;
import game.Transform;
import physics.Vector2D;

import java.awt.*;
import java.util.ArrayList;

public class LayerRenderer {
    public final int Zindex;

    public ArrayList<GraphicComponent> components = new ArrayList<>();

    public LayerRenderer(int z) {
        Zindex = z;
    }

    public void add(GameObject go) {
        GraphicComponent comp = go.getGraphicComponent();
        components.add(comp);
    }

    public void render(Graphics2D g) {
        Camera c = GameWindow.get().getScene().getCamera();
        for (GraphicComponent component : components) {
            Transform t = component.parent.getTransform();
            Vector2D pos = Transform.toScreenPosition(t.position.add(c.getTranslation()));
            Vector2D size = Transform.toScreenSize(t.size);
            g.drawImage(component.getTexture(), (int) pos.x, (int) pos.y, (int) size.x, (int) size.y, null);
        }
    }
}
