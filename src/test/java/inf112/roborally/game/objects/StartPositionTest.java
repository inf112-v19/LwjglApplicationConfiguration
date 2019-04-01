package inf112.roborally.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import inf112.roborally.game.RoboRallyGame;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class StartPositionTest {

    private TiledMap map;
    private TmxMapLoader loader;
    private OrthogonalTiledMapRenderer mapRenderer;

    private TiledMapTileLayer startLayer;

    @Before
    public void setup() {
        loader = new TmxMapLoader();
        TmxMapLoader.Parameters parameters = new TmxMapLoader.Parameters();
        map = loader.load(RoboRallyGame.VAULT, parameters);
        startLayer = (TiledMapTileLayer) map.getLayers().get("start");

    }

    @Test
    public void printStartPositions() {
        assertTrue(true);
    }

    @After
    public void dispose() {
        map.dispose();
    }


}
