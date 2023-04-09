package game.graphics;

import game.Component;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class GraphicComponent extends Component {
    public abstract BufferedImage getTexture();
}
