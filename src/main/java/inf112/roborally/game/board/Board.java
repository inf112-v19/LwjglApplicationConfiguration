package inf112.roborally.game.board;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.*;
import inf112.roborally.game.objects.*;
import inf112.roborally.game.enums.Direction;

import java.util.*;

public abstract class Board extends BoardCreator {

    protected ArrayList<Player> players;
    protected ArrayList<RepairSite> repairSites;
    protected ArrayList<Flag> flags;
    protected ArrayList<Laser> lasers;
    protected ArrayList<StartPosition> startPlates;

    public boolean boardWantsToMuteMusic = false;
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

    public void dispose() {
        System.out.println("disposing board");
        map.dispose();
    }

    public void beltsMove() {
        for (Player player : players) {
            if (player == null) continue;

            beltsMove(player);

            if (playerIsOffTheBoard(player.getX(), player.getY())) {
                player.destroy();
            }
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

    private boolean playerIsOffTheBoard(int x, int y) {
        return (floorLayer.getCell(x, y) == null);
    }

    public void lasersFire(){
        for(Player player : players){
            if(lasersHit(player)) {
                player.takeDamage();
            }
        }
    }

    private boolean lasersHit(Player player) {
        TiledMapTileLayer.Cell currentCell = laserLayer.getCell(player.getX(), player.getY());
        return currentCell != null && currentCell.getTile().getProperties().containsKey("Laser");
    }

    public void visitFlags(){
        for (Player player : players) {
            for(Flag f : flags){
                if(player.positionEquals(f)){
                    player.visitFlag(f.getFlagNumber());
                    player.moveBackupToPlayerPosition();
                }
            }
        }
    }

    public void visitSpecialFields() {
        for (Player player : players){
            if(playerIsOnRepair(player)){
                player.repairOneDamage();
                player.getBackup().moveToPlayerPosition();
            }
        }
    }

    public boolean playerIsOnRepair(Player player) {
        return !(playerIsOffTheBoard(player.getX(), player.getY()))
                && (floorLayer.getCell(player.getX(), player.getY()).getTile().getProperties().containsKey("Special"));
    }

    public void updatePlayers() {
        for (Player player : players) {
            player.update();
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
