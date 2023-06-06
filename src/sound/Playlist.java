package sound;

import assets.Assets;

import java.util.ArrayList;
import java.util.Arrays;

public class Playlist {
    private ArrayList<String> songs = new ArrayList<>();
    private Sound current;
    private int index = 0;

    public Playlist(String... songs) {
       this.songs.addAll(Arrays.asList(songs));
    }

    public Sound getNext() {
        current = Assets.loadSound(songs.get(index));
        index++;
        if (index == songs.size()) index = 0;
        return current;
    }

    public Sound getCurrent() {
        return current;
    }
}
