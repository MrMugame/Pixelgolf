package sound;

import javax.sound.sampled.*;

public class SoundSystem {
    private static SoundSystem instance;

    private float volumeSFX = 1.0f, volumeMusic = 1.0f;


    private SoundSystem() {}

    public static SoundSystem get() {
        if (instance == null) {
            instance = new SoundSystem();
        }
        return instance;
    }

    public Clip getSFXClip() {
        try {
            return AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            System.err.println("Konnte keinen Audiokanal finden!");
            return null;
        }
    }

    public static float percentVolumeToDB(float volume) {
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
    }
}
