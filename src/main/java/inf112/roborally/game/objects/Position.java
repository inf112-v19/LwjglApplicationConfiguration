package inf112.roborally.game.objects;


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

    public Position moveInDirection(Direction dir) {
        switch (dir) {
            case NORTH:
                setY(getY() + 1);
                return this;
            case SOUTH:
                setY(getY() - 1);
                return this;
            case EAST:
                setX(getX() + 1);
                return this;
            case WEST:
                setX(getX() - 1);
                return this;
        }
        return this;
    }

    public void move(int x, int y) {
        setX(x);
        setY(y);
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setPosition(Position position) {
        x = position.getX();
        y = position.getY();
    }

    public Position copy(){
        return new Position(x, y);
    }

    @Override
    public boolean equals(Object other) {
        Position that = (Position) other;
        return this.x == that.x && this.y == that.y;
    }
}
