package inf112.roborally.game.board;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import inf112.roborally.game.RoboRallyGame;

public abstract class TiledBoard {

    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;

    TiledMapTileLayer floorLayer;
    TiledMapTileLayer beltLayer;
    TiledMapTileLayer laserLayer;
    TiledMapTileLayer wallLayer;
    TiledMapTileLayer startLayer;

    public void createBoard(String mapPath) {
        TmxMapLoader.Parameters parameters = new TmxMapLoader.Parameters();
        parameters.flipY = true;
        map = new TmxMapLoader().load(mapPath, parameters);
        beltLayer = (TiledMapTileLayer) map.getLayers().get("belts");
        floorLayer = (TiledMapTileLayer) map.getLayers().get("floor");
        laserLayer = (TiledMapTileLayer) map.getLayers().get("lasers");
        wallLayer = (TiledMapTileLayer) map.getLayers().get("walls");
        startLayer = (TiledMapTileLayer) map.getLayers().get("start");

        mapRenderer = new OrthogonalTiledMapRenderer(map, ((RoboRallyGame) Gdx.app.getApplicationListener()).batch);
    }

    public void render(OrthographicCamera camera) {
        mapRenderer.setView(camera);
        mapRenderer.render();
    }



    void drawWalls(Batch batch){
        for (int x = 0; x < floorLayer.getWidth(); x++) {
            for (int y = 0; y < floorLayer.getHeight(); y++) {
                TiledMapTileLayer.Cell cell = wallLayer.getCell(x, y);
                if (cell != null) {
                    batch.draw(cell.getTile().getTextureRegion(), x * 32, y * 32);
                }
            }
        }
    }
    void drawTallerWalls(Batch batch){
        for (int x = 0; x < floorLayer.getWidth(); x++) {
            for (int y = 0; y < floorLayer.getHeight(); y++) {
                TiledMapTileLayer.Cell cell = wallLayer.getCell(x, y);
                if (cell != null) {
                    batch.draw(cell.getTile().getTextureRegion(), x * 32, y * 32 + 4);
                }
            }
        }
    }

    public void dispose() {
        System.out.println("Disposing TiledBoard");
        map.dispose();
        mapRenderer.dispose();
    }

    public TiledMapTileLayer getLaserLayer() {
        return laserLayer;
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
