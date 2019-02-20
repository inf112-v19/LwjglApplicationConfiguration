package inf112.roborally.game.objects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import inf112.roborally.game.Main;


public class Laser extends MovableGameObject{
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

    @Override
    public void updateSprite(){
        sprite.setRegion(animation.getKeyFrame(stateTimer++, true));
        sprite.setRotation(rotationDegree);
    }

    @Override
    public Sprite getSprite() {
        return sprite;
    }
}
