package inf112.skeleton.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class Player {

    private String name;
    private int x;
    private int y;
    private Direction direction;


    public Player(String name, int x, int y){
        this.name = name;
        this.x = x;
        this.y = y;
        direction = Direction.SOUTH;

    }

    /**
     * Initialize a new player (a new robot).
     *
     * @param x x start coordinate for the player.
     * @param y y start coordinate for the player.
     * @param direction which direction the player should face.
     */
    public Player(String name, int x, int y, Direction direction){
        this.name = name;
        this.x = x;
        this.y = y;
        this.direction = direction;

    }

    /**
     * Changes the direction the player is facing.
     * Uses enums Direction.NORTH, Direction.WEST, Direction.SOUTH and Direction.EAST
     *
     * @param direction new direction to face.
     */
    public void changeDirection(Direction direction){
        this.direction = direction;

        //TODO: update sprite to show the new direction
    }

    /**
     * Moves the robot forward in the direction it is facing.
     * @param steps how many tiles to move.
     */
    public void move(int steps){
        if(direction.equals(Direction.NORTH)) {
            y -= 150 * steps;
        }
        if(direction.equals(Direction.EAST)) {
            x += 150 * steps;
        }
        if(direction.equals(Direction.SOUTH)) {
            y += 150 * steps;
        }
        if(direction.equals(Direction.WEST)) {
            x -= 150 * steps;
        }
    }

    /*
     * Just for testing movement.
     */
    public void move(){
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            y -= 150;
        }else if(Gdx.input.isKeyPressed(Input.Keys.A)){
            x -= 150;
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.S)){
            y += 150;
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.D)){
            x += 150;
        }
        //Assets.load(); //makes it laggy when trying to move
    }

    public String getName() {
        return name;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
