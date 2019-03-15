package inf112.roborally.game.board;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.*;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.objects.*;
import inf112.roborally.game.enums.Direction;

import java.util.*;

public abstract class Board extends BoardCreator {

    protected ArrayList<Player> players;
    protected ArrayList<RepairSite> repairSites;
    protected ArrayList<Flag> flags;
    protected ArrayList<Laser> lasers;
    protected ArrayList<StartPosition> startPlates;

    private boolean boardWantsToMuteMusic = false;
    private boolean musicIsMuted = false;


    public Board() {
        players = new ArrayList<>();
        repairSites = new ArrayList<>();
        flags = new ArrayList<>();
        lasers = new ArrayList<>();
        startPlates = new ArrayList<>();
    }

    public void render(OrthographicCamera camera) {
        mapRenderer.setView(camera);
        mapRenderer.render();
    }

    public void findLasers() {
        for (int x = 0; x < laserLayer.getWidth(); x++) {
            for (int y = 0; y < laserLayer.getHeight(); y++) {
                if (laserLayer.getCell(x, y) != null) {
                    Direction direction = Direction.valueOf(
                            laserLayer.getCell(x, y).getTile().getProperties().getValues().next().toString());
                    lasers.add(new Laser(x, y, direction));
                }
            }
        }
    }

    public void findStartPlates(){
        for (int x = 0; x < startLayer.getWidth(); x++) {
            for (int y = 0; y < startLayer.getHeight(); y++) {
                TiledMapTileLayer.Cell cell = startLayer.getCell(x,y);
                if(cell != null){
                    int value = Integer.parseInt(cell.getTile().getProperties().getValues().next().toString());
                    startPlates.add(new StartPosition(x, y, value));
                }
            }
        }
    }

    public void placePlayers(){
        findStartPlates();
        Collections.sort(startPlates);
        for(int i = 0; i < players.size(); i++){
            players.get(i).move(startPlates.get(i).getX(), startPlates.get(i).getY());
            players.get(i).moveBackupToPlayerPosition();
            players.get(i).setDirection(Direction.EAST);
        }
    }

    public void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.M)){
            boardWantsToMuteMusic = true;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.R)){
            players.get(0).getRegisters().returnCardsFromRegisters(players.get(0).getCardsInHand());
            // messy but it works:
            ((RoboRallyGame) Gdx.app.getApplicationListener()).gameScreen.getHud().getCardsInHandDisplay().
                    updateCardsInHandVisually();
        }
    }

    public void dispose() {
        System.out.println("disposing board");
        map.dispose();
    }

    public void updateBoard() {
        for (Player player : players) {
            boardInteractsWithPlayer(player);
            if (playerIsOnRepair(player.getX(), player.getY())) {
                player.updateBackup();
                player.repairOneDamage();
            }
        }

    }

    public void update() {
        handleInput();
        for (Player player : players) {
            player.updateSprite();
            player.update();
            player.updateSprite();
        }
    }

    public boolean canGo(MovableGameObject player, Direction direction) {
        // first check the current tile:
        Position nextPos = new Position(player.getX(), player.getY());

        // check this tile:
        TiledMapTileLayer.Cell currentCell = getWallLayer().getCell(nextPos.getX(), nextPos.getY());
        List<String> walls = new ArrayList<>();
        if (currentCell != null && currentCell.getTile().getProperties().containsKey("Wall")) {
            walls = getProperties(currentCell.getTile().getProperties().getValues().next().toString());
        }
        if (walls.contains(direction.toString())) {
            System.out.println("Hit a wall!(here)");
            return false;
        }

        // move new position to target tile:
        nextPos.moveInDirection(direction);

        // check target tile:
        if (nextPos.getX() < 0 || nextPos.getY() < 0 || getWidth() <= nextPos.getX()
                || getHeight() <= nextPos.getY())
            return false;

        walls = new ArrayList<>();
        TiledMapTileLayer.Cell targetCell = wallLayer.getCell(nextPos.getX(), nextPos.getY());

        if (targetCell != null && targetCell.getTile().getProperties().containsKey("Wall")) {
            walls = getProperties(targetCell.getTile().getProperties().getValues().next().toString());
        }

        Direction oppositeDirection = direction.getOppositeDirection();

        if (walls.contains(oppositeDirection.toString())) {
            System.out.println("Hit a wall!(next)");
            return false;
        }

        for (Player other : players) {
            if (other.equals(player)) continue;

            if (other.positionEquals(nextPos)) {
                if (!canGo(other, direction)) {
                    return false;
                }
                other.moveInDirection(direction);
            }
        }

        return true;
    }

    /**
     * helper method for canGo()
     */
    public List<String> getProperties(String properties) {
        List<String> splitList = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(properties);
        while(st.hasMoreTokens()){
            splitList.add(st.nextToken());
        }
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
            player.getLaserHitPlayerSound().play(0.05f);
        }
        if (playerIsOffTheBoard(x, y)) {
            player.destroy();
        }
        int flagInPlayerPos = posHasFlagOnIt(x, y);

        // If flagInPlayerPos is greater than 0, it means that a flag is found, and it is
        // the flags number
        if (flagInPlayerPos > 0) {
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
            if (canGo(player, player.getDirection())) {
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
        for (Flag f : flags) {
            if (f.getX() == x && f.getY() == y) {
                return f.getFlagNumber();
            }
        }
        return -1;
    }

    public boolean playerIsOnRepair(int x, int y) {
        for (RepairSite rs : repairSites) {
            if (rs.getX() == x && rs.getY() == y) {
                return true;
            }
        }
        return false;
    }

    public void drawGameObjects(SpriteBatch batch){
        drawBackup(batch);
        drawList(flags, batch);
        drawList(repairSites, batch);
        drawList(lasers, batch);
        drawList(players, batch);
    }

    public void drawBackup(SpriteBatch batch) {
        for (Player player : players) {
            player.getBackup().getSprite().draw(batch);
        }
    }

    private void drawList(ArrayList<? extends GameObject> list, SpriteBatch batch){
        for(GameObject object : list)
            object.draw(batch);

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

    public boolean boardWantsToMuteMusic() {
        return this.boardWantsToMuteMusic;
    }

    public void musicIsMuted() {
        System.out.println("Music is now muted");
        boardWantsToMuteMusic = false;
        musicIsMuted = true;
    }

    public ArrayList<Flag> getFlags(){
        return flags;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }
}
