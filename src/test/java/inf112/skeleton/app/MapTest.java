package inf112.skeleton.app;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.fail;

public class MapTest {
    public  static final String MAP_PATH = "testMap.tmx";

    private OrthographicCamera camera;
    private Viewport port;
    private TmxMapLoader loader;
    private TiledMap board;
    private TiledMapRenderer renderer;
    private TiledMapTileLayer layer;

    @Before
    public void setup( ) {
        camera = new OrthographicCamera(0, 0);
//        port = new FitViewport(800, 480, camera);
        loader = new TmxMapLoader();
        board = loader.load("assets/testMap.tmx");
        renderer = new OrthogonalTiledMapRenderer(board);
        layer = (TiledMapTileLayer) board.getLayers().get("floor");
    }

    @Test
    public void sillyMapTest(){
        assert(layer.getCell(0,0).getTile().getProperties().containsKey("Hole"));
    }
}
