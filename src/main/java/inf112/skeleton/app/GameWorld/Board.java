package inf112.skeleton.app.GameWorld;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.Viewport;
import inf112.skeleton.app.GameObjects.Player;
import inf112.skeleton.app.Main;

public class Board {

    private TmxMapLoader loader;
    private TiledMap map;
    private TiledMapTileLayer floorLayer;
    private TiledMapTileLayer beltLayer;
    private TiledMapTileLayer laserLayer;
    private TiledMapTileLayer wallLayer;
    private TiledMapRenderer mapRenderer;

    public Board(String mapPath) {

        loader = new TmxMapLoader();
        TmxMapLoader.Parameters parameters = new TmxMapLoader.Parameters();
        parameters.flipY = true;
        map = loader.load(mapPath, parameters);
        mapRenderer = new OrthogonalTiledMapRenderer(map, Main.UNIT_SCALE);

        beltLayer =  (TiledMapTileLayer) map.getLayers().get("belts");
        floorLayer = (TiledMapTileLayer) map.getLayers().get("floor");
        laserLayer = (TiledMapTileLayer) map.getLayers().get("lasers");
        wallLayer =  (TiledMapTileLayer) map.getLayers().get("walls");

    }

    public void render(OrthographicCamera camera) {
        mapRenderer.setView(camera);
        mapRenderer.render();
    }


    @SuppressWarnings("Duplicates")
    public void boardInteractsWithPlayer(Player player){
        int x = (player.getX()) / Main.MOVE_DIST;
        int y = (player.getY()) / Main.MOVE_DIST;

        // check if player is on a belt:
        TiledMapTileLayer.Cell currentCell = beltLayer.getCell(x,y);
        if(currentCell != null && currentCell.getTile().getProperties().containsKey("Belt")){
            Direction dir = Direction.valueOf(currentCell.getTile().getProperties().getValues().next().toString());
            if(player.canGo(dir)) {
                player.moveInDirection(dir);
            }
        }
        //Player might have moved so we need to acquire these again:
        x = (player.getX()) / Main.MOVE_DIST;
        y = (player.getY()) / Main.MOVE_DIST;

        // check if player is standing in a laser:
        currentCell = laserLayer.getCell(x,y);
        if(currentCell != null && currentCell.getTile().getProperties().containsKey("Laser")) {
            System.out.println("Ouch!");
            player.takeDamage();
        }

        // check if player is standing on the floor:
        if (floorLayer.getCell(x,y).getTile().getProperties().containsKey("Floor")) {
            System.out.println("Floor");
        } else if (floorLayer.getCell(x,y).getTile().getProperties().containsKey("Hole")){
            System.out.println("Hole");
            //player.getsDestroyed();
        }
    }

    public void dispose(){
        map.dispose();

    }

    public TiledMapTileLayer getWallLayer(){
        return this.wallLayer;
    }
}
