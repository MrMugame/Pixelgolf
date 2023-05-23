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

            // Mit Texture Paint malen um Subpixelgenauigkeit zu erreichen
            // Auskommentiert weil extrem langsam
            /*
            AffineTransform origin = g.getTransform();

            g.translate(pos.x, pos.y);
            g.setPaint(new TexturePaint(component.getTexture(), new Rectangle((int) size.x, (int) size.y)));
            g.fillRect(0, 0, (int) size.x, (int) size.y);

            g.setTransform(origin);
            */

/*            BufferedImage scaledImage = new BufferedImage((int) size.x, (int) size.y, BufferedImage.TYPE_INT_ARGB);

            AffineTransform transform = new AffineTransform();
            transform.setToScale((double) size.x / component.getTexture().getWidth(), (double) size.y / component.getTexture().getHeight());

            AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            op.filter(component.getTexture(), scaledImage);
            g.drawImage(scaledImage, (int) pos.x, (int) pos.y, (int) size.x, (int) size.y, null);

            */

            g.drawImage(component.getTexture(), (int) pos.x, (int) pos.y, (int) size.x, (int) size.y, null);


            //System.out.println((int) pos.x + " " + (int) pos.y + " " + (int) size.x + " " + (int) size.y);


            /* BufferedImage texture = component.getTexture();
            g.drawImage(texture, (int) pos.x, (int) pos.y, (int) (pos.x + size.x), (int) (pos.y + size.y), 0, 0, texture.getWidth(), texture.getHeight(), null);
            */
        }
    }
}
