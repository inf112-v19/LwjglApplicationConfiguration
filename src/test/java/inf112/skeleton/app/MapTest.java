package inf112.skeleton.app;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.fail;

public class MapTest {
    public  static final String MAP_PATH = "testMap.tmx";

    public TiledMap map;
    public TiledMapTileLayer layer;


    @Before
    public void setup(){
        TmxMapLoader loader = new TmxMapLoader();
        map = loader.load("testMap.tmx");
        layer = (TiledMapTileLayer) map.getLayers().get("floor");
    }

    @Test
    public void sillyMapTest(){
        System.out.println(map.getTileSets());
        fail();
    }
}
