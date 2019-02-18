package inf112.skeleton.app.GameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import inf112.skeleton.app.*;
import inf112.skeleton.app.GameWorld.Board;
import inf112.skeleton.app.GameWorld.Direction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class  Player implements IPlayer {


    public static boolean playerMoved;
    private String name;
    private int x;
    private int y;
    private int lives;
    private int damage;

    private float rotationDegree;
    private Direction direction;
    private ArrayList<ProgramCard> cardsInHand;

    private ProgramCard[] registers = new ProgramCard[5];
    private int unlockedRegisters;


    private Texture texture;
    private Sprite sprite;

    private Board board;


    /**
     * Initialize a new player.
     * Choose which direction the player is facing when created.
     *
     * @param name      the name of the player.
     * @param x         x start coordinate for the player.
     * @param y         y start coordinate for the player.
     * @param direction which direction the player should face.
     */
    public Player(String name, int x, int y, Direction direction, Board board) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.cardsInHand = new ArrayList<>();

        this.damage = 0;
        this.lives = 3;
        this.unlockedRegisters = 5;

        this.rotationDegree = 180;

        this.board = board;
    }
    public void update(){
            //Just for testing
            playerMoved = true;

            if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) || (Gdx.input.isKeyJustPressed(Input.Keys.D))){
                direction = Direction.EAST;
            }
            else if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT) || (Gdx.input.isKeyJustPressed(Input.Keys.A))){
                direction = Direction.WEST;
            }
            else if (Gdx.input.isKeyJustPressed(Input.Keys.UP) || (Gdx.input.isKeyJustPressed(Input.Keys.W))){
                direction = Direction.NORTH;
            }
            else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN) || (Gdx.input.isKeyJustPressed(Input.Keys.S))){
                direction = Direction.SOUTH;
            }
            else {
                playerMoved = false;
            }
        }

        @SuppressWarnings("Duplicates")
    public boolean canGo(Direction dir){
        // first check the current tile:
        int x = (this.x) / Main.MOVE_DIST;
        int y = (this.y) / Main.MOVE_DIST;
        TiledMapTileLayer.Cell currentCell =  board.getWallLayer().getCell(x,y);
        List<String> walls = new ArrayList<>();

        if(currentCell != null && currentCell.getTile().getProperties().containsKey("Wall")) {
            walls = splitBySpace(currentCell.getTile().getProperties().getValues().next().toString());
        }
        if (walls.contains(dir.toString())){
            System.out.println("Hit a wall!(here)");
            return false;
        }

        // then check target tile:
        switch (dir){
            case NORTH:
                y++; break;
            case SOUTH:
                y--; break;
            case WEST:
                x--; break;
            case EAST:
                x++; break;
        }

        walls = new ArrayList<>();
        TiledMapTileLayer.Cell targetCell = board.getWallLayer().getCell(x,y);

        if(targetCell != null && targetCell.getTile().getProperties().containsKey("Wall")) {
            walls = splitBySpace(targetCell.getTile().getProperties().getValues().next().toString());
        }

        dir = dir.getOppositeDirection();

        if (walls.contains(dir.toString())){
            System.out.println("Hit a wall!(next)");
            return false;
        }
        return true;
    }

    public List<String> splitBySpace(String strToSplit){
        List<String> splitList;
        String [] items = strToSplit.split(" ");
        splitList = Arrays.asList(items);
        return splitList;
    }

    public void takeDamage() {
        this.damage++;
        if (isDestroyed()) {
            if (outOfLives()) {
                // TODO: GAME OVER for this player.
            } else {
//                repairDamage();
                // TODO: Move to last backup
            }
        } else if (damage >= 5) {
            unlockedRegisters--;
        }
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
        for (int register = 0; register < unlockedRegisters; register++) {
            cardsInHand.add(registers[register]);
            registers[register] = null;
        }
        return cardsInHand;
    }

    public boolean isDestroyed() {
        return damage > 9;
    }


    public boolean outOfLives() {
        return lives == 0;
    }

    public void repairDamage() {
        damage = 0;
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


    /**
     * Moves the robot forward in the direction it is facing.
     *
     * @param steps number of tiles to move.
     */
    public void move(int steps) {
        switch (direction){
            case NORTH:
                y += Main.MOVE_DIST * steps; break;
            case SOUTH:
                y -= Main.MOVE_DIST * steps; break;
            case EAST:
                x += Main.MOVE_DIST * steps; break;
            case WEST:
                x -= Main.MOVE_DIST * steps; break;
        }
    }

    public void moveInDirection(Direction dir){
        switch (dir){
            case NORTH:
                y += Main.MOVE_DIST; break;
            case SOUTH:
                y -= Main.MOVE_DIST; break;
            case EAST:
                x += Main.MOVE_DIST; break;
            case WEST:
                x -= Main.MOVE_DIST; break;
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



    /**
     * @return the direction the player is facing.
     */
    public Direction getDirection() {
        return this.direction;
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

    /**
     * @return current degrees rotated.
     */
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

    //Remove once we remove movement with WASD in GameScreen render method.
    public void setX(int x) {
        this.x = x;
    }

    //Remove once we remove movement with WASD in GameScreen render method.
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return getName() + " | Health: " + (10 - damage) + " | Lives: " + lives;
    }

    public Texture getTexture() {
        return texture;
    }

    public Sprite getSprite() {
        return sprite;
    }
}
