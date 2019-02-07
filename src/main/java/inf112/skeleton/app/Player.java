package inf112.skeleton.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import java.util.ArrayList;
import java.util.Iterator;

public class  Player implements IPlayer {


    private String name;
    private int x;
    private int y;
    private int lives;
    private int dmg;

    private float rotationDegree; //Current degrees rotated. Used in GameScreen to rotate the player sprite.
    private Direction direction; //Which direction the player is currently facing.
    private ArrayList<ProgramCard> cardsInHand;

    private ProgramCard[] registers = new ProgramCard[5];
    private int unlockedRegisters;


    private Texture texture;
    private Sprite sprite;


    /**
     * Initialize a new player.
     * The player is facing south by default.
     *
     * @param name the name of the player.
     * @param x    x start coordinate for the player.
     * @param y    y start coordinate for the player.
     */
    public Player(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.direction = Direction.SOUTH;
        this.cardsInHand = new ArrayList<>();

        this.dmg = 0;
        this.lives = 3;
        this.unlockedRegisters = 5;

        this.rotationDegree = 0;

    }


    /**
     * Initialize a new player.
     * Choose which direction the player is facing when created.
     *
     * @param name      the name of the player.
     * @param x         x start coordinate for the player.
     * @param y         y start coordinate for the player.
     * @param direction which direction the player should face.
     */
    public Player(String name, int x, int y, Direction direction) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.cardsInHand = new ArrayList<>();

        this.dmg = 0;
        this.lives = 3;
        this.unlockedRegisters = 5;

        this.rotationDegree = 0;
    }


    public void takeDamage() {
        dmg++;
        if (isDestroyed()) {
            if (outOfLives()) {
                // TODO: GAME OVER for this player.
            } else {
//                repairDamage();
                // TODO: Move to last backup
            }
        } else if (dmg >= 5) {
            unlockedRegisters--;
        }
    }

    /* Registers are stored like this:
     * [0][1][2][3][4]
     *
     * taking 5 dmg locks register 4.
     * 6 dmg locks 3..
     */
    public boolean isLocked(int register) {
        return register >= unlockedRegisters;
    }

    /**
     * When damage is taken some registers might become locked.
     * Cards in locked registers should remain untill the register is unlocked.
     *
     * @return list of cards that are not locked in registers.
     */
    public ArrayList<ProgramCard> returnCards() {
        for (int register = 0; register < unlockedRegisters; register++) {
            cardsInHand.add(registers[register]);
            registers[register] = null;
        }
        return cardsInHand;
    }

    public boolean isDestroyed() {
        return dmg > 9;
    }


    public boolean outOfLives() {
        return lives == 0;
    }

    public void repairDamage() {
        dmg = 0;
        unlockedRegisters = 5;
    }

    /**
     * Give the player a new program card.
     * A player will not receive the card if the card limit is reached.
     */
    public void receiveNewCard(ProgramCard programCard) {
        if (cardsInHand.size() >= getCardLimit()) {
            System.out.println("Card was not added because of card limit");
            return;
        }
        cardsInHand.add(programCard);
    }

    public void pickCard(int cardPos) {
        int i = 0;
        while (i < unlockedRegisters) {
            if (registers[i] == null) {
                registers[i] = cardsInHand.remove(cardPos);
                //TODO: potential issue - array spots after the removed index gets shifted
                return;
            }
            i++;
        }
        System.err.println("Error: can't pick more cards. Register is full!");
    }

    public boolean registerIsFull() {
        return registers[unlockedRegisters] != null;
    }


    public void canGo(Direction dir, TiledMapTileLayer layer){
        //TODO:
    }

    /**
     * Moves the robot forward in the direction it is facing.
     *
     * @param steps number of tiles to move.
     */
    public void move(int steps) {
        switch (direction){
            case NORTH:
                y += GameScreen.MOVE_DIST * steps; break;
            case SOUTH:
                y -= GameScreen.MOVE_DIST * steps; break;
            case EAST:
                x += GameScreen.MOVE_DIST * steps; break;
            case WEST:
                x -= GameScreen.MOVE_DIST * steps; break;
        }
    }

    public void moveDir(Direction dir){
        switch (dir){
            case NORTH:
                y += GameScreen.MOVE_DIST; break;
            case SOUTH:
                y -= GameScreen.MOVE_DIST; break;
            case EAST:
                x += GameScreen.MOVE_DIST; break;
            case WEST:
                x -= GameScreen.MOVE_DIST; break;
        }
    }

    /**
     * Rotates the player - visually too.
     *
     * @param rotateDir which direction the player should rotate.
     * @return the new direction the player is facing.
     */
    @SuppressWarnings("Duplicates")
    public Direction rotate(Rotate rotateDir) {
        final Direction NORTH = Direction.NORTH;
        final Direction WEST = Direction.WEST;
        final Direction SOUTH = Direction.SOUTH;
        final Direction EAST = Direction.EAST;


        if (rotateDir.equals(Rotate.RIGHT)) {
            switch (this.direction) {
                case NORTH: this.direction = EAST; break;
                case EAST: this.direction = SOUTH; break;
                case SOUTH: this.direction = WEST; break;
                case WEST: this.direction = NORTH; break;
            }
            this.rotationDegree += 90;
        }
        else if (rotateDir.equals(Rotate.LEFT)) {
            switch (this.direction) {
                case NORTH: this.direction = WEST; break;
                case WEST: this.direction = SOUTH; break;
                case SOUTH: this.direction = EAST; break;
                case EAST: this.direction = NORTH; break;
            }
            this.rotationDegree -= 90;
        }
        else if (rotateDir.equals(Rotate.UTURN)) {
            switch (this.direction) {
                case NORTH: this.direction = SOUTH; break;
                case SOUTH: this.direction = NORTH; break;
                case WEST:  this.direction = EAST;  break;
                case EAST:  this.direction = WEST;  break;
            }
            this.rotationDegree += 180;
        }
        return this.direction;
    }

    public void loadVisualRepresentation() {
        texture = new Texture(Gdx.files.internal("assets/robot/tvBot.png"));
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        sprite = new Sprite(texture);
        sprite.setSize(sprite.getWidth()/2, sprite.getHeight()/2);
        sprite.setOriginCenter();
        sprite.setPosition(this.x, this.y-10);
        sprite.setRotation(this.rotationDegree);
    }

    // getters and setters:

    /**
     * @return how many cards the player is allowed to be dealt.
     */
    public int getCardLimit() {
        return 9 - dmg;
    }

    public int getUnlockedRegisters() {
        return this.unlockedRegisters;
    }

    /**
     * @return all the cards that the player currently has in hand.
     */
    public ArrayList<ProgramCard> getCardsInHand() {
        return this.cardsInHand;
    }



    /**
     * @return the direction the player is facing.
     */
    public Direction getDirection() {
        return this.direction;
    }


    public int getDamage() {
        return dmg;
    }


    public int getLives() {
        return this.lives;
    }

    public ArrayList<ProgramCard> getRegisters() {
        ArrayList<ProgramCard> list = new ArrayList<>();
        for (ProgramCard pc : registers)
            list.add(pc);

        return list;
    }

    public ProgramCard getCardInRegister(int register) {
        return registers[register];
    }

    /**
     * Every phase the player with the highest priority goes first.
     *
     * @param phaseNumber
     * @return priority of the card in the register for the given phase.
     */
    public int getPriority(int phaseNumber) {
        return registers[phaseNumber].getPriority();
    }

    public float getRotationDegree() {
        return rotationDegree;
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return getName() + " | Health: " + (10 - dmg) + " | Lives: " + lives;
    }

    public Texture getTexture() {
        return texture;
    }

    public Sprite getSprite() {
        return sprite;
    }
}
