package inf112.roborally.game.board;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Array;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.objects.*;
import inf112.roborally.game.Main;
import inf112.roborally.game.enums.Direction;
import inf112.roborally.game.enums.Rotate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Board extends BoardCreator {


    protected ArrayList<Player> players;

    protected ArrayList<RepairSite> repairSites;
    protected Array<Flag> flags;

    public Board() {
        flags = new Array<>();
        repairSites = new ArrayList<>();
        players = new ArrayList<>();
    }

    public void render(OrthographicCamera camera) {
        mapRenderer.setView(camera);
        mapRenderer.render();
    }

    public void handleInput() {
        //Just for testing
        Player p1 = players.get(0);
        Player p2 = players.get(1);

        if(Gdx.input.isKeyJustPressed(Input.Keys.R)){
            p1.getRegisters().returnCardsFromRegisters(p1.getCardsInHand());
            // messy but it works:
            ((RoboRallyGame)Gdx.app.getApplicationListener()).gameScreen.getHud().getCardsInHandDisplay().updateCardsInHandVisually();
        }

        p1.moved = false;

        if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            p1.setDirection(Direction.EAST);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            p1.setDirection(Direction.WEST);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            p1.setDirection(Direction.NORTH);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            p1.setDirection(Direction.SOUTH);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.B)) {
            p1.moveBackupToPlayerPosition();
        }


        p2.moved = false;

        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            p2.setDirection(Direction.EAST);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            p2.setDirection(Direction.WEST);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            p2.setDirection(Direction.NORTH);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            p2.setDirection(Direction.SOUTH);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.L)) {
            p2.moveBackupToPlayerPosition();
        }
    }

    public void dispose() {
        map.dispose();
    }

    public void updateBoard(){
        for(Player player : players) {
            boardInteractsWithPlayer(player);
            if(playerIsOnRepair(player.getX(), player.getY())) {
                player.updateBackup();
                player.repairOneDamage();
            }
        }

    }

    public void update() {
        handleInput();
        for (Player player : players) {
            player.updateSprite();
            if (player.moved) {
                if (canGo(player))
                    player.move(1);
                boardInteractsWithPlayer(player);
            }
            player.update();
            player.updateSprite();
        }
    }

    public void executeCard(Player player, ProgramCard card){
        if(card.getRotate() != Rotate.NONE){
            player.rotate(card.getRotate());
        }
        else{
            for(int i = 0; i < card.getMoveDistance(); i++){
                if(canGo(player)){
                    player.move(1);
                }
            }
        }
    }

    public boolean canGo(MovableGameObject gameObject) {
        Direction direction = gameObject.getDirection();
        // first check the current tile:
        int newX = gameObject.getX();
        int newY = gameObject.getY();

        // check this tile:
        TiledMapTileLayer.Cell currentCell = getWallLayer().getCell(newX, newY);
        List<String> walls = new ArrayList<>();
        if (currentCell != null && currentCell.getTile().getProperties().containsKey("Wall")) {
            walls = splitBySpace(currentCell.getTile().getProperties().getValues().next().toString());
        }
        if (walls.contains(direction.toString())) {
            System.out.println("Hit a wall!(here)");
            return false;
        }

        // move new position to target tile:
        switch (direction) {
            case NORTH:
                newY++;
                break;
            case SOUTH:
                newY--;
                break;
            case WEST:
                newX--;
                break;
            case EAST:
                newX++;
                break;
        }

        // check target tile:
        if (newX < 0 || newY < 0 || getWidth() <= newX || getHeight() <= newY)
            return false;

        walls = new ArrayList<>();
        TiledMapTileLayer.Cell targetCell = wallLayer.getCell(newX, newY);

        if (targetCell != null && targetCell.getTile().getProperties().containsKey("Wall")) {
            walls = splitBySpace(targetCell.getTile().getProperties().getValues().next().toString());
        }

        Direction oppositeDirection = direction.getOppositeDirection();

        if (walls.contains(oppositeDirection.toString())) {
            System.out.println("Hit a wall!(next)");
            return false;
        }

        return true;
    }
    // helper method for canGo()

    public List<String> splitBySpace(String strToSplit) {
        List<String> splitList;
        String[] items = strToSplit.split(" ");
        splitList = Arrays.asList(items);
        return splitList;
    }

    public void boardInteractsWithPlayer(Player player) {
        if (player == null) return;


        beltsMove(player);

        int x = player.getX();
        int y = player.getY();

        if (lasersHit(x, y)) {
            System.out.println("Ouch!");
            player.takeDamage();
        }
        if (playerIsOffTheBoard(x, y)) {
            player.destroy();
        }
        int flagInPlayerPos = posHasFlagOnIt(x, y);

        // If flagInPlayerPos is greater than 0, it means that a flag is found, and it is
        // the flags number
        if(flagInPlayerPos > 0) {
            player.addFlag(flagInPlayerPos);
            player.updateBackup();
            System.out.printf("%s found a flag!%n", player.getName());
        }
    }


    private void beltsMove(Player player) {
        // check if player is on a belt:
        TiledMapTileLayer.Cell currentCell = beltLayer.getCell(player.getX(), player.getY());
        if (currentCell != null && currentCell.getTile().getProperties().containsKey("Belt")) {
            Direction dir = Direction.valueOf(currentCell.getTile().getProperties().getValues().next().toString());
            if (canGo(player)) {
                player.moveInDirection(dir);
            }
        }
    }

    private boolean lasersHit(int x, int y) {
        TiledMapTileLayer.Cell currentCell = laserLayer.getCell(x, y);
        return currentCell != null && currentCell.getTile().getProperties().containsKey("Laser");
    }

    private boolean playerIsOffTheBoard(int x, int y) {
        return (floorLayer.getCell(x, y) == null);
    }

    // Check the position if there is a flag there
    // Return the flagnumber if true, else return -1
    private int posHasFlagOnIt(int x, int y) {
        for(Flag f : flags) {
            if(f.getX() == x && f.getY() == y) {
                return f.getFlagNumber();
            }
        }
        return -1;
    }

    public boolean playerIsOnRepair(int x, int y) {
        for(RepairSite rs : repairSites) {
            if(rs.getX() == x && rs.getY() == y) {
                return true;
            }
        }
        return false;
    }


    public TiledMapTileLayer getWallLayer() {
        return this.wallLayer;
    }

    public int getWidth() {
        return this.floorLayer.getWidth();
    }

    public int getHeight() {
        return this.floorLayer.getHeight();
    }

    public ArrayList<Player> getPlayers() {
        return this.players;
    }

    public void drawGameObjects(SpriteBatch batch) {
        for (Flag o : flags) {
            o.getSprite().draw(batch);
        }
        for (RepairSite rs : repairSites) {
            rs.getSprite().draw(batch);
        }
    }

    public void drawPlayers(SpriteBatch batch) {
        for (Player player : players) {
            player.getSprite().draw(batch);
        }
    }

    public void drawBackup(SpriteBatch batch){
        for(Player player : players){
            player.getBackup().getSprite().draw(batch);
        }

    }

}