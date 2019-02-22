package inf112.roborally.game.objects;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import inf112.roborally.game.Main;

/**
 * Class for a flag on the board, which extends AbstractGameObject
 */
public class Flag extends GameObject {
    private String filePath = "assets/gameboard/flag.png";

    private TiledMapTileLayer.Cell flagCell;

    public Flag (int x, int y) {
        super(x, y, "assets/gameboard/flag.png");
        makeSprite();
        updateSprite();
        sprite.setSize(Main.TILE_LENGTH, Main.TILE_LENGTH);
    }

    public String getFilename() {
        return filePath;
    }

}
