package inf112.roborally.game.objects;

import inf112.roborally.game.enums.Direction;

public class LaserShot extends MovableGameObject{

    public LaserShot(Direction dir, int x , int y){
        super(x, y, "");
        setDirection(dir);
    }
}
