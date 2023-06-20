package sound;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.io.InputStream;

public class Sound {
    private InputStream stream;
    private Clip clip;

    public Sound(InputStream stream) throws IOException {
        this.stream = stream;
    }

    public AudioInputStream getInputStream() {
        try {
            return AudioSystem.getAudioInputStream(stream);
        } catch (UnsupportedAudioFileException | IOException e) {
            System.err.println("Konnte Audiodatei nicht laden!");
            throw new RuntimeException(e);
        }
    }

    public void setClip(Clip clip) {
        this.clip = clip;
    }

    public Clip getClip() {
        return clip;
    }
}
