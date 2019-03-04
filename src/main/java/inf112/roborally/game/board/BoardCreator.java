package inf112.roborally.game.board;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import inf112.roborally.game.Main;
import inf112.roborally.game.RoboRallyGame;

public abstract class BoardCreator {

    protected TiledMap map;
    protected TmxMapLoader loader;
    protected TiledMapRenderer mapRenderer;

    protected TiledMapTileLayer floorLayer;
    protected TiledMapTileLayer beltLayer;
    protected TiledMapTileLayer laserLayer;
    protected TiledMapTileLayer wallLayer;

    protected void createdBoard(String mapPath) {
        loader = new TmxMapLoader();
        TmxMapLoader.Parameters parameters = new TmxMapLoader.Parameters();
        parameters.flipY = true;
        map = loader.load(RoboRallyGame.VAULT, parameters);
        mapRenderer = new OrthogonalTiledMapRenderer(map, Main.UNIT_SCALE);
        createLayers();
    }

    protected void createLayers() {
        beltLayer = (TiledMapTileLayer) map.getLayers().get("belts");
        floorLayer = (TiledMapTileLayer) map.getLayers().get("floor");
        laserLayer = (TiledMapTileLayer) map.getLayers().get("lasers");
        wallLayer = (TiledMapTileLayer) map.getLayers().get("walls");
    }

}
