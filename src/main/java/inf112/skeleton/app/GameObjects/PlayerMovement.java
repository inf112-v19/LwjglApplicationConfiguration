package inf112.skeleton.app.GameObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import inf112.skeleton.app.GameWorld.Board;
import inf112.skeleton.app.GameWorld.Direction;
import inf112.skeleton.app.Main;
import inf112.skeleton.app.Rotate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlayerMovement extends GameObject{
    public boolean playerMoved;
    private Player player;
    private Direction direction;
    private int rotationDegree;


    public PlayerMovement(int x, int y, String filePath, Player player){
        super(x, y, filePath);
        direction = Direction.SOUTH;
        this.player = player;
        rotationDegree = 180;

        loadVisualRepresentation();
    }

    /** ONLY FOR TESTING */
    public PlayerMovement(int x, int y){
        super(x, y, "assets/robot/tvBot.png");
        direction = Direction.SOUTH;
        rotationDegree = 180;

        loadVisualRepresentation();
    }

    public void loadVisualRepresentation() {
        sprite.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        sprite.setSize(Main.TILE_LENGTH, Main.TILE_LENGTH);
        sprite.setOriginCenter();
        sprite.setRotation(this.rotationDegree);

    }

    public void moveToBackup() {
        move(player.getBackup().getX(), player.getBackup().getY());
    }

    public void moveInDirection(Direction dir){
        switch (dir){
            case NORTH:
                moveY(getY() + Main.TILE_LENGTH); break;
            case SOUTH:
                moveY(getY() - Main.TILE_LENGTH); break;
            case EAST:
                moveX(getX() + Main.TILE_LENGTH); break;
            case WEST:
                moveX(getX() - Main.TILE_LENGTH); break;
        }
    }
    
    public void move(int steps) {
        switch (direction){
            case NORTH:
                moveY(getY() + Main.TILE_LENGTH * steps); break;
            case SOUTH:
                moveY(getY() - Main.TILE_LENGTH * steps); break;
            case EAST:
                moveX(getX() + Main.TILE_LENGTH * steps); break;
            case WEST:
                moveX(getX() - Main.TILE_LENGTH * steps); break;

        }
    }

    public boolean canGo(Direction dir){
        Board board = player.getBoard();
        // first check the current tile:
        int newX = (int) getX() / Main.TILE_LENGTH;
        int newY = (int) getY() / Main.TILE_LENGTH;

        // check this tile:
        TiledMapTileLayer.Cell currentCell =  board.getWallLayer().getCell(newX,newY);
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
        if(newX < 0 || newY < 0 || board.getWidth() <= newX || board.getHeight() <= newY)
            return false;

        walls = new ArrayList<>();
        TiledMapTileLayer.Cell targetCell = board.getWallLayer().getCell(newX, newY);

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

    /**
     * Rotates the player - visually too.
     *
     * @param rotateDir which direction the player should rotate.
     * @return the new direction the player is facing.
     */
    public Direction rotate(Rotate rotateDir) {
        final Direction NORTH = Direction.NORTH;
        final Direction WEST = Direction.WEST;
        final Direction SOUTH = Direction.SOUTH;
        final Direction EAST = Direction.EAST;


        if (rotateDir.equals(Rotate.RIGHT)) {
            switch (this.direction) {
                case NORTH: this.direction = EAST; break;
                case EAST: this.direction = SOUTH; break;
                case SOUTH: this.direction = WEST; break;
                case WEST: this.direction = NORTH; break;
            }
            this.rotationDegree += 90;
        }
        else if (rotateDir.equals(Rotate.LEFT)) {
            switch (this.direction) {
                case NORTH: this.direction = WEST; break;
                case WEST: this.direction = SOUTH; break;
                case SOUTH: this.direction = EAST; break;
                case EAST: this.direction = NORTH; break;
            }
            this.rotationDegree -= 90;
        }
        else if (rotateDir.equals(Rotate.UTURN)) {
            switch (this.direction) {
                case NORTH: this.direction = SOUTH; break;
                case SOUTH: this.direction = NORTH; break;
                case WEST:  this.direction = EAST;  break;
                case EAST:  this.direction = WEST;  break;
            }
            this.rotationDegree += 180;
        }
        return this.direction;
    }

    public void setDirection(Direction direction){
        this.direction = direction;
    }
    public Direction getDirection(){
        return this.direction;
    }

}