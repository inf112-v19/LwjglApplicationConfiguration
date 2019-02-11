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
        TiledMapTileLayer floorLayer;
        TiledMapTileLayer laserLayer;

        RoboRallyGame game;

    @Before
    public void setup( ) {
        game = new RoboRallyGame();
        game.gameScreen = new GameScreen(game);
//        laserLayer = (TiledMapTileLayer) game.gameScreen.board.getLayers().get("lasers");
//        floorLayer = (TiledMapTileLayer) game.gameScreen.board.getLayers().get("floor");

    }

    @Test
    public void sillyMapTest(){
        assert(floorLayer.getCell(0,0).getTile().getProperties().containsKey("Hole"));
    }
}
