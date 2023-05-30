package assets;

import scenes.levels.LevelLoader;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Objects;

public class Assets {

    public static final String DEFAULT_FONT = "pixelgold.ttf";
    private static HashMap<String, Font> fonts = new HashMap<>();

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
                Font font = Font.createFont(Font.TRUETYPE_FONT, Assets.class.getResource(path).openStream());
                GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(font);
                fonts.put(path, font);
            } catch(IOException | FontFormatException | NullPointerException e) {
                System.err.println("Konnte Font nicht laden!");
                return null;
            }
        }

        return fonts.get(path);
    }

    public static File getFile(Class<?> c, String path) {
        try {
            // Zu URI konvertieren um komisches Escaping von z.B. Leerzeichen zu vermeiden
            return new File(new URI(Objects.requireNonNull(c.getResource(path)).toString()).getPath());
        } catch (URISyntaxException e) {
            System.err.println("Konnte Datei nicht Finden: " + path);
            return null;
        }
    }
}
