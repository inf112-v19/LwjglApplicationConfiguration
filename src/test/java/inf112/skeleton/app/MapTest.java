package inf112.skeleton.app;


import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import org.junit.Before;
import org.junit.Test;

public class MapTest {
    public  static final String MAP_PATH = "testMap.tmx";
        TiledMapTileLayer floorLayer;
        TiledMapTileLayer laserLayer;

        RoboRallyGame game;

    @Before
    public void setup( ) {
        game = new RoboRallyGame();
//        game.gameScreen = new GameScreen(game);
//        laserLayer = (TiledMapTileLayer) game.gameScreen.board.getLayers().get("lasers");
//        floorLayer = (TiledMapTileLayer) game.gameScreen.board.getLayers().get("floor");

    }

    @Test
    public void sillyMapTest(){
        assert(floorLayer.getCell(0,0).getTile().getProperties().containsKey("Hole"));
    }
}
