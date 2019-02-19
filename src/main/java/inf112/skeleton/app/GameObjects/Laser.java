package inf112.skeleton.app.GameObjects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import inf112.skeleton.app.Main;


public class Laser extends GameObject{
    private static final int FRAME_DURATION = 6;

    private Sprite sprite;
    private Array<TextureRegion> regions;
    private Animation<TextureRegion> animation;
    private int stateTimer;

    public Laser(int x, int y){
        super(x, y);
        setUpAnimation();
    }

    private void setUpAnimation() {
        String filePath = "assets/gameboard/roborallypack.atlas";
        sprite = new Sprite(new TextureAtlas(filePath).findRegion("laser"));
        sprite.setBounds(getX(), getY(), Main.TILE_LENGTH, Main.TILE_LENGTH);

        stateTimer = 0;
        regions = new Array<>();
        for(int i = 0; i < 3; i++)
            regions.add(new TextureRegion(sprite.getTexture(), 0, 32*i, 32, 32));
        animation = new Animation<>(FRAME_DURATION, regions);
    }

    public void update(){
        sprite.setRegion(animation.getKeyFrame(stateTimer++, true));

    }

    @Override
    public Sprite getSprite() {
        return sprite;
    }
}
