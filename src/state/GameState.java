package state;

import graphics.GameWindow;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;

public class GameState implements Serializable {
    private static GameState instance;

    private HashMap<Integer, LevelState> levels = new HashMap<>();

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

    public static GameState load() {
        try {
            String path = URLDecoder.decode(GameState.class.getProtectionDomain().getCodeSource().getLocation().getPath(), "UTF-8");
            File file = new File(path + "/savegame.txt");
            if (file.isDirectory() || !file.exists()) return new GameState();

            FileInputStream stream = new FileInputStream(file);
            try (stream) {
                ObjectInputStream objectStream = new ObjectInputStream(stream);
                GameState state = (GameState) objectStream.readObject();
                objectStream.close();
                return state;
            }
        } catch (IOException | ClassNotFoundException e) {
            return new GameState();
        }
    }

    public void save() {
        try {
            String path = URLDecoder.decode(GameState.class.getProtectionDomain().getCodeSource().getLocation().getPath(), "UTF-8");
            File file = new File(path + "/savegame.txt");
            file.createNewFile();
            FileOutputStream stream = new FileOutputStream(file, false);
            try (stream) {
                ObjectOutputStream objectStream = new ObjectOutputStream(stream);
                objectStream.writeObject(this);
                objectStream.flush();
                objectStream.close();
            }
        } catch (IOException e) {
            System.err.println("Konnte den Spielfortschritt nicht speichern!");
        }
    }
}
