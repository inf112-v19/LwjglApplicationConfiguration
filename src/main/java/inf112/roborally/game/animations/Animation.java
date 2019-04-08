package inf112.roborally.game.animations;

import inf112.roborally.game.objects.GameObject;
import inf112.roborally.game.objects.Position;

public abstract class Animation extends GameObject {
    protected int lifetime;
    protected int stateTimer;

    public Animation(Position position) {
        super(position);
        updateSprite();

    }

    protected abstract void update();

    public boolean hasFinished() {
        return lifetime < stateTimer;
    }
}
