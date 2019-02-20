package inf112.roborally.game.objects;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

/**
 * Class for a flag on the board, which extends AbstractGameObject
 */
public class Flag {//extends AbstractGameObject {
//    private int flagNumber;
    private String filepath = "assets/gameboard/flag.png";

//    private TiledMapTileLayer flagLayer;
    private TiledMapTileLayer.Cell flagCell;

//    public Flag(int x, int y, int flagNumber) {
//        super(x, y);
//        this.flagNumber = flagNumber;
//    }

    public Flag (TiledMapTileLayer.Cell cell) {
        this.flagCell = cell;
    }

    public String getFilename() {
        return filepath;
    }

}
