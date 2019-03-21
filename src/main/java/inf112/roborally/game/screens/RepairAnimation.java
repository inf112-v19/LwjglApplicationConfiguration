package inf112.roborally.game.screens;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class RepairAnimation extends Animation {
    int stateTimer;

    public RepairAnimation(int x, int y) {
        super(x, y, "assets/animations/wrench.png");

        sprite.setSize(100, 100);
        sprite.setOriginCenter();

        stateTimer = 0;
    }

    public void draw(SpriteBatch batch) {
        update();
        sprite.draw(batch);
    }

    @Override
    protected void update() {
        updateSprite();
        sprite.setRotation(90 + 5 * stateTimer++);
    }
}
