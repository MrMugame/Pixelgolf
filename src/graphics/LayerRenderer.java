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
        Camera c = GameWindow.get().getScene().getCamera();
        for (GraphicComponent component : components) {
            Transform t = component.parent.getTransform();
            Vector2D pos = Transform.toScreenPosition(t.position.add(c.getTranslation()));
            Vector2D size = Transform.toScreenSize(t.size);


            /*
            // Mit Texture Paint malen um Subpixelgenauigkeit zu erreichen
            // Auskommentiert weil extrem langsam

            AffineTransform origin = g.getTransform();

            g.translate(pos.x, pos.y);
            g.setPaint(new TexturePaint(component.getTexture(), new Rectangle((int) size.x, (int) size.y)));
            g.fillRect(0, 0, (int) size.x, (int) size.y);

            g.setTransform(origin);
            */


            g.drawImage(component.getTexture(), (int) pos.x, (int) pos.y, (int) size.x, (int) size.y, null);
        }
    }
}
