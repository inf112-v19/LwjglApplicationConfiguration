package inf112.skeleton.app.GameObjects;

import inf112.skeleton.app.GameWorld.Direction;
import inf112.skeleton.app.Main;
import inf112.skeleton.app.Rotate;

public abstract class MoveableGameObject extends GameObject {
    public boolean moved;
    protected int rotationDegree;
    private Direction direction;

    public MoveableGameObject(float x, float y) {
        super(x, y);
        direction = Direction.SOUTH;
        rotationDegree = 180;
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
     * Rotates the player - visually too.
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
            this.rotationDegree += 90;
        }
        else if (rotateDir.equals(Rotate.LEFT)) {
            switch (this.direction) {
                case NORTH: this.direction = WEST; break;
                case WEST: this.direction = SOUTH; break;
                case SOUTH: this.direction = EAST; break;
                case EAST: this.direction = NORTH; break;
            }
            this.rotationDegree -= 90;
        }
        else if (rotateDir.equals(Rotate.UTURN)) {
            switch (this.direction) {
                case NORTH: this.direction = SOUTH; break;
                case SOUTH: this.direction = NORTH; break;
                case WEST:  this.direction = EAST;  break;
                case EAST:  this.direction = WEST;  break;
            }
            this.rotationDegree += 180;
        }
        return this.direction;
    }

    public void setDirection(Direction direction){
        this.direction = direction;
    }

    public Direction getDirection(){
        return this.direction;
    }

}
