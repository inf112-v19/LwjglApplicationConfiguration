package inf112.roborally.game.enums;

/**
 * Keeps track of the direction an object is facing.
 * {@link #rotationDegree} keeps track of sprite rotation according to current direction.
 */
public enum Direction {

    NORTH(0, 180),
    EAST(1, 90),
    SOUTH(2, 0),
    WEST(3, 270);

    static {
        NORTH.opposite = SOUTH;
        SOUTH.opposite = NORTH;
        EAST.opposite = WEST;
        WEST.opposite = EAST;
    }

    private int value;
    private int rotationDegree;
    private Direction opposite;

    Direction(int v, int rotationDegree) {
        this.value = v;
        this.rotationDegree = rotationDegree;
    }

    public int toInt() {
        return value;
    }

    public int getRotationDegree() {
        return rotationDegree;
    }

    public Direction getOppositeDirection() {
        return opposite;
    }

    public Direction rotate(Rotate rotate) {
        switch (rotate) {
            case RIGHT:
                return degreeToDirection(this.rotationDegree - 90);
            case LEFT:
                return degreeToDirection(this.rotationDegree + 90);
            case UTURN:
                return getOppositeDirection();
            default:
                return this;
        }
    }

    public Direction degreeToDirection(int degree) {
        while (degree <= 0)
            degree += 360;
        degree = degree % 360;

        if (degree == 0)
            return Direction.SOUTH;
        else if (degree == 270)
            return Direction.WEST;
        else if (degree == 180)
            return Direction.NORTH;
        else if (degree == 90)
            return Direction.EAST;
        else
            return this;
    }

}
