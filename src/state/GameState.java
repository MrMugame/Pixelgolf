package state;

import java.io.*;
import java.net.URLDecoder;
import java.util.HashMap;

public class GameState implements Serializable {
    private static final String FILENAME = "savegame.txt";
    private static GameState instance;

    private final HashMap<Integer, LevelState> levels = new HashMap<>();

    private final HashMap<String, Boolean> properties = new HashMap<>();

    private GameState() {}

    public static GameState get() {
        if (instance == null) {
            instance = load();
        }
        return instance;
    }

    public LevelState getLevel(int i) {
        if (levels.get(i) == null) levels.put(i, new LevelState());
        return levels.get(i).copy(); // Copy um implizites Modifizieren zu vermeiden
    }

    public void setLevel(int i, LevelState level) {
        levels.put(i, level);
        save();
    }

    public boolean getProperty(String name) {
        if (properties.containsKey(name)) {
            return properties.get(name);
        }
        return false;
    }

    public void setProperty(String name, boolean value) {
        properties.put(name, value);
        save();
    }

    private static GameState load() {
        try {
            String path = URLDecoder.decode(GameState.class.getProtectionDomain().getCodeSource().getLocation().getPath(), "UTF-8");
            File file = new File(path + "/" + FILENAME);
            if (file.isDirectory() || !file.exists()) return new GameState();

            FileInputStream stream = new FileInputStream(file);
            ObjectInputStream objectStream = new ObjectInputStream(stream);

            GameState state = (GameState) objectStream.readObject();
            objectStream.close();

            return state;
        } catch (IOException | ClassNotFoundException e) {
            return new GameState();
        }
    }

    private void save() {
        try {
            String path = URLDecoder.decode(GameState.class.getProtectionDomain().getCodeSource().getLocation().getPath(), "UTF-8");
            File file = new File(path + "/" + FILENAME);
            file.createNewFile();

            FileOutputStream stream = new FileOutputStream(file, false);
            ObjectOutputStream objectStream = new ObjectOutputStream(stream);

            objectStream.writeObject(this);
            objectStream.flush();
            objectStream.close();
        } catch (IOException e) {
            System.err.println("Konnte den Spielfortschritt nicht speichern!");
        }
    }
}
