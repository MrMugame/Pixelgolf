package game.graphics;

import assets.Assets;
import game.Component;

import java.awt.image.BufferedImage;

public class StaticGraphic extends GraphicComponent {
    private String path;
    private BufferedImage image;

    public StaticGraphic(String path) {
        this.path = path;
    }

    @Override
    public BufferedImage getTexture() {
        if (image == null) {
            image = Assets.loadImage(path);
        }
        return image;
    }

    public void changePath(String path) {
        image = null;
        this.path = path;
    }
}
