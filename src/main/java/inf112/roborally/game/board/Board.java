package inf112.roborally.game.board;

import com.badlogic.gdx.Gdx;
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

import static inf112.roborally.game.board.TiledTools.*;


@SuppressWarnings("Duplicates")
public abstract class Board extends TiledBoard {

    protected ArrayList<Player> players;
    protected ArrayList<RepairSite> repairSites;
    protected ArrayList<Flag> flags;
    protected ArrayList<LaserAnimation> lasers;
    protected ArrayList<StartPosition> startPlates;

    // Need this one so we don't check for non existing music when sounds are muted/disposed
    private boolean soundIsMuted;


    public Board() {
        players = neasdw ArrayList<>();
        repairSites = new ArrayList<>();
        flags = new ArrayList<>();
        lasers = new ArrayList<>();
        startPlates = new ArrayList<>();
        soundIsMuted = false;
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
        expressBeltsMovePlayers();
        beltsMovePlayers();
        lasersFire();
        robotLasersFire();
        visitFlags();
        visitSpecialFields();
    }

    private void expressBeltsMovePlayers() {
        for (Player player : players) {
            if (player == null) continue;
            expressBeltsMove(player);
            if (player.isOffTheBoard(floorLayer)) {
                if (!soundIsMuted && !player.hasScreamed()) {
                    player.getSoundFromPlayer(2).play();
                }
                player.destroy();
            }
        }
    }

    private void beltsMovePlayers() {
        for (Player player : players) {
            if (player == null) continue;
            beltsMove(player);
            if (player.isOffTheBoard(floorLayer)) {
                if (!soundIsMuted && !player.hasScreamed()) {
                    player.getSoundFromPlayer(2).play();
                }
                player.destroy();
            }
        }
    }

    private void expressBeltsMove(Player player) {
        TiledMapTileLayer.Cell currentCell = beltLayer.getCell(player.getX(), player.getY());
        if (player.isOnExpressBelt(beltLayer)) {
            Direction beltDir = Direction.valueOf(getValue(currentCell));
            if (!player.canGo(beltDir, wallLayer) || player.crashWithRobot(beltDir, this)) return;

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
            if (!player.canGo(beltDir, wallLayer) || player.crashWithRobot(beltDir, this)) return;

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
            if (player.hitByLaser(laserLayer)) {
                if (!soundIsMuted) {
                    player.getSoundFromPlayer(0).play();
                }
                player.takeDamage();
            }
        }
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
            if (player.isOnRepair(floorLayer) || player.isOnOption(floorLayer)) {
                player.getBackup().moveToPlayerPosition();
            }
        }
    }


    public void cleanUp() {
        for (Player player : players) {
            if (player.isOnRepair(floorLayer) || player.isOnOption(floorLayer)) {
                player.repairOneDamage();
                if (!soundIsMuted) {
                    player.getSoundFromPlayer(1).play();
                }
                addAnimation(new RepairAnimation(player.position));
            }
            if (player.isOnOption(floorLayer)) {
                System.out.println("Give option card to player!");
            }
            player.respawn();
        }
    }

    private void addAnimation(Animation animation) {
        ((RoboRallyGame) Gdx.app.getApplicationListener()).gameScreen.animations.add(animation);
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

    public ArrayList<Player> getPlayers() {
        return this.players;
    }


    public void robotLasersFire() {
        for (Player player : players) {
            player.fireLaser(this);
        }
    }

    public ArrayList<Flag> getFlags() {
        return flags;
    }
}
