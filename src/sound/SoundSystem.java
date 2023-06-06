package sound;

import javax.sound.sampled.*;
import java.io.IOException;

public class SoundSystem {
    private static SoundSystem instance;

    private float volumeSFX = 1.0f, volumeMusic = 1.0f;

    private Playlist playlist;

    private SoundSystem() {}

    public static SoundSystem get() {
        if (instance == null) {
            instance = new SoundSystem();
        }
        return instance;
    }

    public void update() {
        if (!playlist.getCurrent().getClip().isRunning()) {
            play(playlist.getNext());
        }
    }

    public void play(Sound sound) {
        play(sound, volumeSFX);
    }

    private void play(Sound sound, float volume) {
        // TODO: See if I need to close the clips because of memory
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(sound.getInputStream());

            FloatControl gain = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gain.setValue(Math.max(gain.getMinimum(), Math.min(gain.getMaximum(), percentVolumeToDB(volume))));

            clip.start();

            sound.setClip(clip);
        } catch (LineUnavailableException | IOException e) {
            e.printStackTrace();
            System.err.println("Konnte Sound nicht laden!");
        }
    }

    public void play(Playlist playlist) {
        if (this.playlist != null) playlist.getCurrent().getClip().stop();

        this.playlist = playlist;
        play(this.playlist.getNext(), volumeMusic);
    }

    private static float percentVolumeToDB(float volume) {
        // https://docs.oracle.com/javase/8/docs/api/javax/sound/sampled/FloatControl.Type.html#MASTER_GAIN
        // linearScalar = pow(10.0, gainDB/20.0)
        // log(linearScalar) = gainDB/20.0 * log(10.0)
        // log(linearScalar) / log(10.0) * 20.0 = gainDB
        return (float) (Math.log(volume != 0 ? volume : 1e-7) / Math.log(10.0) * 20.0);
    }

    public float getSFXVolume() {
        return volumeSFX;
    }

    public float getMusicVolume() {
        return volumeMusic;
    }

    public void setSFXVolume(float volume) {
        volumeSFX = volume;
    }

    public void setMusicVolume(float volume) {
        volumeMusic = volume;
        if (playlist != null) {
            FloatControl gain = (FloatControl) playlist.getCurrent().getClip().getControl(FloatControl.Type.MASTER_GAIN);
            gain.setValue(Math.max(gain.getMinimum(), Math.min(gain.getMaximum(), percentVolumeToDB(volume))));
        }
    }
}
