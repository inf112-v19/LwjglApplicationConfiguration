package inf112.roborally.game.board;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * This class contains methods used to get information from TiledMaps
 */
public class TiledTools {

    /** gets the first value of a specified cell
     * @param cell
     * @return
     */
    public static String getValue(TiledMapTileLayer.Cell cell) {
        return cell.getTile().getProperties().getValues().next().toString();
    }

    /**
     * Checks if a cell contains a key
     * @param cell
     * @param target
     * @return true if cell contains target, false if not
     */
    public static boolean cellContainsKey(TiledMapTileLayer.Cell cell, String target) {
        return cell != null && cell.getTile().getProperties().containsKey(target);
    }

    /**
     * gets the first value from a cell and split the resulting string by spaces into a list
     * @param cell
     * @return
     */
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
