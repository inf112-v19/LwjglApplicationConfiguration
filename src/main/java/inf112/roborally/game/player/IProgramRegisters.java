package inf112.roborally.game.player;

public interface IProgramRegisters {

    /**
     * Places a card in the next available slot.
     * @return if the card was placed, return the index, if not, return -1.
     */
    int placeCard(int index);

    /**
     * get a card from specified slot.
     * @param index
     * @return
     */
     ProgramCard getCard(int index);


    /**
     * @return false if any slot contains null, else true
     */
    boolean isFull();

    /**
     * locks the next slot
     */
    void lock();

    /**
     * checks if a slot is locked
     * @param index
     * @return
     */
    boolean isLocked(int index);

    /**
     * unlocks the previous slot
     */
    void unlock();

    /**
     * unlocks all slots
     */
    void unlockAll();
}
