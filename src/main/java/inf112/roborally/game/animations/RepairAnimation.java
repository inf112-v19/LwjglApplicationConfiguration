package inf112.roborally.game.animations;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import inf112.roborally.game.objects.Position;

import java.util.List;

public class RepairAnimation extends Animation {
    List<Animation> animations;
    int rotation;
    int direction;

    public RepairAnimation(Position position, List<Animation> animations) {
        super(position, "assets/animations/wrench.png");
        this.animations = animations;
        sprite.setSize(100, 100);
        sprite.setOriginCenter();

        stateTimer = 0;
        rotation = 45;
        direction = -1;
        lifetime = 60;
    }

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
        update();
    }

    @Override
    protected void update() {
        updateSprite();

        if (rotation < -45) {
            direction = 1;
        }
        else if (rotation > 45) {
            direction = -1;
        }

        rotation = rotation + 5 * direction;
        sprite.setRotation(rotation);

        stateTimer++;
    }
}
