package inf112.roborally.game.sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class GameMusic {
    private Music music;
    private boolean musicIsMuted;
    private String filepath; // save the filepath for this instance of music


    public GameMusic(String filepath) {
        music = Gdx.audio.newMusic(Gdx.files.internal(filepath));
        filepath = filepath;
        music.setLooping(true);
        music.setVolume(0.3f);
        musicIsMuted = false;
    }

    public void play() {
        music.play();
    }

    public void mute() {
        music.stop();
        musicIsMuted = true;
    }

    public void dispose() {
        music.dispose();
    }
}
