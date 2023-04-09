package game.graphics;

import assets.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;

public class StaticGraphic extends GraphicComponent {
    private String path;
    private BufferedImage image;

    public StaticGraphic(String path) {
        this.path = path;
    }

    @Override
    public void update(float dt) {}

    @Override
    public BufferedImage getTexture() {
        if (image == null) {
            image = Assets.loadImage(path);
        }
        return image;
    }
}
