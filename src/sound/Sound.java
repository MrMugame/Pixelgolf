package sound;

import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;

public class Sound {
    private AudioInputStream inputStream;
    private Clip clip;

    public Sound(InputStream file) throws UnsupportedAudioFileException, IOException {
        inputStream = AudioSystem.getAudioInputStream(file);
    }

    public AudioInputStream getInputStream() {
        return inputStream;
    }

    public void setClip(Clip clip) {
        this.clip = clip;
    }

    public Clip getClip() {
        return clip;
    }
}
