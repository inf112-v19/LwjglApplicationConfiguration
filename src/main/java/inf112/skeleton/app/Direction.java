package inf112.skeleton.app;

public enum Direction {

    NORTH,
    EAST,
    SOUTH,
    WEST;



    public static Direction charToDir(char c){
        switch (c){
            case 'N':
                return NORTH;
            case 'S':
                return SOUTH;
            case 'W':
                return WEST;
            case 'E':
                return EAST;

        }
        return null;
    }
}
