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
    public MovableGameObject(float x, float y, String filePath) {
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
                moveY(getY() + Main.TILE_LENGTH); break;
            case SOUTH:
                moveY(getY() - Main.TILE_LENGTH); break;
            case EAST:
                moveX(getX() + Main.TILE_LENGTH); break;
            case WEST:
                moveX(getX() - Main.TILE_LENGTH); break;
        }
    }

    public void move(int steps) {
        switch (direction){
            case NORTH:
                moveY(getY() + Main.TILE_LENGTH * steps); break;
            case SOUTH:
                moveY(getY() - Main.TILE_LENGTH * steps); break;
            case EAST:
                moveX(getX() + Main.TILE_LENGTH * steps); break;
            case WEST:
                moveX(getX() - Main.TILE_LENGTH * steps); break;

        }
    }

    /**
     * Rotates the object - visually too.
     *
     * @param rotateDir which direction the player should rotate.
     * @return the new direction the player is facing.
     */
    public Direction rotate(Rotate rotateDir) {
        final Direction NORTH = Direction.NORTH;
        final Direction WEST = Direction.WEST;
        final Direction SOUTH = Direction.SOUTH;
        final Direction EAST = Direction.EAST;


        if (rotateDir.equals(Rotate.RIGHT)) {
            switch (this.direction) {
                case NORTH: this.direction = EAST; break;
                case EAST: this.direction = SOUTH; break;
                case SOUTH: this.direction = WEST; break;
                case WEST: this.direction = NORTH; break;
            }
        }
        else if (rotateDir.equals(Rotate.LEFT)) {
            switch (this.direction) {
                case NORTH: this.direction = WEST; break;
                case WEST: this.direction = SOUTH; break;
                case SOUTH: this.direction = EAST; break;
                case EAST: this.direction = NORTH; break;
            }
        }
        else if (rotateDir.equals(Rotate.UTURN)) {
            switch (this.direction) {
                case NORTH: this.direction = SOUTH; break;
                case SOUTH: this.direction = NORTH; break;
                case WEST:  this.direction = EAST;  break;
                case EAST:  this.direction = WEST;  break;
            }
        }
        rotationDegree = direction.getRotationDegree();
        return this.direction;
    }

    public void setDirection(Direction direction){
        this.direction = direction;
        rotationDegree = direction.getRotationDegree();
    }

    public Direction getDirection(){
        return this.direction;
    }

}
