package inf112.skeleton.app.GameWorld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import inf112.skeleton.app.GameObjects.Player;
import inf112.skeleton.app.Main;

public class Board {

    private TiledMap map;
    private TmxMapLoader loader;
    private TiledMapRenderer mapRenderer;

    private TiledMapTileLayer floorLayer;
    private TiledMapTileLayer beltLayer;
    private TiledMapTileLayer laserLayer;
    private TiledMapTileLayer wallLayer;
    private TiledMapTileLayer backupLayer;


    public Board(String mapPath) {

        loader = new TmxMapLoader();
        TmxMapLoader.Parameters parameters = new TmxMapLoader.Parameters();
        parameters.flipY = true;
        map = loader.load(mapPath, parameters);
        mapRenderer = new OrthogonalTiledMapRenderer(map, Main.UNIT_SCALE);

        beltLayer = (TiledMapTileLayer) map.getLayers().get("belts");
        floorLayer = (TiledMapTileLayer) map.getLayers().get("floor");
        laserLayer = (TiledMapTileLayer) map.getLayers().get("lasers");
        wallLayer = (TiledMapTileLayer) map.getLayers().get("walls");
        backupLayer = (TiledMapTileLayer) map.getLayers().get("backup");
    }

    public void render(OrthographicCamera camera) {
        mapRenderer.setView(camera);
        mapRenderer.render();
    }

    public void leaveBackup(int x, int y){
        TiledMapTileSet tileset = map.getTileSets().getTileSet(0);
        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
        cell.setTile(tileset.getTile(29));
//        backupLayer.setCell(x/Main.MOVE_DIST ,y/Main.MOVE_DIST , cell);

        System.out.println(x/Main.MOVE_DIST + " " + y/Main.MOVE_DIST);
    }


    @SuppressWarnings("Duplicates")
    private void lasersFire(Player player){
        int x = (player.getX()) / Main.MOVE_DIST;
        int y = (player.getY()) / Main.MOVE_DIST;

        // check if player is standing in a laser:
        TiledMapTileLayer.Cell currentCell = laserLayer.getCell(x,y);
        if(currentCell != null && currentCell.getTile().getProperties().containsKey("Laser")) {
            System.out.println("Ouch!");
            player.takeDamage();
        }
    }

    @SuppressWarnings("Duplicates")
    private void beltsMove(Player player){
        int x = (player.getX()) / Main.MOVE_DIST;
        int y = (player.getY()) / Main.MOVE_DIST;

        // check if player is on a belt:
        TiledMapTileLayer.Cell currentCell = beltLayer.getCell(x,y);
        if(currentCell != null && currentCell.getTile().getProperties().containsKey("Belt")){
            Direction dir = Direction.valueOf(currentCell.getTile().getProperties().getValues().next().toString());
            if(player.canGo(dir, this)) {
                player.moveInDirection(dir);
            }
        }
    }

    private boolean playerIsOffTheBoard(Player player){
        int x = (player.getX()) / Main.MOVE_DIST;
        int y = (player.getY()) / Main.MOVE_DIST;

        // check if player is standing on the floor:
        return  !floorLayer.getCell(x,y).getTile().getProperties().containsKey("Floor");
    }

    public void boardInteractsWithPlayer(Player player){
        if (player == null) return;

        beltsMove(player);
        lasersFire(player);
        if(playerIsOffTheBoard(player))
            Gdx.app.log("Player", "is off the board");
    }

    public void dispose(){
        map.dispose();

    }

    public TiledMapTileLayer getWallLayer(){
        return this.wallLayer;
    }

    public int getWidth(){
        return this.floorLayer.getWidth();
    }

    public int getHeight(){
        return this.floorLayer.getHeight();
    }
}
