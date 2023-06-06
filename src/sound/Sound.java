package sound;

import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;

public class Sound {
    private AudioInputStream inputStream;

    public Sound(InputStream file) throws UnsupportedAudioFileException, IOException {
        inputStream = AudioSystem.getAudioInputStream(file);
    }

    public void play() {
        // TODO: See if I need to close the clips because of memory
        try {
            Clip clip = SoundSystem.get().getSFXClip();
            clip.open(inputStream);

            FloatControl gain = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gain.setValue(Math.max(gain.getMinimum(), Math.min(gain.getMaximum(), SoundSystem.percentVolumeToDB(SoundSystem.get().getSFXVolume()))));

            clip.start();
        } catch (LineUnavailableException | IOException e) {
            System.err.println("Konnte Sound nicht laden!");
        }
    }
}
