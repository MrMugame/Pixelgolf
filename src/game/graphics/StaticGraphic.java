package game.graphics;

import assets.Assets;

import java.awt.*;

public class StaticGraphic extends GraphicComponent {
    private String path;
    private Image image;

    public StaticGraphic(String path) {
        this.path = path;
    }

    @Override
    public void update(float dt) {}

    @Override
    public Image getTexture() {
        if (image == null) {
            image = Assets.loadImage(path);
        }
        return image;
    }
}
