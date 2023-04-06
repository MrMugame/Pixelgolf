package game.graphics;

import Assets.Assets;

import java.awt.*;

public class StaticGraphic extends GraphicComponent {
    private String path;

    public StaticGraphic(String p) {
        path = p;
    }

    @Override
    public void update(float dt) {}

    @Override
    public Image getTexture() {
        return Assets.loadTexture(path);
    }
}
