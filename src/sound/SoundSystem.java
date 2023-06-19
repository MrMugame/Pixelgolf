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
        if (playlist != null && !playlist.getCurrent().getClip().isRunning()) {
            // Clips schließen, weil die sonst Memory leaken (Frag mich nicht, Java halt). Vermutlich bei Sounds auch nötig, aber wegen der geringen größe nicht lohnenswert
            playlist.getCurrent().getClip().close();
            play(playlist.getNext(), volumeMusic);
        }
    }

    public void play(Sound sound) {
        play(sound, volumeSFX);
    }

    private void play(Sound sound, float volume) {
        try {
            Clip clip = AudioSystem.getClip();
            AudioInputStream stream = sound.getInputStream();
            clip.open(stream);

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
        if (this.playlist != null) this.playlist.getCurrent().getClip().stop();

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

    public void close() {
        if (playlist != null) {
            playlist.getCurrent().getClip().stop();
            playlist.getCurrent().getClip().close();
        }
    }
}
