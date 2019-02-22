package inf112.roborally.game.objects;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import inf112.roborally.game.Main;

/**
 * Class for a flag on the board, which extends AbstractGameObject
 * Three filepaths for the picture files for the three different flags
 */
public class Flag extends GameObject {
    private String filePath1 = "assets/gameboard/flag1.png";
    private String filePath2 = "assets/gameboard/flag2.png";
    private String filePath3 = "assets/gameboard/flag3.png";

    private TiledMapTileLayer.Cell flagCell;

    public Flag (int x, int y, int identifier) {
        super(x, y, "");

        // First set the filepath to an empty string, then we have to update the filepath
        // to correspond to the right image
        String currFilepath = "";
        switch (identifier) {
            case 1:
                currFilepath = filePath1;
                break;
            case 2:
                currFilepath = filePath2;
                break;
            case 3:
                currFilepath = filePath3;
                break;
        }

        this.setFilePath(currFilepath);

        makeSprite();
        updateSprite();
        sprite.setSize(Main.TILE_LENGTH, Main.TILE_LENGTH);
    }

    public String getFilename() {
        return filePath;
    }

}
