package game.graphics;

import game.Component;
import physics.Vector2D;

import java.awt.image.BufferedImage;

public abstract class GraphicComponent extends Component {
    private Vector2D anchor = new Vector2D(0, 0);
    public abstract BufferedImage getTexture();
    public Vector2D getAnchor() {
        return anchor;
    }
    public void setAnchor(Vector2D anchor) {
        this.anchor = anchor;
    }
}
