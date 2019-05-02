package inf112.roborally.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import inf112.roborally.game.Main;
import inf112.roborally.game.board.Board;
import inf112.roborally.game.enums.Direction;
import inf112.roborally.game.enums.Rotate;
import inf112.roborally.game.player.Player;
import inf112.roborally.game.tools.TiledTools;

import static inf112.roborally.game.tools.TiledTools.cellContainsKey;
import static inf112.roborally.game.tools.TiledTools.getValue;

public class MovableGameObject extends GameObject {
    protected int rotationDegree;
    private Direction direction;

    /**
     * A GameObject that is facing a certain direction and can move in
     * said direction. It can also rotate. Facing south by default.
     *
     * @param x position x
     * @param y position y
     */
    public MovableGameObject(int x, int y) {
        super(x, y);
        direction = Direction.SOUTH;
        rotationDegree = direction.getRotationDegree();
    }

    @Override
    public void updateSprite() {
        sprite.setRotation(rotationDegree);
        super.updateSprite();
    }

    protected void loadVisualRepresentation() {
        sprite.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        sprite.setSize(Main.PIXELS_PER_TILE, Main.PIXELS_PER_TILE);
        sprite.setOriginCenter();

        updateSprite();
    }

    public void move(int steps) {
        for (int i = 0; i < steps; i++)
            moveInDirection(getDirection());
    }

    public void moveInDirection(Direction dir) {
        position.moveInDirection(dir);
    }

    /**
     * Rotates the object - visually too.
     *
     * @param rotateDir which direction the player should rotate.
     */
    public void rotate(Rotate rotateDir) {
        setDirection(direction.rotate(rotateDir));
    }

    public boolean isOffTheBoard(TiledMapTileLayer floorLayer) {
        TiledMapTileLayer.Cell currentCell = floorLayer.getCell(getX(), getY());
        return currentCell == null || cellContainsKey(currentCell, "Hole");
    }

    public boolean isOnRepair(TiledMapTileLayer floorLayer) {
        return !isOffTheBoard(floorLayer) && cellContainsKey(floorLayer.getCell(getX(), getY()), "Special");
    }

    public boolean isOnOption(TiledMapTileLayer floorLayer) {
        return !isOffTheBoard(floorLayer) && getValue(floorLayer.getCell(getX(), getY())).equals("OPTION");
    }

    public boolean isOnExpressBelt(TiledMapTileLayer beltLayer) {
        return cellContainsKey(beltLayer.getCell(getX(), getY()), "Express");
    }

    public boolean isOnBelt(TiledMapTileLayer beltLayer) {
        TiledMapTileLayer.Cell cell = beltLayer.getCell(getX(), getY());
        return cellContainsKey(cell, "Express") || cellContainsKey(cell, "Normal");
    }

    public boolean canGo(Direction direction, TiledMapTileLayer wallLayer) {
        return canLeaveCell(direction, wallLayer) && canEnter(direction, wallLayer);
    }

    private boolean canLeaveCell(Direction direction, TiledMapTileLayer wallLayer) {
        TiledMapTileLayer.Cell currentCell = wallLayer.getCell(getX(), getY());
        if (!cellContainsKey(currentCell, "Wall")) {
            return true;
        }
        return blockedByWall(currentCell, direction);
    }

    private boolean canEnter(Direction direction, TiledMapTileLayer wallLayer) {
        Position nextPosition = new Position(getX(), getY()).moveInDirection(direction);

        TiledMapTileLayer.Cell nextCell = wallLayer.getCell(nextPosition.getX(), nextPosition.getY());
        if (!cellContainsKey(nextCell, "Wall")) {
            return true;
        }
        return blockedByWall(nextCell, direction.getOppositeDirection());
    }


    private boolean blockedByWall(TiledMapTileLayer.Cell cell, Direction direction) {
        return !TiledTools.splitValuesBySpace(cell).contains(direction.toString());
    }

    public boolean crashWithRobot(Direction direction, Board board) {
        Position nextPos = this.position.copy().moveInDirection(direction);
        for (Player other : board.getPlayers()) {
            if (this.equals(other)) continue;

            if (other.position.equals(nextPos)) {
                return true;
            }
        }
        return false;
    }

    protected boolean canPush(Direction direction, Board board) {
        if (this instanceof Player && ((Player) this).getName().equals("testPilot")) return true;

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

    boolean outOfBounds(Board board) {
        return this.getX() < 0 || this.getX() > board.getWidth()
                || this.getY() < 0 || this.getY() > board.getHeight();
    }

    public Direction getDirection() {
        return this.direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
        rotationDegree = direction.getRotationDegree();
    }
}
