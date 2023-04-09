package gui.components;

import assets.Assets;
import gui.UIComponent;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UIImage extends UIComponent {
    private BufferedImage image;

    public UIImage(String path) {
        image = Assets.loadImage(path);
    }

    @Override
    protected void render(Graphics2D g) {
        g.drawImage(image, getX(), getY(), getWidth(), getHeight(), null);
    }

    public BufferedImage getImage() {
        return image;
    }
}
