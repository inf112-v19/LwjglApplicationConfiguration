package inf112.skeleton.app;

import java.util.ArrayList;

public interface IPlayer {

    String getName();

    /**
     * @return current x position.
     */
    int getX();

    /**
     * @return current y position.
     */
    int getY();

    /**
     * @return the current direction the player is facing.
     */
    Direction getDirection();


    /**
     * Move forward the direction the player is facing.
     *
     * @param steps how many tiles to move forward.
     */
    void move(int steps);


    /**
     * @return all cards dealt to the player this round.
     */
    ArrayList<ProgramCardCD> getCardsInHand();


    /**
     * (Use-case: when the game is giving a player a new card from the game deck)
     *
     * @param programCard the card the player receives.
     */
    void receiveNewCard(ProgramCardCD programCard);

    /**
     * @return how many cards the player is allowed to choose this round.
     */
    int getUnlockedRegisters();

    /**
     * @return how many cards the player is allowed to be dealt this round.
     */
    int getCardLimit();

}

