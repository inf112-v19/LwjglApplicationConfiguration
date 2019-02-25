package inf112.roborally.game.objects;

import com.badlogic.gdx.graphics.Texture;
import inf112.roborally.game.world.Direction;
import inf112.roborally.game.Main;

public abstract class MovableGameObject extends GameObject {
    public boolean moved;
    protected int rotationDegree;
    private Direction direction;

    /**
     * A GameObject that is facing a certain direction and can move in
     * said direction. It can also rotate. Facing south by default.
     * @param x position x
     * @param y position y
     */
    public MovableGameObject(int x, int y, String filePath) {
        super(x, y, filePath);
        direction = Direction.SOUTH;
        rotationDegree = direction.getRotationDegree();
    }

    @Override
    public void updateSprite() {
        sprite.setRotation(rotationDegree);
        super.updateSprite();
    }

    public void loadVisualRepresentation() {
        sprite.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        sprite.setSize(Main.TILE_LENGTH, Main.TILE_LENGTH);
        sprite.setOriginCenter();

        updateSprite();
    }

    public void moveInDirection(Direction dir){
        switch (dir){
            case NORTH:
                moveY(getY() + 1); break;
            case SOUTH:
                moveY(getY() - 1); break;
            case EAST:
                moveX(getX() + 1); break;
            case WEST:
                moveX(getX() - 1); break;
        }
        moved = true;
    }

    public void move(int steps) {
        for(int i = 0 ; i < steps; i++)
            moveInDirection(getDirection());
    }

    /**
     * Rotates the object - visually too.
     *
     * @param rotateDir which direction the player should rotate.
     * @return the new direction the player is facing.
     */
    public void rotate(Rotate rotateDir) {
         setDirection(direction.rotate(rotateDir));
    }

    public void setDirection(Direction direction){
        this.direction = direction;
        rotationDegree = direction.getRotationDegree();
        moved = true;
    }

    public Direction getDirection(){
        return this.direction;
    }

}
