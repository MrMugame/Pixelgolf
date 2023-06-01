package graphics;

import game.GameObject;
import game.graphics.GraphicComponent;
import game.Transform;
import physics.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class LayerRenderer {
    public final int Zindex;

    public ArrayList<GraphicComponent> components = new ArrayList<>();

    public LayerRenderer(int z) {
        Zindex = z;
    }

    public void add(GameObject go) {
        GraphicComponent comp = go.get(GraphicComponent.class);
        components.add(comp);
    }

    public void render(Graphics2D g) {
        Camera camera = GameWindow.get().getScene().getCamera();
        for (GraphicComponent component : components) {
            Transform transform = component.parent.getTransform();
            Vector2D pos = Transform.toScreenPosition(transform.position.add(camera.getTranslation()));
            Vector2D size = Transform.toScreenSize(transform.size);

            // Fälle separieren aufgrund von Performance Problemen
            if (transform.rotation == 0) {
                g.drawImage(component.getTexture(), (int) pos.x, (int) pos.y, (int) size.x, (int) size.y, null);
            } else {
                AffineTransform backup =  g.getTransform();

                if (transform.rotateCenter) {
                    g.rotate(transform.rotation, (int) (pos.x + size.x/2), (int) (pos.y + size.y/2));
                } else {
                    g.rotate(transform.rotation, (int) pos.x, (int) pos.y);
                }


                g.drawImage(component.getTexture(), (int) pos.x, (int) pos.y, (int) size.x, (int) size.y, null);

                g.setTransform(backup);
            }
        }
    }
}
