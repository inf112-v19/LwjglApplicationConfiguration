package inf112.roborally.game.board;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.*;

import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.animations.Animation;
import inf112.roborally.game.animations.LaserAnimation;
import inf112.roborally.game.animations.RepairAnimation;
import inf112.roborally.game.enums.Rotate;
import inf112.roborally.game.objects.*;
import inf112.roborally.game.enums.Direction;

import java.util.*;


@SuppressWarnings("Duplicates")
public abstract class Board extends BoardCreator {

    protected ArrayList<Player> players;
    protected ArrayList<RepairSite> repairSites;
    protected ArrayList<Flag> flags;
    protected ArrayList<LaserAnimation> lasers;
    protected ArrayList<StartPosition> startPlates;

    // Need this one so we don't check for non existing music when sounds are muted/disposed
    private boolean soundIsMuted;


    public Board() {
        players = new ArrayList<>();
        repairSites = new ArrayList<>();
        flags = new ArrayList<>();
        lasers = new ArrayList<>();
        startPlates = new ArrayList<>();
        soundIsMuted = false;
    }

    public void render(OrthographicCamera camera) {
        mapRenderer.setView(camera);
        mapRenderer.render();
    }

    public void findLasers() {
        for (int x = 0; x < laserLayer.getWidth(); x++) {
            for (int y = 0; y < laserLayer.getHeight(); y++) {
                TiledMapTileLayer.Cell cell = laserLayer.getCell(x, y);
                if (cell != null) {
                    Direction direction = Direction.valueOf(getValue(cell));
                    lasers.add(new LaserAnimation(x, y, direction));
                }
            }
        }
    }

    public void findStartPlates() {
        for (int x = 0; x < startLayer.getWidth(); x++) {
            for (int y = 0; y < startLayer.getHeight(); y++) {
                TiledMapTileLayer.Cell cell = startLayer.getCell(x, y);
                if (cell != null) {
                    int value = Integer.parseInt(getValue(cell));
                    startPlates.add(new StartPosition(x, y, value));
                }
            }
        }
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void placePlayers() {
        findStartPlates();
        Collections.sort(startPlates);
        int startNumber = 0;
        for (Player currentPlayer : players) {
            currentPlayer.moveToPosition(startPlates.get(startNumber++).position);
            currentPlayer.setDirection(Direction.WEST);
            currentPlayer.updateSprite();
            currentPlayer.getBackup().moveToPlayerPosition();
        }
    }

    public void boardMoves() {
        beltsMovePlayers();
        lasersFire();
        visitFlags();
        visitSpecialFields();
    }

    private void beltsMovePlayers() {
        for (Player player : players) {
            if (player == null) continue;
            expressBeltsMove(player);
            if (isOffTheBoard(player)) {
                if (!soundIsMuted) {
                    player.getSoundFromPlayer(2).play();
                }
                player.destroy();
            }
        }

        for (Player player : players) {
            if (player == null) continue;
            beltsMove(player);
            if (isOffTheBoard(player)) {
                player.destroy();
            }
        }
    }

    private void expressBeltsMove(Player player) {
        TiledMapTileLayer.Cell currentCell = beltLayer.getCell(player.getX(), player.getY());
        if (cellContainsKey(currentCell, "Express")) {

            Direction beltDir = Direction.valueOf(getValue(currentCell));
            if (!canGo(player, beltDir) || crashWithRobot(player, beltDir)) return;

            player.moveInDirection(beltDir);
            currentCell = beltLayer.getCell(player.getX(), player.getY());
            if (!cellContainsKey(currentCell, "Rotate")) return;

            Iterator<Object> i = currentCell.getTile().getProperties().getValues();
            i.next();
            player.rotate(Rotate.valueOf(i.next().toString()));

        }
    }

    private void beltsMove(Player player) {
        TiledMapTileLayer.Cell currentCell = beltLayer.getCell(player.getX(), player.getY());
        if (cellContainsKey(currentCell, "Normal") || cellContainsKey(currentCell, "Express")) {

            Direction beltDir = Direction.valueOf(getValue(currentCell));
            if (!canGo(player, beltDir) || crashWithRobot(player, beltDir)) return;

            player.moveInDirection(beltDir);
            currentCell = beltLayer.getCell(player.getX(), player.getY());
            if (!cellContainsKey(currentCell, "Rotate")) return;

            Iterator<Object> i = currentCell.getTile().getProperties().getValues();
            i.next();
            player.rotate(Rotate.valueOf(i.next().toString()));
        }

        // Gyros rotate
        if (cellContainsKey(currentCell, "Gyro")) {
            player.rotate(Rotate.valueOf(getValue(currentCell)));
        }
    }

    private void lasersFire() {
        for (Player player : players) {
            if (lasersHit(laserLayer.getCell(player.getX(), player.getY()))) {
                if (!soundIsMuted) {
                    player.getSoundFromPlayer(0).play();
                }
                player.takeDamage();
            }
        }
        laserFire();
    }

    private boolean lasersHit(TiledMapTileLayer.Cell currentCell) {
        return cellContainsKey(currentCell, "Laser");
    }

    private void visitFlags() {
        for (Player player : players) {
            for (Flag flag : flags) {
                if (player.position.equals(flag.position)) {
                    player.visitFlag(flag.getFlagNumber());
                    player.getBackup().moveToPlayerPosition();
                }
            }
        }
    }

    private void visitSpecialFields() {
        for (Player player : players) {
            if (isOnRepair(player) || isOnOption(player)) {
                player.getBackup().moveToPlayerPosition();
            }
        }
    }

    public boolean isOffTheBoard(GameObject object) {
        return (floorLayer.getCell(object.getX(), object.getY()) == null);
    }

    private boolean isOnRepair(Player p) {
        return !isOffTheBoard(p) && cellContainsKey(floorLayer.getCell(p.getX(), p.getY()), "Special");
    }

    private boolean isOnOption(Player p) {
        return !isOffTheBoard(p) && getValue(floorLayer.getCell(p.getX(), p.getY())).equals("OPTION");
    }

    public void cleanUp() {
        for (Player player : players) {
            if (isOnRepair(player) || isOnOption(player)) {
                player.repairOneDamage();
                if (!soundIsMuted) {
                    player.getSoundFromPlayer(1).play();
                }
                addAnimation(new RepairAnimation(player.position));
            }
            if (isOnOption(player)) {
                System.out.println("Give option card to player!");
            }
            player.respawn();
        }
    }

    private void addAnimation(Animation animation) {
        ((RoboRallyGame) Gdx.app.getApplicationListener()).gameScreen.animations.add(animation);
    }

    public boolean canGo(MovableGameObject object, Direction direction) {
        return canLeaveCell(object, direction) && canEnter(object, direction);
    }

    private boolean canLeaveCell(MovableGameObject object, Direction direction) {
        TiledMapTileLayer.Cell currentCell = getWallLayer().getCell(object.position.getX(), object.position.getY());
        if (!cellContainsKey(currentCell, "Wall")) {
            return true;
        }
        return !blockedByWall(currentCell, direction);
    }

    private boolean canEnter(MovableGameObject object, Direction direction) {
        Position nextPosition = new Position(object.getX(), object.getY()).moveInDirection(direction);
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


    public boolean crashWithRobot(MovableGameObject player, Direction direction) {
        Position nextPos = new Position(player.getX(), player.getY());
        nextPos.moveInDirection(direction);
        for (Player other : players) {
            if (other.equals(player)) continue;

            if (other.position.equals(nextPos)) {
                return true;
            }
        }
        return false;
    }

    public boolean canPush(MovableGameObject player, Direction direction) {
        Position nextPos = new Position(player.getX(), player.getY());
        nextPos.moveInDirection(direction);
        for (Player other : players) {
            if (other.equals(player)) continue;

            if (other.position.equals(nextPos)) {
                if (!canGo(other, direction) || !canPush(other, direction)) {
                    return false;
                }
                other.moveInDirection(direction);
            }
        }
        return true;
    }

    public boolean cellContainsKey(TiledMapTileLayer.Cell cell, String target) {
        return cell != null && cell.getTile().getProperties().containsKey(target);
    }

    public String getValue(TiledMapTileLayer.Cell cell) {
        return cell.getTile().getProperties().getValues().next().toString();
    }

    public List<String> splitValuesBySpace(String string) {
        List<String> splitList = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(string);
        while (st.hasMoreTokens()) {
            splitList.add(st.nextToken());
        }
        return splitList;
    }

    public void drawGameObjects(SpriteBatch batch) {
        drawBackup(batch);
        drawList(repairSites, batch);
        drawList(players, batch);
        drawList(lasers, batch);
        drawList(flags, batch);
    }

    public void drawBackup(SpriteBatch batch) {
        for (Player player : players) {
            player.getBackup().getSprite().draw(batch);
        }
    }

    private void drawList(ArrayList<? extends GameObject> list, SpriteBatch batch) {
        for (GameObject object : list)
            object.draw(batch);

    }

    public void dispose() {
        System.out.println("disposing board");
        map.dispose();

    }

    public void killTheSound() {
        for (Player p : players) {
            p.killTheSound();
        }
        soundIsMuted = true;
    }

    public void restartTheSound() {
        for (Player p : players) {
            p.createSounds();
        }
        soundIsMuted = false;
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


    public void laserFire() {
        for (Player player :
                players) {
            robotFire(player);
        }
    }

    public void robotFire(Player player) {

        LaserShot laserShot = new LaserShot(player.getDirection(), player.getX(), player.getY());
        while (true) {
            if (!canGo(laserShot, laserShot.getDirection()))
                return;
            laserShot.moveInDirection(laserShot.getDirection());

            for (Player target :
                    players) {
                if (laserShot.position.equals(target.position)) {
                    target.takeDamage();
                    System.out.println(player.getName() + " shoots  " + target.getName());
                    return;
                }

            }

            if (laserShot.getX() < 0 || laserShot.getX() > getWidth()
                    || laserShot.getY() < 0 || laserShot.getY() > getHeight()) {
                return;
            }
        }

    }

    public ArrayList<Flag> getFlags() {
        return flags;
    }

}
