package inf112.roborally.game.world;


import inf112.roborally.game.objects.Rotate;
import org.omg.PortableInterceptor.DISCARDING;

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

    public Direction getOppositeDirection() {
        return opposite;
    }

    private int rotationDegree;
    static {
        SOUTH.rotationDegree = 0;
        EAST.rotationDegree = 90;
        NORTH.rotationDegree = 180;
        WEST.rotationDegree = 270;
    }

    public int getRotationDegree(){
        return rotationDegree;
    }

    public Direction rotate(Rotate rotate){
        switch (rotate){
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

    public Direction degreeToDirection(int degree){
        while (degree <= 0)
            degree += 360;
        degree = degree % 360;

        if(degree == 0)
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
