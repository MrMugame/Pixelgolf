package Assets;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Assets {
    private static HashMap<String, Image> textures = new HashMap();

    public static Image loadTexture(String path) {
        if (textures.containsKey(path)) {
            return textures.get(path);
        }
        try {
            Image img = ImageIO.read(new File("src/Assets/" + path));
            textures.put(path, img);
            return img;
        } catch(IOException e) {
            return null;
        }
    }
}
