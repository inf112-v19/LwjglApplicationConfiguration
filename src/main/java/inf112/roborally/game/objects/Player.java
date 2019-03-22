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


public class Player extends MovableGameObject {
    private static final int MAX_DAMAGE = 9;
    private static final int MAX_LIVES = 3;

    private String name;
    private int lives;
    private int damage;
    private Backup backup;
    private ArrayList<ProgramCard> cardsInHand;
    private ProgramRegisters registers;
    private int flagCounter;
    private boolean[] flagsFound;
    private Board board;
    private ArrayList<TextureRegion> regions;
    public PlayerState playerState;
    private GameSound laserHitPlayerSound;


    public Player(String name, String filepath, Direction direction, Board board) {
        super(0, 0, filepath);

        this.name = name;
        this.board = board;
        setDirection(direction);
        makeSprite();
        loadVisualRepresentation();

        damage = 0;
        lives = MAX_LIVES;

        flagCounter = 0;
        flagsFound = new boolean[board.getFlags().size()];


        backup = new Backup(getX(), getY(), this);
        registers = new ProgramRegisters(this);
        cardsInHand = new ArrayList<>();

        playerState = PlayerState.PICKING_CARDS;
    }

    @Override
    public void makeSprite() {
        super.makeSprite();
        regions = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            regions.add(new TextureRegion(getSprite().getTexture(), 32 * 8 * i, 0, 32 * 8, 48 * 8));
        }
        sprite.setRegion(regions.get(0));
        laserHitPlayerSound = new GameSound("assets/music/playerLaser.wav");
    }

    /**
     * FOR TESTING ONLY
     */
    public Player(int x, int y) {
        super(x, y, "assets/robot/tvBot.png");
        damage = 0;
        lives = MAX_LIVES;
        registers = new ProgramRegisters(this);
        cardsInHand = new ArrayList<>();


        // For testing with flag behaviour, now tests with 3 flags on the board
        flagCounter = 0;
//        backup = new Backup(x,y);
        flagsFound = new boolean[3];
    }

    public void executeCard(ProgramCard programCard) {
        if (programCard == null) {
            return;
        }

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
        registers.returnCards(cardsInHand);
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
        if (isDestroyed() && !outOfLives()) {
            Gdx.app.log("Player", "is destroyed!");
            lives--;
            repairAllDamage();
            if (backup != null)
                backup.movePlayerToBackup();
        }
        else if (isDestroyed() && outOfLives()) {
            Gdx.app.log("Player", "is dead!");
            Gdx.app.log("GAME OVER", "");
        }
        updateSprite();
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
        if (flagNumber > flagsFound.length) return;

        // you need to pick up the flags in order, so first check if the flag you are standing on
        // is your next flag
        if (flagNumber - 1 <= flagCounter && !flagsFound[flagNumber - 1]) {
            flagsFound[flagNumber - 1] = true;
            flagCounter++;
            System.out.printf("%s picked up a flag!%n", name);
        }
    }

    // Iterates over the booleanArray which keeps track of how many  of the flags
    // have been found. If one of the array positions is false, then
    // this will return false
    public boolean thisPlayerHasWon() {
        for (boolean found : flagsFound) {
            if (!found) {
                return false;
            }
        }
        return true;
    }

    public void destroy() {
        damage = MAX_DAMAGE + 1;
    }

    public boolean isDestroyed() {
        return damage > MAX_DAMAGE;
    }

    public boolean outOfLives() {
        return lives == 0;
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

    public int getFlagCounter() {
        return this.flagCounter;
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

    public boolean[] getFlagsFound() {
        return this.flagsFound;
    }


    @Override
    public String toString() {
        return getName() + " | Health: " + (10 - damage) + " | Lives: " + lives;
    }

    public GameSound getLaserHitPlayerSound() { return this.laserHitPlayerSound; }

    public void killTheSound() {
        laserHitPlayerSound.mute();
    }

    @Override
    public boolean equals(Object other) {
        if (other.getClass() != this.getClass())
            return false;

        return this.name.equals(((Player) other).name);
    }
}
