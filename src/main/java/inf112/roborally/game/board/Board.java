package inf112.roborally.game.board;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.animations.Animation;
import inf112.roborally.game.animations.LaserAnimation;
import inf112.roborally.game.animations.RepairAnimation;
import inf112.roborally.game.enums.Direction;
import inf112.roborally.game.enums.Rotate;
import inf112.roborally.game.objects.Flag;
import inf112.roborally.game.objects.GameObject;
import inf112.roborally.game.objects.LaserBeam;
import inf112.roborally.game.objects.StartPosition;
import inf112.roborally.game.player.Player;
import inf112.roborally.game.tools.AssMan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static inf112.roborally.game.tools.TiledTools.cellContainsKey;
import static inf112.roborally.game.tools.TiledTools.getValue;


@SuppressWarnings("Duplicates")
public class Board extends TiledBoard {
    private float volume = .25f;

    protected final List<Player> players;
    protected final ArrayList<Flag> flags;
    protected final ArrayList<LaserAnimation> lasers;
    protected final ArrayList<LaserBeam> laserGuns;
    protected final ArrayList<StartPosition> startPlates;
    protected final RoboRallyGame game;

    public Board(final RoboRallyGame game) {
        this.game = game;
        players = Collections.synchronizedList(new ArrayList<Player>());
        flags = new ArrayList<>();
        lasers = new ArrayList<>();
        laserGuns = new ArrayList<>();
        startPlates = new ArrayList<>();
    }

    public void findLaserGuns() {
        for (int x = 0; x < laserLayer.getWidth(); x++) {
            for (int y = 0; y < laserLayer.getHeight(); y++) {
                TiledMapTileLayer.Cell cell = laserLayer.getCell(x, y);
                if (cell != null)
                    laserGuns.add(new LaserBeam(x, y, Direction.valueOf(getValue(cell)), this));
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
        if(player == null){
            return;
        }
        players.add(player);
    }

    public void placePlayers() {
        findStartPlates();
        Collections.sort(startPlates);
        int startNumber = 0;
        for (Player currentPlayer : players) {
            currentPlayer.moveToPosition(startPlates.get(startNumber++).position);
            currentPlayer.setDirection(Direction.EAST);
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
                if (!game.soundMuted && !player.hasScreamed()) {
                    AssMan.manager.get(AssMan.SOUND_PLAYER_WILHELM_SCREAM).play(volume);
                    player.setScreamed(true);
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
                if (!game.soundMuted && !player.hasScreamed()) {
                    AssMan.manager.get(AssMan.SOUND_PLAYER_WILHELM_SCREAM).play(volume);
                    player.setScreamed(true);
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

    public void lasersFire() {
        for (LaserBeam laser : laserGuns) laser.fire();
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
            if ((player.isOnRepair(floorLayer) || player.isOnOption(floorLayer)) && player.getDamage() > 0) {
                player.repairOneDamage();
                if (!game.soundMuted) {
                    AssMan.manager.get(AssMan.SOUND_PLAYER_REPAIR).play(volume);
                }
                addAnimation(new RepairAnimation(player.position));
            }
            if (player.isOnOption(floorLayer)) {
                System.out.println("Give option card to player!");
            }
        }
    }

    private void addAnimation(Animation animation) {
        game.gameScreen.animations.add(animation);
    }

    public void drawGameObjects(SpriteBatch batch) {
        drawBackup(batch);
        drawList(players, batch);
        drawLasers(batch);
        drawList(flags, batch);
    }

    public void drawLasers(SpriteBatch batch) {
        for (LaserBeam beam : laserGuns)
            beam.draw(batch);
    }

    public void drawBackup(SpriteBatch batch) {
        for (Player player : players) {
            player.getBackup().getSprite().draw(batch);
        }
    }

    private void drawList(List<? extends GameObject> list, SpriteBatch batch) {
        for (GameObject object : list)
            object.draw(batch);
    }

    public void robotLasersFire() {
        for (Player player : players) {
            player.getLaserCannon().fire(this);
        }
    }

    public ArrayList<Flag> getFlags() {
        return flags;
    }

    public List<Player> getPlayers() {
        return this.players;
    }

    public ArrayList<LaserBeam> getLaserGuns() {
        return laserGuns;
    }


    public void dispose() {
        System.out.println("Disposing board");
        super.dispose();

        for (Flag flag : flags) {
            flag.dispose();
        }
        for(LaserAnimation beam : laserGuns){
            beam.dispose();
        }
        for(LaserAnimation laser : lasers){
            laser.dispose();
        }
        for(Player player : players){
            player.dispose();
        }
    }
}
