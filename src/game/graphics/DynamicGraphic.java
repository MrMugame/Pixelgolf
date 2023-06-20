package game.graphics;

import java.awt.image.BufferedImage;

public class DynamicGraphic extends GraphicComponent {

    private final BufferedImage image;

    public DynamicGraphic(BufferedImage image) {
        this.image = image;
    }

    @Override
    public BufferedImage getTexture() {
        return image;
    }
}
