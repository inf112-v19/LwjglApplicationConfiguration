package inf112.roborally.game.board;

import java.util.List;

public interface IProgramRegisters {

    /**
     * Places a card in the next available slot.
     * @return if the card was placed, return the index, if not, return -1.
     */
    public int placeCard(int index);

    /**
     * get a card from specified slot.
     * @param index
     * @return
     */
    public ProgramCard getCard(int index);

    /**
     * Remove and return all unlocked cards
     * @return
     */
    public void returnCards(List<ProgramCard> list);

    /**
     * @return false if any slot contains null, else true
     */
    public boolean isFull();

    /**
     * locks the next slot
     */
    public void lock();

    /**
     * checks if a slot is locked
     * @param index
     * @return
     */
    public boolean isLocked(int index);

    /**
     * unlocks the previous slot
     */
    public void unlock();

    /**
     * unlocks all slots
     */
    public void unlockAll();
}
