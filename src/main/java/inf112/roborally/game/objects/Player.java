package inf112.roborally.game.objects;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import inf112.roborally.game.board.ProgramCard;
import inf112.roborally.game.board.ProgramRegisters;
import inf112.roborally.game.enums.Direction;
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
    private Sound laserHitPlayerSound;


    public Player(String name, int x, int y, Direction direction, int numberOfFlagsOnBoards) {
        super(x, y, "assets/robot/tvBot.png");
        this.name = name;

        damage = 0;
        lives = MAX_LIVES;

        flagCounter = 0;
        flagsFound = new boolean [numberOfFlagsOnBoards];

        setDirection(direction);
        makeSprite();
        loadVisualRepresentation();

        backup = new Backup(getPos());
        registers = new ProgramRegisters(this);
        cardsInHand = new ArrayList<>();

        laserHitPlayerSound = Gdx.audio.newSound(Gdx.files.internal("assets/music/playerLaser.wav"));
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
        flagsFound = new boolean [3];
    }


    public void receiveCard(ProgramCard programCard) {
        if(programCard == null){
            throw new NullPointerException("Trying to add a programCard that has value null");
        }
        cardsInHand.add(programCard);
    }

    public ProgramCard removeCardInHand(int cardPos){
        if(cardPos < 0 || cardPos >= cardsInHand.size()) {
            throw new IndexOutOfBoundsException("Trying to remove index: " + cardPos
                    + ", but number of cards in hand: " + getNumberOfCardsInHand());
        }
       return cardsInHand.remove(cardPos);
    }

    public ProgramCard getCardInHand(int cardPos){
        return cardsInHand.get(cardPos);
    }

    public ArrayList<ProgramCard> getCardsInHand(){
        return cardsInHand;
    }

    public int getNumberOfCardsInHand(){
        return cardsInHand.size();
    }

    //Remove?
    public void putCardIntoRegister(int cardPos){
        registers.pickCard(cardPos);
    }

    public ArrayList<ProgramCard> returnCards(){
        registers.returnCardsFromRegisters(cardsInHand);
        return cardsInHand;
    }

    @Override
    public void updateSprite() {
        boolean flipSprite = false;
        switch (getDirection()) {
            case SOUTH:
                flipSprite = true;
            case NORTH:
                rotationDegree = 270;
                break;
            case WEST:
                flipSprite = true;
            case EAST:
                rotationDegree = 180;
                break;
        }

        if(sprite != null) {
            sprite.setFlip(flipSprite, false);
            super.updateSprite();
        }
    }


    public void moveBackupToPlayerPosition(){
        backup.move(getX(), getY());
        backup.updateSprite();
    }

    public void update() {
        if (isDestroyed() && !outOfLives()) {
            Gdx.app.log("Player", "is destroyed!");
            lives--;
            repairAllDamage();
            if(backup != null)
                backup.movePlayer(this);
        } else if (isDestroyed() && outOfLives()) {
            Gdx.app.log("Player", "is dead!");
            Gdx.app.log("GAME OVER", "");
        }
        updateSprite();
    }


   /* public void execute(int spotInRegister) {
        if (spotInRegister < 0 || spotInRegister >= registers.NUMBER_OF_REGISTERS) {
            System.out.println(spotInRegister + " is not between 0 and " + (registers.NUMBER_OF_REGISTERS - 1));
            return;
        }
        else if(!registerIsFull()){
            System.out.println("Need to fill your register before executing!");
            return;
        }

        ProgramCard cardToExecute = registers.getCardInRegister(spotInRegister);
        System.out.println("Card in reg " + spotInRegister + ": "  + cardToExecute.toString());
        if (cardToExecute.getRotate() == Rotate.NONE) {
            move(cardToExecute.getMoveDistance());
        } else {
            setDirection(getDirection().rotate(cardToExecute.getRotate()));
        }
    }

    public void execute(ProgramCard programCard) {
        if (programCard.getRotate() == Rotate.NONE) {
            move(programCard.getMoveDistance());
        } else {
            setDirection(getDirection().rotate(programCard.getRotate()));
        }
    }
*/

    /**
     * Repairs all damage dealt to the player and unlocks all locked registers.
     */
    public void repairAllDamage() {
        registers.unlockRegisters();
        damage = 0;
    }

    public void repairOneDamage() {
        if(damage > 0) {
            damage--;
        }
    }

    /**
     * Take one damage. Locks a register if damage taken is greater or equal to 5.
     */
    public void takeDamage() {
        if(damage < 10) {
            damage++;
        }
        if (damage >= 5) {
            registers.lockRegister();
        }
    }

    public void updateBackup() {
        this.backup = new Backup(getPos());
    }

    // TODO Make is so you need to get the flags in order
    public void addFlag(int flagNumber) {
        if(flagNumber > flagsFound.length) {
            // If the flagnumber is greater than the array length, do nothing
            ;
        }
        // you need to pick up the flags in order, so first check if the flag you are standing on
        // is your next flag
        else if(flagNumber-1 <= flagCounter && !flagsFound[flagNumber-1]) {
            flagsFound[flagNumber-1] = true;
            flagCounter++;
            System.out.printf("%s picked up a flag!%n", name);
        }
    }

    // Iterates over the booleanArray which keeps track of how many  of the flags
    // have been found. If one of the array positions is false, then
    // this will return false
    public boolean thisPlayerHasWon() {
        for(boolean found : flagsFound) {
            if(!found) {
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

    public GameObject getBackup() {
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

    public Sound getLaserHitPlayerSound() { return this.laserHitPlayerSound; }
}
