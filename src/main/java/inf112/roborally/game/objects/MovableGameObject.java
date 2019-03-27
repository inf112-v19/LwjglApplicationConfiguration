package inf112.roborally.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import inf112.roborally.game.Main;
import inf112.roborally.game.board.Board;
import inf112.roborally.game.enums.Direction;
import inf112.roborally.game.enums.Rotate;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public abstract class MovableGameObject extends GameObject {
    protected int rotationDegree;
    private Direction direction;

    /**
     * A GameObject that is facing a certain direction and can move in
     * said direction. It can also rotate. Facing south by default.
     * @param x position x
     * @param y position y
     */
    public MovableGameObject(int x, int y, String filePath) {
        super(x, y, filePath);
        direction = Direction.SOUTH;
        rotationDegree = direction.getRotationDegree();
    }

    @Override
    public void updateSprite() {
        sprite.setRotation(rotationDegree);
        super.updateSprite();
    }

    public void loadVisualRepresentation() {
        sprite.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        sprite.setSize(Main.PIXELS_PER_TILE, Main.PIXELS_PER_TILE);
        sprite.setOriginCenter();

        updateSprite();
    }

    public void move(int steps) {
        for(int i = 0 ; i < steps; i++)
            moveInDirection(getDirection());
    }

    public void moveInDirection(Direction dir) {
        position.moveInDirection(dir);
    }

    /**
     * Rotates the object - visually too.
     *
     * @param rotateDir which direction the player should rotate.
     * @return the new direction the player is facing.
     */
    public void rotate(Rotate rotateDir) {
         setDirection(direction.rotate(rotateDir));
    }

    public boolean isOffTheBoard(TiledMapTileLayer floorLayer) {
        return floorLayer.getCell(getX(), getY()) == null;
    }

    public boolean isOnRepair(TiledMapTileLayer floorLayer) {
        return !isOffTheBoard(floorLayer) && cellContainsKey(floorLayer.getCell(getX(), getY()), "Special");
    }

    public boolean isOnOption(TiledMapTileLayer floorLayer) {
        return !isOffTheBoard(floorLayer) && getValue(floorLayer.getCell(getX(), getY())).equals("OPTION");
    }

    public boolean canGo(Direction direction, TiledMapTileLayer wallLayer) {
        return canLeaveCell(direction, wallLayer) && canEnter(direction, wallLayer);
    }

    private boolean canLeaveCell(Direction direction, TiledMapTileLayer wallLayer) {
        TiledMapTileLayer.Cell currentCell = wallLayer.getCell(getX(), getY());
        if (!cellContainsKey(currentCell, "Wall")) {
            return true;
        }
        return !blockedByWall(currentCell, direction);
    }

    private boolean canEnter(Direction direction, TiledMapTileLayer wallLayer) {
        Position nextPosition = new Position(getX(), getY()).moveInDirection(direction);
        // check if out of bounds

        TiledMapTileLayer.Cell nextCell = wallLayer.getCell(nextPosition.getX(), nextPosition.getY());
        if (!cellContainsKey(nextCell, "Wall")) {
            return true;
        }
        return !blockedByWall(nextCell, direction.getOppositeDirection());
    }


    private boolean blockedByWall(TiledMapTileLayer.Cell cell, Direction direction) {
        return splitValuesBySpace(getValue(cell)).contains(direction.toString());
    }

    public boolean crashWithRobot(Direction direction, Board board) {
        Position nextPos = new Position(getX(), getY());
        nextPos.moveInDirection(direction);
        for (Player other : board.getPlayers()) {
            if (this.equals(other)) continue;

            if (other.position.equals(nextPos)) {
                return true;
            }
        }
        return false;
    }

    public boolean canPush(Direction direction, Board board) {
        Position nextPos = new Position(getX(), getY());
        nextPos.moveInDirection(direction);
        for (Player other : board.getPlayers()) {
            if (this.equals(other)) continue;

            if (other.position.equals(nextPos)) {
                if (!other.canGo(direction, board.getWallLayer()) || !other.canPush(direction, board)) {
                    return false;
                }
                other.moveInDirection(direction);
            }
        }
        return true;
    }

    public boolean outOfBounds(Board board){
        return this.getX() < 0 || this.getX() > board.getWidth()
                || this.getY() < 0 || this.getY() > board.getHeight();
    }

    public boolean cellContainsKey(TiledMapTileLayer.Cell cell, String target) {
        return cell != null && cell.getTile().getProperties().containsKey(target);
    }

    public List<String> splitValuesBySpace(String string) {
        List<String> splitList = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(string);
        while (st.hasMoreTokens()) {
            splitList.add(st.nextToken());
        }
        return splitList;
    }

    public String getValue(TiledMapTileLayer.Cell cell) {
        return cell.getTile().getProperties().getValues().next().toString();
    }

    public void setDirection(Direction direction){
        this.direction = direction;
        rotationDegree = direction.getRotationDegree();
    }

    public Direction getDirection(){
        return this.direction;
    }


}
