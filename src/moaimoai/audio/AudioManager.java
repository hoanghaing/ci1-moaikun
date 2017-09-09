package moaimoai.audio;

import javafx.scene.media.MediaPlayer;

/**
 * Created by NguyenGiaThe on 9/9/2017.
 */
public class AudioManager {
    public static MediaPlayer backgroundMusic;

    public static void register(MediaPlayer mediaPlayer){
        backgroundMusic = mediaPlayer;
        backgroundMusic.play();
    }

    public static void unregister(){
        if (backgroundMusic != null){
            backgroundMusic.stop();
            backgroundMusic = null;
        }
    }
}
