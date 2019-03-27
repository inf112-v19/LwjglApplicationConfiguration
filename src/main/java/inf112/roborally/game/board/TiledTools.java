package inf112.roborally.game.board;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * This class contains methods used to get information from TiledMaps
 */
public class TiledTools {

    public static String getValue(TiledMapTileLayer.Cell cell) {
        return cell.getTile().getProperties().getValues().next().toString();
    }

    public static boolean cellContainsKey(TiledMapTileLayer.Cell cell, String target) {
        return cell != null && cell.getTile().getProperties().containsKey(target);
    }

    public static List<String> splitValuesBySpace(TiledMapTileLayer.Cell cell) {
        String string = getValue(cell);
        List<String> splitList = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(string);
        while (st.hasMoreTokens()) {
            splitList.add(st.nextToken());
        }
        return splitList;
    }

}
