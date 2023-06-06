package sound;

import assets.Assets;

import java.util.ArrayList;

public class Playlist {
    private ArrayList<Sound> songs = new ArrayList<>();
    private int index = 0;

    public Playlist(String... songs) {
        for (int i = 0; i < songs.length; i++) {
            this.songs.add(Assets.loadSound(songs[index]));
        }
    }

    public Sound getNext() {
        Sound sound = songs.get(index);
        index++;
        if (index == songs.size()) index = 0;
        return sound;
    }

    public Sound getCurrent() {
        return songs.get(index);
    }
}
