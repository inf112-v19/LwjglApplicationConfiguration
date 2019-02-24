package inf112.roborally.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import inf112.roborally.game.ProgramCard;
import inf112.roborally.game.world.Direction;

import java.util.ArrayList;

public class  Player extends MovableGameObject implements IPlayer{
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


    public Player(String name, int x, int y, Direction direction) {
        super(x, y, "assets/robot/tvBot.png");
        setDirection(direction);
        makeSprite();

        this.name = name;
        backup = new Backup(x, y);

        registers = new ProgramCard[NUMBER_OF_REGISTERS];
        unlockedRegisters = NUMBER_OF_REGISTERS;
        cardsInHand = new ArrayList<>();
        damage = 0;
        lives = MAX_LIVES;

        loadVisualRepresentation();
    }

    /** FOR TESTING ONLY */
    public Player(int x, int y){
        super(x, y, "assets/robot/tvBot.png");
        registers = new ProgramCard[NUMBER_OF_REGISTERS];
        unlockedRegisters = registers.length;
        cardsInHand = new ArrayList<>();
        damage = 0;
        lives = MAX_LIVES;
    }

    @Override
    public void updateSprite() {
        boolean flipSprite = false;
        switch (getDirection()){
            case SOUTH:
                flipSprite = true;
            case NORTH:
                rotationDegree = 270; break;
            case WEST:
                flipSprite = true;
            case EAST:
                rotationDegree = 180;
                break;
        }
        sprite.setFlip(flipSprite, false);
        super.updateSprite();
    }


    public void update(){
        handleInput();

        if (isDestroyed() && !outOfLives()){
            Gdx.app.log("Player", "is destroyed!");
            lives--;
            repairAllDamage();
            backup.movePlayer(this);
            }
        else if (isDestroyed() && outOfLives()){
            Gdx.app.log("Player", "is dead!");
            Gdx.app.log("GAME OVER", "");
        }
        updateSprite();
    }

    /*
     Takes in an int which corresponds to the spot in the register.
     That way we don't need to remove the card from the player in another class and put it back in through
     another method. Keep?
    */
    public void execute(int spotInRegister){
        if (spotInRegister < 0 || spotInRegister >= NUMBER_OF_REGISTERS){
            System.out.println(spotInRegister + " is not between 0 and " + (NUMBER_OF_REGISTERS-1));
            return;
        }
        /*else if(!registerIsFull()){
            System.out.println("Need to fill your register before executing!");
            return;
        }*/

        ProgramCard cardToExecute = registers[spotInRegister];
        System.out.println("Card in reg: " + cardToExecute.toString());
        if(cardToExecute.getRotate() == Rotate.NONE){
            move(cardToExecute.getMoveDistance());
        }
        else{
            setDirection(getDirection().rotate(cardToExecute.getRotate()));
        }
    }

    public void execute(ProgramCard programCard){
        if(programCard.getRotate() == Rotate.NONE){
            move(programCard.getMoveDistance());
        }
        else {
            setDirection(getDirection().rotate(programCard.getRotate()));
        }
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
            backup.updateSprite();
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

    public boolean isLocked(int register) {
        return register >= unlockedRegisters;
    }

    /**
     * When damage is taken some registers might become locked.
     * Cards in locked registers should remain untill the register is unlocked.
     *
     * @return list of cards that are not locked in registers.
     */
    public ArrayList<ProgramCard> returnCardsToHand() {
        for (int i = 0; i < unlockedRegisters; i++) {
            if(registers[i] != null) {
                cardsInHand.add(registers[i]);
                registers[i] = null;
            }
        }
        return cardsInHand;
    }

    public void repairAllDamage() {
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

    //Made it return i so that we can know which spot the newly card is inserted at. Returns -1 if not.
    //Is there a better way?
    public int pickCard(int cardPos) {
        int i = 0;
        while (i < unlockedRegisters) {
            if (registers[i] == null) {
                registers[i] = cardsInHand.remove(cardPos);
                //TODO: potential issue - array spots after the removed index gets shifted
                return i;
            }
            i++;
        }
        System.err.println("Error: can't pick more cards. Register is full!");
        return -1; //TODO: Better way?
    }

    public boolean registerIsFull() {
        for(int i = 0; i < registers.length; i++){
            if(registers[i] == null)
                return  false;
        }
        return true;
    }

    // getters and setters:

    /**
     * @return how many cards the player is allowed to be dealt.
     */
    public int getCardLimit() {
        return 9 - this.damage;
    }

    public int getDamage() {
        return this.damage;
    }


    public int getLives() {
        return this.lives;
    }

    public ArrayList<ProgramCard> getCardsInRegisters() {
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

    @Override
    public ArrayList<ProgramCard> getCardsInHand() {
        return cardsInHand;
    }

    public int getNumberOfCardsInHand(){
        return this.cardsInHand.size();
    }

    public GameObject getBackup() {
        return backup;
    }

    @Override
    public String toString() {
        return getName() + " | Health: " + (10 - damage) + " | Lives: " + lives;
    }
}
