package inf112.roborally.game.animations;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.roborally.game.tools.AssMan;
import inf112.roborally.game.objects.Position;


public class RepairAnimation extends Animation {
    float rotation;
    float direction;

    public RepairAnimation(Position position) {
        super(position);
        sprite = new Sprite(AssMan.manager.get(AssMan.REPAIRSITE_REPAIR_ANIMATION));
        sprite.setSize(32, 32);
        sprite.setOriginCenter();

        stateTimer = 0;
        rotation = 45;
        direction = -1;
        lifetime = 60;
    }

    public void draw(SpriteBatch batch) {
        super.draw(batch);
        update();
    }

    @Override
    protected void update() {
        updateSprite();

        if (rotation < -45) {
            direction = 1;
        } else if (rotation > 45) {
            direction = -1;
        }

        rotation = rotation + 5 * direction;
        sprite.setRotation(rotation);

        direction += 0.052f;
        stateTimer++;
    }

    @Override
    public void updateSprite() {
        super.updateSprite();
        sprite.setPosition(sprite.getX() - 3, sprite.getY() + 7);
    }
}
