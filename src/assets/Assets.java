package assets;

import sound.Sound;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Objects;

public class Assets {

    public static final String DEFAULT_FONT = "pixelgold.ttf";
    private static final HashMap<String, Font> fonts = new HashMap<>();

    public static BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(Objects.requireNonNull(Assets.class.getResource(path)));
        } catch(IOException | NullPointerException e) {
            System.err.println("Cant load texture: " + path);
            return null;
        }
    }

    public static Font loadFont(String path) {
        if (!fonts.containsKey(path)) {
            try {
                Font font = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(Assets.class.getResourceAsStream(path)));
                GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(font);
                fonts.put(path, font);
            } catch(IOException | FontFormatException | NullPointerException e) {
                System.err.println("Konnte Font nicht laden!");
                return null;
            }
        }

        return fonts.get(path);
    }

    public static Sound loadSound(String path) {
        try {
            return new Sound(new BufferedInputStream(Objects.requireNonNull(Assets.class.getResourceAsStream(path))));
        } catch (IOException | NullPointerException  e) {
            System.err.println("Konnte Sound nicht laden!");
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public static InputStream loadLevel(int number) {
        return Assets.class.getResourceAsStream("maps/level_" + number + ".xml");
    }
}
