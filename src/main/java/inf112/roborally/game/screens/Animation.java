package inf112.roborally.game.screens;

import inf112.roborally.game.objects.GameObject;

public abstract class Animation extends GameObject {
    protected int lifetime;
    protected int stateTimer;

    public Animation(int x, int y, String filePath) {
        super(x, y, filePath);
        makeSprite();
        updateSprite();

    }

    protected abstract void update();

    public boolean hasFinished(){
        return lifetime < stateTimer;
    }
}
