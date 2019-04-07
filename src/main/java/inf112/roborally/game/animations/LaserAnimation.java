package inf112.roborally.game.animations;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.Array;
import inf112.roborally.game.Main;
import inf112.roborally.game.enums.Direction;
import inf112.roborally.game.objects.MovableGameObject;
import inf112.roborally.game.tools.AssMan;


public class LaserAnimation extends MovableGameObject {
    private static final int FRAME_DURATION = 6;

    private Array<TextureRegion> regions;
    private com.badlogic.gdx.graphics.g2d.Animation<TextureRegion> animation;
    private int stateTimer;

    public LaserAnimation(int x, int y, Direction direction) {
        super(x, y, "assets/objects/animatedlaser.atlas");
        setDirection(direction);
        setUpAnimation();
    }

    private void setUpAnimation() {
        sprite = new Sprite(AssMan.manager.get(AssMan.LASER_ATLAS).findRegion("laser"));
        sprite.setBounds(getX(), getY(), Main.PIXELS_PER_TILE, Main.PIXELS_PER_TILE);
        sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);

        stateTimer = 0;
        regions = new Array<>();
        for (int i = 0; i < 3; i++)
            regions.add(new TextureRegion(sprite.getTexture(), 0, 32 * i, 32, 32));
        animation = new Animation<>(FRAME_DURATION, regions);
    }

    @Override
    public void updateSprite() {
        super.updateSprite();
        sprite.setRegion(animation.getKeyFrame(stateTimer++, true));
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
        updateSprite();
    }

}
