package inf112.roborally.game.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import inf112.roborally.game.Main;
import inf112.roborally.game.board.Board;
import inf112.roborally.game.enums.Direction;
import inf112.roborally.game.enums.PlayerState;
import inf112.roborally.game.objects.LaserCannon;
import inf112.roborally.game.objects.MovableGameObject;
import inf112.roborally.game.objects.Position;
import inf112.roborally.game.tools.AssMan;

import java.util.ArrayList;

import static inf112.roborally.game.enums.PlayerState.*;

public class Player extends MovableGameObject implements Comparable {
    public static final int MAX_DAMAGE = 10;
    private static final int MAX_LIVES = 3;

    public boolean wantsToPowerDown;
    public ArrayList<ProgramCard> toPlay;
    private PlayerState playerState;
    private LaserCannon laserCannon;
    private String name;
    private int lives;
    private int damage;
    private Backup backup;
    private Board board;
    private ArrayList<TextureRegion> regions;
    private int targetFlag;
    private int nFlags;
    private boolean screamed;  //Whether or not the player has screamed from falling off the map this round.
    private int phase;
    private boolean debugging;
    private ProgramRegisters registers;
    private PlayerHand hand;
    private Texture skinTexture;

    public Player(String name, Texture skin, Direction direction, Board board) {
        this(0, 0);
        this.name = name;
        makeSprite(skin);
        this.board = board;
        setDirection(direction);
        loadVisualRepresentation();
        nFlags = board.getFlags().size();
        backup.setupSprite();
        phase = 0;
        debugging = false;
        skinTexture = AssMan.getPlayerSkins()[0];
        toPlay = new ArrayList<>(5);
    }

    /**
     * Basic player
     */
    private Player(int x, int y) {
        super(x, y);
        damage = 0;
        lives = MAX_LIVES;
        targetFlag = 1;
        nFlags = 1;
        playerState = OPERATIONAL;
        hand = new PlayerHand(this);
        registers = new ProgramRegisters(this);
        backup = new Backup(this);
        laserCannon = new LaserCannon(this);
    }

    /**
     * FOR TESTING ONLY
     */
    public Player(int x, int y, int nFlags) {
        this(x, y);
        this.nFlags = nFlags;
        name = "testBot";
        debugging = true;
    }

    private void makeSprite(Texture skin) {
        sprite = new Sprite(skin);
        regions = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            regions.add(new TextureRegion(getSprite().getTexture(), 32 * 8 * i, 0, 32 * 8, 48 * 8));
        }
        sprite.setRegion(regions.get(0));
    }

    @Override
    public void move(int steps) {
        if (debugging) {
            for (int i = 0; i < steps; i++) moveInDirection(getDirection());
            return;
        }

        screamed = false;

        for (int i = 0; i < steps; i++) {
            if (canGo(getDirection(), board.getWallLayer()) && canPush(getDirection(), board)) {
                moveInDirection(getDirection());
            }
        }
        // every time a player moves we need to check if it is off the board or not
        if (board != null && isOffTheBoard(board.getFloorLayer())) { // need to check if board is null for tests to work
            this.destroy();
        }
    }

    public void reverse() {
        if (debugging) {
            moveInDirection(getDirection().getOppositeDirection());
            return;
        }

        Direction directionToMoveIn = getDirection().getOppositeDirection();
        if (canGo(directionToMoveIn, board.getWallLayer()) && canPush(directionToMoveIn, board))
            moveInDirection(directionToMoveIn);
        move(0);
    }

    public ArrayList<ProgramCard> returnCards() {
        registers.returnCards();
        return hand.getCardsInHand();
    }

    @Override
    public void updateSprite() {
        if (sprite != null) {
            sprite.setRegion(regions.get(getDirection().toInt()));
            sprite.setPosition(getX() * Main.PIXELS_PER_TILE, getY() * Main.PIXELS_PER_TILE + 5);
        }
    }

    /**
     * @return true if player was respawned
     */
    public boolean respawn() {
        if (!isDestroyed()) return false; // Can only respawn dead robots

        if (outOfLives()) {
            System.out.println(name + " is out of the game");
            playerState = GAME_OVER;
            return false;
        } else {
            System.out.println(name + " was respawned!");
            repairAllDamage();
            if (backup != null) {
                backup.movePlayerToBackup();
            }
            playerState = OPERATIONAL;
        }
        return true;
    }

    public void powerDown() {
        if (!wantsToPowerDown || !isOperational()) return;

        playerState = POWERED_DOWN;
        System.out.println(name + " powers down");
        wantsToPowerDown = false;
    }

    public void powerUp() {
        if (isPoweredDown()) return;

        repairAllDamage();
        playerState = OPERATIONAL;
        System.out.println(name + " powers up");
    }

    /**
     * Repairs all damage dealt to the player and unlocks all locked registers.
     */
    public void repairAllDamage() {
        registers.unlockAll();
        damage = 0;
    }

    public void repairOneDamage() {
        if (damage >= 5) {
            registers.unlock();
        }
        if (damage > 0) {
            damage--;
        }
    }

    /**
     * Take one damage. Locks a register if damage taken is greater or equal to 5.
     * Lose a life if damage taken is equal to MAX_DAMAGE.
     */
    public void takeDamage() {
        if (isDestroyed()) return;

        if (damage < MAX_DAMAGE && lives > 0) {
            damage++;

            if (damage >= 5) {
                registers.lock();
            }
        }
        if (damage == MAX_DAMAGE) {
            lives--;
            playerState = DESTROYED;
        }
    }

    public void visitFlag(int flagNumber) {
        if (flagNumber > nFlags) return;

        if (flagNumber == targetFlag) {
            targetFlag++;
            System.out.printf("%s picked up a flag!%n", name);
        }
    }

    public void destroy() {
        if (isDestroyed()) return;

        damage = MAX_DAMAGE;
        playerState = DESTROYED;
        lives--;
    }

    public boolean outOfLives() {
        return lives < 1;
    }

    public boolean isDestroyed() {
        return playerState == DESTROYED;
    }

    public boolean isReady() {
        return playerState == POWERED_DOWN || playerState == READY;
    }

    public boolean isOperational() {
        return playerState == OPERATIONAL;
    }

    public boolean isPoweredDown() {
        return playerState != POWERED_DOWN;
    }

    public boolean hasScreamed() {
        return !this.screamed;
    }

    public boolean hasWon() {
        return targetFlag > nFlags;
    }

    @Override
    public boolean equals(Object other) {
        if (other.getClass() != this.getClass())
            return false;

        return this.name.equals(((Player) other).name);
    }

    @Override
    public int compareTo(Object o) {
        if (o == null) return 0;
        Player other = (Player) o;
        int thisPriority = registers.getCard(phase).getPriority();
        int otherPriority = other.getRegisters().getCard(phase).getPriority();

        return Integer.compare(thisPriority, otherPriority);
    }

    public void setScreamed(boolean b) {
        screamed = b;
    }

    public void setPhase(int phase) {
        this.phase = phase;
    }

    public void setSkinTexture(Texture skinTexture) {
        this.skinTexture = skinTexture;
    }

    public boolean isDebuggingActive() {
        return debugging;
    }

    public LaserCannon getLaserCannon() {
        return laserCannon;
    }

    public int getCardLimit() {
        return ProgramRegisters.MAX_NUMBER_OF_CARDS - damage;
    }

    public int getDamage() {
        return this.damage;
    }

    public int getLives() {
        return this.lives;
    }

    public int getTargetFlag() {
        return targetFlag;
    }

    public String getName() {
        return this.name;
    }

    public Backup getBackup() {
        return backup;
    }

    public ProgramRegisters getRegisters() {
        return registers;
    }

    public PlayerState getPlayerState() {
        return playerState;
    }

    public void setPlayerState(PlayerState playerState) {
        if (this.playerState == GAME_OVER) {
            System.out.println("Can not set player state when player state is: " + playerState);
            return;
        } else if (this.playerState == DESTROYED) {
            System.out.println("Only respawn method can change the state of a destroyed robot");
        }
        this.playerState = playerState;
    }

    int getMaxDamage() {
        return MAX_DAMAGE;
    }

    public PlayerHand getHand() {
        return hand;
    }

    @Override
    public String toString() {
        return getName() + " | Health: " + (10 - damage) + " | Lives: " + lives;
    }

    public void dispose() {
        super.dispose();
        backup.dispose();
    }

    public Player createTestPilot() {
        Player testPilot = new Player("testPilot", sprite.getTexture(), getDirection(), board);
        testPilot.move(getX(), getY());
        return testPilot;
    }

    public Position getTargetFlagPos() {
        return board.getFlags().get(targetFlag - 1).position;
    }
}
