package inf112.skeleton.app.GameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import inf112.skeleton.app.*;
import inf112.skeleton.app.GameWorld.Direction;

import java.util.ArrayList;

public class  Player extends MoveableGameObject {
    private static final int MAX_DAMAGE = 9;
    private static final int MAX_LIVES = 3;
    private static final int NUMBER_OF_REGISTERS = 5;

    private String name;
    private int lives;
    private int damage;
    private int unlockedRegisters;
    private ProgramCard[] registers;
    private Backup backup;
    private ArrayList<ProgramCard> cardsInHand;
    private Sprite sprite;


    public Player(String name, int x, int y, Direction direction) {
        super(x, y);
        this.name = name;
        backup = new Backup(x, y);
        sprite = new Sprite(new Texture("assets/robot/tvBot.png"));

        registers = new ProgramCard[NUMBER_OF_REGISTERS];
        unlockedRegisters = NUMBER_OF_REGISTERS;
        cardsInHand = new ArrayList<>();
        damage = 0;
        lives = MAX_LIVES;
        setDirection(direction);

        loadVisualRepresentation();
    }

    /** FOR TESTING ONLY */
    public Player(int x, int y){
        super(x, y);
        registers = new ProgramCard[NUMBER_OF_REGISTERS];
        unlockedRegisters = registers.length;
        cardsInHand = new ArrayList<>();
        damage = 0;
        lives = MAX_LIVES;
    }

    public void loadVisualRepresentation() {
        sprite.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        sprite.setSize(Main.TILE_LENGTH, Main.TILE_LENGTH);
        sprite.setOriginCenter();
        sprite.setRotation(rotationDegree);

        updateSprite();
    }

    private void updateSprite() {
        sprite.setPosition(getX(), getY());
    }

    public void update(){

        handleInput();
        if (isDestroyed() && !outOfLives()){
            Gdx.app.log("Player", "is destroyed!");
            lives--;
            repairDamage();
            backup.movePlayer(this);
            }
        else if (isDestroyed() && outOfLives()){
            Gdx.app.log("Player", "is dead!");
            Gdx.app.log("GAME OVER", "");
        }
        updateSprite();
        backup.updateSprite();
    }

    public void handleInput() {
        //Just for testing
        moved = true;

        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) || (Gdx.input.isKeyJustPressed(Input.Keys.D))) {
            setDirection(Direction.EAST);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT) || (Gdx.input.isKeyJustPressed(Input.Keys.A))) {
            setDirection(Direction.WEST);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.UP) || (Gdx.input.isKeyJustPressed(Input.Keys.W))) {
            setDirection(Direction.NORTH);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN) || (Gdx.input.isKeyJustPressed(Input.Keys.S))) {
            setDirection(Direction.SOUTH);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.B)) {
            backup.move(getX(), getY());
            moved = false;
        } else {
            moved = false;
        }
    }

    public void takeDamage() {
        this.damage++;
        if (damage < 10 && damage >= 5) {
            unlockedRegisters--;
        }
    }

    public void destroy(){
        damage = MAX_DAMAGE +1;
    }

    public boolean isDestroyed() {
        return damage > MAX_DAMAGE;
    }

    public boolean outOfLives() {
        return lives <= 0;
    }



    /* Registers are stored like this:
     * [0][1][2][3][4]
     *
     * taking 5 damage locks register 4.
     * 6 damage locks 3..
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
        for (int i = 0; i < unlockedRegisters; i++) {
            cardsInHand.add(registers[i]);
            registers[i] = null;
        }
        return cardsInHand;
    }

    public void repairDamage() {
        damage = 0;
        unlockedRegisters = NUMBER_OF_REGISTERS;
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

    // getters and setters:

    /**
     * @return how many cards the player is allowed to be dealt.
     */
    public int getCardLimit() {
        return 9 - damage;
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

    public int getDamage() {
        return damage;
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

    public String getName() {
        return name;
    }

    public GameObject getBackup() {
        return backup;
    }


    @Override
    public String toString() {
        return getName() + " | Health: " + (10 - damage) + " | Lives: " + lives;
    }

    @Override
    public Sprite getSprite() {
        return sprite;
    }
}
