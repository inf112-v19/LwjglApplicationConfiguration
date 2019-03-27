package inf112.roborally.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import inf112.roborally.game.Main;
import inf112.roborally.game.board.Board;
import inf112.roborally.game.board.ProgramCard;
import inf112.roborally.game.board.ProgramRegisters;
import inf112.roborally.game.enums.Direction;
import inf112.roborally.game.enums.PlayerState;
import inf112.roborally.game.enums.Rotate;

import inf112.roborally.game.sound.GameSound;

import java.util.ArrayList;


public class Player extends MovableGameObject implements Comparable {
    private static final int MAX_DAMAGE = 9;
    private static final int MAX_LIVES = 3;

    int phase = 0;

    private String name;
    private int lives;
    private int damage;
    private Backup backup;
    private ArrayList<ProgramCard> cardsInHand;
    private ProgramRegisters registers;
    private Board board;
    private ArrayList<TextureRegion> regions;
    public PlayerState playerState;

    public boolean wantsToPowerDown;

    private int nSounds;
    private GameSound[] allPlayerSounds;

    //flags:
    private int targetFlag;
    private int nFlags;


    public Player(String name, String filepath, Direction direction, Board board) {
        super(0, 0, filepath);

        this.name = name;
        this.board = board;
        setDirection(direction);
        makeSprite();
        loadVisualRepresentation();

        damage = 0;
        lives = MAX_LIVES;

        targetFlag = 1;
        nFlags = board.getFlags().size();

        backup = new Backup(getX(), getY(), this);
        registers = new ProgramRegisters(this);
        cardsInHand = new ArrayList<>();

        playerState = PlayerState.OPERATIONAL;

        // As for now, we have 3 sounds
        nSounds = 3;
        allPlayerSounds = new GameSound[nSounds];
        createSounds();
    }

    public void createSounds() {
        allPlayerSounds[0] = new GameSound("assets/music/playerLaser.wav");
        allPlayerSounds[1] = new GameSound("assets/music/playerRepair.wav");
        allPlayerSounds[2] = new GameSound("assets/music/playerWilhelmScream.wav");
    }

    @Override
    public void makeSprite() {
        super.makeSprite();
        regions = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            regions.add(new TextureRegion(getSprite().getTexture(), 32 * 8 * i, 0, 32 * 8, 48 * 8));
        }
        sprite.setRegion(regions.get(0));
    }

    /**
     * FOR TESTING ONLY
     */
    public Player(int x, int y, int nFlags) {
        super(x, y, "assets/robot/tvBot.png");
        damage = 0;
        lives = MAX_LIVES;
        registers = new ProgramRegisters(this);
        cardsInHand = new ArrayList<>();


        // For testing with flag behaviour, now tests with 3 flags on the board
        targetFlag = 1;
        this.nFlags = nFlags;

    }

    public void executeCard(int phase) {
        this.phase = phase;
        ProgramCard programCard = registers.getCard(phase);
        if (programCard == null || !isOperational()) return;

        if (programCard.getRotate() != Rotate.NONE) {
            rotate(programCard.getRotate());
            return;
        }

        if (programCard.getMoveDistance() == -1) {
            if (board.canGo(this, getDirection().getOppositeDirection())
                    && board.canPush(this, getDirection().getOppositeDirection())) {
                moveInDirection(getDirection().getOppositeDirection());
            }
        }
        for (int i = 0; i < programCard.getMoveDistance(); i++) {
            if (board.canGo(this, getDirection()) && board.canPush(this, getDirection())) {
                move(1);
            }
        }
    }

    @Override
    public void move(int steps) {
        for (int i = 0; i < steps; i++) {
            moveInDirection(getDirection());
        }
        // every time a player moves we need to check if it is off the board or not
        if (board != null && board.isOffTheBoard(this)) { // need to check if board is null for tests to work
            this.destroy();
        }
    }


    public void receiveCard(ProgramCard programCard) {
        if (programCard == null) {
            throw new NullPointerException("Trying to add a programCard that has value null");
        }
        cardsInHand.add(programCard);
    }

    public ProgramCard removeCardInHand(int cardPos) {
        if (cardPos < 0 || cardPos >= cardsInHand.size()) {
            throw new IndexOutOfBoundsException("Trying to remove index: " + cardPos
                    + ", but number of cards in hand: " + getNumberOfCardsInHand());
        }
        return cardsInHand.remove(cardPos);
    }

    public ProgramCard getCardInHand(int cardPos) {
        return cardsInHand.get(cardPos);
    }

    public ArrayList<ProgramCard> getCardsInHand() {
        return cardsInHand;
    }

    public int getNumberOfCardsInHand() {
        return cardsInHand.size();
    }

    public ArrayList<ProgramCard> returnCards() {
        registers.returnCards(this);
        return cardsInHand;
    }

    @Override
    public void updateSprite() {
        if (sprite != null) {
            sprite.setRegion(regions.get(getDirection().toInt()));
            sprite.setPosition(getX() * Main.PIXELS_PER_TILE, getY() * Main.PIXELS_PER_TILE + 5);
        }
    }

    public void update() {
        if (playerState == PlayerState.DESTROYED) return; // Player needs to respawn before it receives updates.

        if (isDestroyed() && !outOfLives()) {
            playerState = PlayerState.DESTROYED;
            if (backup != null) {
                move(-1, -1);
            }
            Gdx.app.log(name, "is destroyed!");
        }
        updateSprite();
    }

    public void respawn() {
        if (!isDestroyed()) return; // Can only respawn dead robots

        lives--;
        if (outOfLives()) {
            System.out.println(name + " is out of the game");
            playerState = PlayerState.GAME_OVER;
        }
        else {
            repairAllDamage();
            if (backup != null) {
                backup.movePlayerToBackup();
            }
            playerState = PlayerState.OPERATIONAL;
        }
    }

    public void powerDown() {
        if (!wantsToPowerDown || !isOperational()) return;

        playerState = PlayerState.POWERED_DOWN;
        System.out.println(name + " powers down");
        wantsToPowerDown = false;
    }

    public void powerUp() {
        if(!isPoweredDown()) return;

        repairAllDamage();
        playerState = PlayerState.OPERATIONAL;
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
     */
    public void takeDamage() {
        if (damage < 10) {
            damage++;
        }
        if (damage >= 5) {
            registers.lock();
        }
    }


    public void visitFlag(int flagNumber) {
        if(flagNumber > nFlags) return;

        if (flagNumber == targetFlag) {
            targetFlag++;
            System.out.printf("%s picked up a flag!%n", name);
        }
    }

    public boolean hasWon() {
        return targetFlag > nFlags;
    }

    public void destroy() {
        damage = MAX_DAMAGE + 1;
    }

    public boolean isDestroyed() {
        return damage > MAX_DAMAGE;
    }

    public boolean isReady() {
        return playerState == PlayerState.POWERED_DOWN || playerState == PlayerState.READY;
    }

    public boolean isOperational(){
        return playerState == PlayerState.OPERATIONAL;
    }

    public boolean isPoweredDown(){
        return playerState == PlayerState.POWERED_DOWN;
    }

    public boolean outOfLives() {
        return lives < 1;
    }

    public boolean handIsFull(){
        return cardsInHand.size() >= ProgramRegisters.MAX_NUMBER_OF_CARDS - damage;
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


    @Override
    public String toString() {
        return getName() + " | Health: " + (10 - damage) + " | Lives: " + lives;
    }

    public GameSound getSoundFromPlayer(int index) {
        return allPlayerSounds[index];
    }

    public void killTheSound() {
        for (GameSound s : allPlayerSounds) {
            s.mute();
        }
        allPlayerSounds = new GameSound[nSounds];
    }

    @Override
    public boolean equals(Object other) {
        if (other.getClass() != this.getClass())
            return false;

        return this.name.equals(((Player) other).name);
    }

    // TODO: TEST THIS
    @Override
    public int compareTo(Object o) {
        Player other = (Player) o;
        int thisPriority = registers.getCard(phase).getPriority();
        int otherPriority = other.getRegisters().getCard(phase).getPriority();
        return ((Integer)thisPriority).compareTo(otherPriority);
    }
}
