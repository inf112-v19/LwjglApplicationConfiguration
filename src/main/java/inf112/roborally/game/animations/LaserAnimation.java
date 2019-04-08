package inf112.roborally.game.animations;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import inf112.roborally.game.tools.AssMan;

public class LaserAnimation {
    private static final int FRAME_DURATION = 6;
    private final Animation<TextureRegion> animation;
    private int stateTimer;
    private Sound sound;
    protected Sprite sprite;

    public LaserAnimation() {
        stateTimer = 0;
        sound = AssMan.manager.get(AssMan.SOUND_PLAYER_LASER);
        TextureAtlas.AtlasRegion region = AssMan.manager.get(AssMan.LASER_ATLAS).findRegion("laser");
        Array<TextureRegion> regions = new Array<>();
        for (int i = 0; i < 3; i++)
            regions.add(new TextureRegion(region.getTexture(), 0, 32 * i, 32, 32));
        animation = new Animation<>(FRAME_DURATION, regions);
    }

    public TextureRegion getRegion() {
        return animation.getKeyFrame(stateTimer++, true);
    }

    public void playSound() {
        sound.play();
    }

    public void dispose(){
        sprite.getTexture().dispose();
        sound.dispose();
    }
}
