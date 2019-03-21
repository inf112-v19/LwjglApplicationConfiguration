package inf112.roborally.game.objects;


import com.badlogic.gdx.math.Vector3;
import inf112.roborally.game.enums.Direction;

/**
 * Position is an x and a y value
 */
public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void moveInDirection(Direction dir){
        switch (dir){
            case NORTH:
                setY(getY() +1); break;
            case SOUTH:
                setY(getY() - 1); break;
            case EAST:
                setX(getX() + 1); break;
            case WEST:
                setX(getX() - 1); break;
        }
    }

    public void move(int x, int y){
        setX(x);
        setY(y);
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setPosition(Position position){
        x = position.getX();
        y = position.getY();
    }

    @Override
    public boolean equals(Object other){
        Position that = (Position) other;
        if (this.x == that.x && this.y == that.y)
            return true;

        return false;
    }
}
