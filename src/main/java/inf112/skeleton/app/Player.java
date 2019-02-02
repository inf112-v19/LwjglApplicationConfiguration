package inf112.skeleton.app;

import java.util.ArrayList;

public class Player {

    private String name;
    private int x;
    private int y;

    //Which direction the player is currently facing.
    private Direction direction;

    //All cards in the player hand.
    private ArrayList<ProgramCardCD> cardDeck;

    private ArrayList<ProgramCardCD> chosenCards;

    //How many cards you have at hand to pick from
    private int cardLimit;

    //How many cards you can choose
    private int chosenCardsLimit;

    /**
     * Initialize a new player (a new robot).
     * The player is facing south by default.
     *
     * @param name
     *            the name of the player.
     * @param x
     *          x start coordinate for the player.
     * @param y
     *          y start coordinate for the player.
     */
    public Player(String name, int x, int y){
        this.name = name;
        this.x = x;
        this.y = y;
        this.direction = Direction.SOUTH;
        this.cardDeck = new ArrayList<>();
        this.cardLimit = 9;
        this.chosenCardsLimit = 5;

    }

    /**
     * Initialize a new player (a new robot).
     * Choose which direction the player is facing at spawn.
     *
     * @param name
     *              the name of the player
     * @param x
     *          x start coordinate for the player.
     * @param y
     *          y start coordinate for the player.
     * @param direction
     *                  which direction the player should face.
     */
    public Player(String name, int x, int y, Direction direction){
        this.name = name;
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.cardDeck = new ArrayList<>();
        this.cardLimit = 9;
        this.chosenCardsLimit = 5;

    }

    public void chooseCard(int cardID){
        if(chosenCards.size() >= chosenCardsLimit){
            System.out.println("Already have maximum amount of cards chosen");
            return;
        }
        chosenCards.add(cardDeck.get(cardID));
    }

    /**
     *
     * @return
     *          how many cards the player can choose to use this round
     */
    public int getChosenCardsLimit(){
        return this.chosenCardsLimit;
    }

    /**
     *
     * @return
     *          how many cards the player is allowed to be dealt
     */
    public int getCardLimit(){
        return this.cardLimit;
    }

    /**
     *
     * @return
     *          the cards chosen this round
     */
    public ArrayList<ProgramCardCD> getChosenCards(){
        return this.chosenCards;
    }

    /**
     *
     * @return
     *          all the cards that the player currently has in hand.
     */
    public ArrayList<ProgramCardCD> getCardDeck(){
        return this.cardDeck;
    }

    /**
     * Give the player a new program card.
     * A player will not receive the card if the card limit is reached.
     */
    public void receiveNewCard(ProgramCardCD programCard){
        if(cardDeck.size() >= cardLimit){
            System.out.println("Card was not added because of card limit");
            return;
        }
        cardDeck.add(programCard);
    }

    /**
     *
     * @return
     *          the direction the player is facing.
     */
    public Direction getDirection(){
        return this.direction;
    }

    /**
     * Change the direction that the player is facing.
     *
     * Rotation is possible using one of the enums in Rotate.java.
     * Program cards should use this function, not the function changeDirection(Direction direction).
     *
     * @param rotate
     *              which direction the player should rotate
     */
    public void rotate(Rotate rotate){
        if(rotate.equals(Rotate.LEFT)){
            if(direction.equals(Direction.NORTH)){
                this.direction = Direction.WEST;
            }
            else if(direction.equals(Direction.WEST)){
                this.direction = Direction.SOUTH;
            }
            else if(direction.equals(Direction.SOUTH)){
                this.direction = Direction.EAST;
            }
            else if(direction.equals(Direction.EAST)){
                this.direction = Direction.NORTH;
            }
        }

        else if(rotate.equals(Rotate.RIGHT)){
            if(direction.equals(Direction.NORTH)){
                this.direction = Direction.EAST;
            }
            else if(direction.equals(Direction.EAST)){
                this.direction = Direction.SOUTH;
            }
            else if(direction.equals(Direction.SOUTH)){
                this.direction = Direction.WEST;
            }
            else if(direction.equals(Direction.WEST)){
                this.direction = Direction.NORTH;
            }
        }

        else if(rotate.equals(Rotate.UTURN)){
            if(direction.equals(Direction.NORTH)){
                this.direction = Direction.SOUTH;
            }
            else if(direction.equals(Direction.SOUTH)){
                this.direction = Direction.NORTH;
            }
            else if(direction.equals(Direction.WEST)){
                this.direction = Direction.EAST;
            }
            else if(direction.equals(Direction.EAST)){
                this.direction = Direction.WEST;
            }
        }
    }

    /**
     * Change the direction the player is facing.
     * Uses enums Direction.NORTH, Direction.WEST, Direction.SOUTH and Direction.EAST
     *
     * You should use rotate(Rotate rotate) if using program cards.
     *
     * @param direction
     *                  new direction to face.
     */
    //Might want to make private
    public void changeDirection(Direction direction){
        this.direction = direction;

        //TODO: update sprite to show the new direction
    }

    /**
     * Moves the robot forward in the direction it is facing.
     * @param steps
     *              how many tiles to move.
     */
    public void move(int steps){
        if(direction.equals(Direction.NORTH)) {
            y -= 150 * steps;
        }
        if(direction.equals(Direction.EAST)) {
            x += 150 * steps;
        }
        if(direction.equals(Direction.SOUTH)) {
            y += 150 * steps;
        }
        if(direction.equals(Direction.WEST)) {
            x -= 150 * steps;
        }
        //Texture/sprite is updated automatically because of render method in GameScreen.java.
    }

    public String getName() {
        return name;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
