package inf112.roborally.game.sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import inf112.roborally.game.tools.AssMan;

public class GameSound implements IGameMusic{
    private Sound sound;
    private String filepath; // Save the filepath if we want to use it later

    public GameSound(String filepath) {
        sound = Gdx.audio.newSound(Gdx.files.internal(filepath));
        this.filepath = filepath;
    }

    public GameSound(AssetDescriptor<Sound> file) {
        sound = AssMan.manager.get(file);
    }

    @Override
    public void play() {
        sound.play(0.3f);
    }

    @Override
    public void mute() {
        // For now, dispose the music, so it never plays again,
        sound.dispose();
    }
    public Sound getSound() { return this.sound; }

    @Override
    public void dispose() {
        sound.dispose();
    }
}
