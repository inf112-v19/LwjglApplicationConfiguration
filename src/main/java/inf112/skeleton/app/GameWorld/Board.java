package inf112.skeleton.app.GameWorld;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import inf112.skeleton.app.GameObjects.MoveableGameObject;
import inf112.skeleton.app.GameObjects.Player;
import inf112.skeleton.app.GameObjects.PlayerMovement;
import inf112.skeleton.app.Main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {

    private TiledMap map;
    private TmxMapLoader loader;
    private TiledMapRenderer mapRenderer;

    private TiledMapTileLayer floorLayer;
    private TiledMapTileLayer beltLayer;
    private TiledMapTileLayer laserLayer;
    private TiledMapTileLayer wallLayer;


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
    }

    public void render(OrthographicCamera camera) {
        mapRenderer.setView(camera);
        mapRenderer.render();
    }

    public void dispose(){
        map.dispose();
    }

    public void update(Player player) {
        MoveableGameObject playerMovement = player.getPlayerMovement();
        if(playerMovement.moved) {
            if (canGo(playerMovement))
                playerMovement.move(1);
            boardInteractsWithPlayer(player);
        }

    }

    public boolean canGo(MoveableGameObject gameObject){
        Direction direction = gameObject.getDirection();
        // first check the current tile:
        int newX = (int) gameObject.getX() / Main.TILE_LENGTH;
        int newY = (int) gameObject.getY() / Main.TILE_LENGTH;

        // check this tile:
        TiledMapTileLayer.Cell currentCell =  getWallLayer().getCell(newX,newY);
        List<String> walls = new ArrayList<>();
        if(currentCell != null && currentCell.getTile().getProperties().containsKey("Wall")) {
            walls = splitBySpace(currentCell.getTile().getProperties().getValues().next().toString());
        }
        if (walls.contains(direction.toString())){
            System.out.println("Hit a wall!(here)");
            return false;
        }

        // move new position to target tile:
        switch (direction){
            case NORTH:
                newY++; break;
            case SOUTH:
                newY--; break;
            case WEST:
                newX--; break;
            case EAST:
                newX++; break;
        }

        // check target tile:
        if(newX < 0 || newY < 0 || getWidth() <= newX || getHeight() <= newY)
            return false;

        walls = new ArrayList<>();
        TiledMapTileLayer.Cell targetCell = wallLayer.getCell(newX, newY);

        if(targetCell != null && targetCell.getTile().getProperties().containsKey("Wall")) {
            walls = splitBySpace(targetCell.getTile().getProperties().getValues().next().toString());
        }

        Direction oppositeDirection = direction.getOppositeDirection();

        if (walls.contains(oppositeDirection.toString())){
            System.out.println("Hit a wall!(next)");
            return false;
        }

        return true;
    }
    // helper method for canGo()

    public List<String> splitBySpace(String strToSplit){
        List<String> splitList;
        String [] items = strToSplit.split(" ");
        splitList = Arrays.asList(items);
        return splitList;
    }
    public void boardInteractsWithPlayer(Player player){
        if (player == null) return;

        PlayerMovement playerMovement = player.getPlayerMovement();

        beltsMove(playerMovement);

        int x = (int) playerMovement.getX() / Main.TILE_LENGTH;
        int y = (int) playerMovement.getY() / Main.TILE_LENGTH;

        if(lasersHit(x, y)) {
            System.out.println("Ouch!");
            player.takeDamage();
        }
        if(playerIsOffTheBoard(x, y)) {
            player.destroy();
        }
    }

    private void beltsMove(PlayerMovement playerMovement){
        int x = (int) playerMovement.getX() / Main.TILE_LENGTH;
        int y = (int) playerMovement.getY() / Main.TILE_LENGTH;

        // check if player is on a belt:
        TiledMapTileLayer.Cell currentCell = beltLayer.getCell(x,y);
        if(currentCell != null && currentCell.getTile().getProperties().containsKey("Belt")){
            Direction dir = Direction.valueOf(currentCell.getTile().getProperties().getValues().next().toString());
            if(canGo(playerMovement)) {
                playerMovement.moveInDirection(dir);
            }
        }
    }
    private boolean lasersHit(int x, int y){
        TiledMapTileLayer.Cell currentCell = laserLayer.getCell(x, y);
        return currentCell != null && currentCell.getTile().getProperties().containsKey("Laser");
    }

    private boolean playerIsOffTheBoard(int x, int y){
        return  !floorLayer.getCell(x,y).getTile().getProperties().containsKey("Floor");
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
