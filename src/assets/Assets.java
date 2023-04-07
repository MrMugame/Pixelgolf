package assets;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class Assets {
    private static HashMap<String, Font> fonts = new HashMap<>();

    public static Image loadImage(String path) {
        try {
            return ImageIO.read(Objects.requireNonNull(Assets.class.getResource(path)));
        } catch(IOException e) {
            return null;
        }
    }

    public static Font loadFont(String path) {
        if (!fonts.containsKey(path)) {
            try {
                Font font = Font.createFont(Font.TRUETYPE_FONT, new File(Objects.requireNonNull(Assets.class.getResource(path)).getPath()));
                GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(font);
                fonts.put(path, font);
            } catch(IOException | FontFormatException | NullPointerException e) {
                System.err.println("Konnte Font nicht laden!");
                return null;
            }
        }

        return fonts.get(path);
    }
}
