package inf112.roborally.game.world;


public enum Direction {

    NORTH,
    EAST,
    SOUTH,
    WEST;

    private Direction opposite;
    static {
        NORTH.opposite = SOUTH;
        SOUTH.opposite = NORTH;
        EAST.opposite = WEST;
        WEST.opposite = EAST;
    }

    private int rotationDegree;
    static {
        SOUTH.rotationDegree = 0;
        EAST.rotationDegree = 90;
        NORTH.rotationDegree = 180;
        WEST.rotationDegree = 270;
    }

    public Direction getOppositeDirection() {
        return opposite;
    }

    public int getRotationDegree(){
        return rotationDegree;
    }
}
