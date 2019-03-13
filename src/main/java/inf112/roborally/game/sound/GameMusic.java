package inf112.roborally.game.sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class GameMusic implements IGameMusic {
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

    @Override
    public void play() {
        music.play();
    }

    @Override
    public void mute() {
        music.stop();
        musicIsMuted = true;
    }

    @Override
    public void dispose() {
        music.dispose();
    }

    public Music getMusic() {
        return this.music;
    }
}
