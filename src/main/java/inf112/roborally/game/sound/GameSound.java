package inf112.roborally.game.sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class GameSound {
    private Sound sound;

    public GameSound(String filepath) {
        sound = Gdx.audio.newSound(Gdx.files.internal(filepath));
    }

    public void mute() {
        sound.dispose();
    }
    public Sound getSound() { return this.sound; }

    public void dispose() {
        sound.dispose();
    }
}
