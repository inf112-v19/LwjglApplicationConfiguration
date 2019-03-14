package inf112.roborally.game.objects;

import inf112.roborally.game.enums.Direction;

public class LaserShot {
    private int damage = 1;
    private Direction dir;
    private  int x;
    private int y;

    public LaserShot(Direction dir, int x , int y){
        this.dir = dir;
        this.x = x;
        this.y = y;
    }

}
