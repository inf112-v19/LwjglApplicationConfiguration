package inf112.roborally.game.board;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import inf112.roborally.game.RoboRallyGame;

public abstract class TiledBoard {

    protected TiledMap map;
    protected TmxMapLoader loader;
    protected OrthogonalTiledMapRenderer mapRenderer;

    protected TiledMapTileLayer floorLayer;
    protected TiledMapTileLayer beltLayer;
    protected TiledMapTileLayer laserLayer;
    protected TiledMapTileLayer wallLayer;
    protected TiledMapTileLayer startLayer;

    public void createBoard(String mapPath) {
        loader = new TmxMapLoader();
        TmxMapLoader.Parameters parameters = new TmxMapLoader.Parameters();
        parameters.flipY = true;
        map = loader.load(mapPath, parameters);
        mapRenderer = new OrthogonalTiledMapRenderer(map, ((RoboRallyGame) Gdx.app.getApplicationListener()).batch);
        createLayers();
    }

    public void render(OrthographicCamera camera) {
        mapRenderer.setView(camera);
        mapRenderer.render();
    }

    /**
     * Use this when rendering a specific layer(like walls) on top of sprites.
     * MUST CALL BATCH BEGIN BEFORE THIS!!
     *
     * @param layer
     */
    public void renderLayer(TiledMapTileLayer layer) {
        mapRenderer.renderTileLayer(layer);
    }

    protected void createLayers() {
        beltLayer = (TiledMapTileLayer) map.getLayers().get("belts");
        floorLayer = (TiledMapTileLayer) map.getLayers().get("floor");
        laserLayer = (TiledMapTileLayer) map.getLayers().get("lasers");
        wallLayer = (TiledMapTileLayer) map.getLayers().get("walls");
        startLayer = (TiledMapTileLayer) map.getLayers().get("start");
    }

    public void dispose() {
        System.out.println("disposing board");
        map.dispose();

    }

    public TiledMapTileLayer getLaserLayer() {
        return laserLayer;
    }

    public TiledMapTileLayer getStartLayer() {
        return startLayer;
    }

    public TiledMapTileLayer getBeltLayer() {
        return beltLayer;
    }

    public TiledMapTileLayer getWallLayer() {
        return this.wallLayer;
    }

    public TiledMapTileLayer getFloorLayer() {
        return floorLayer;
    }

    public int getWidth() {
        return this.floorLayer.getWidth();
    }

    public int getHeight() {
        return this.floorLayer.getHeight();
    }

}
