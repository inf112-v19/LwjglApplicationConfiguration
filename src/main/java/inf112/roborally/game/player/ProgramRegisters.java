package inf112.roborally.game.player;

import inf112.roborally.game.board.ProgramCard;

import java.util.ArrayList;

public class ProgramRegisters implements IProgramRegisters {
    public static final int NUMBER_OF_REGISTERS = 5;
    public static final int MAX_NUMBER_OF_CARDS = 9;

    private final Player player;

    private int unlockedRegisters;
    private ProgramCard[] registers;


    public ProgramRegisters(Player player) {
        this.player = player;
        registers = new ProgramCard[NUMBER_OF_REGISTERS];
        unlockedRegisters = NUMBER_OF_REGISTERS;
    }

    /**
     * Picks a card from from the player hand and puts it into the register.
     *
     * @param cardPosition the position of the card in hand
     * @return index at which it is stored if adding a card to the register was successful, -1 if not.
     */
    @Override
    public int placeCard(int cardPosition) {
        if (cardPosition < 0 || cardPosition >= player.getHand().size()) {
            throw new IndexOutOfBoundsException(
                            "Card position: "
                            + cardPosition
                            + "\n Number of cards in hand: "
                            + player.getHand().size()
                            + "\n Player name: "
                            + player.getName()
                            + "\n Player damage: "
                            + player.getDamage()
                            + "\n Player lives: "
                            + player.getLives());
        }

        for (int i = 0; i < unlockedRegisters; i++) {
            if (registers[i] == null) {
                registers[i] = player.getHand().removeCard(cardPosition);
                return i;
            }
        }
        System.out.println("Registers are full");
        return -1;
    }

    public boolean isFull() {
        for (int i = 0; i < NUMBER_OF_REGISTERS; i++) {
            if (registers[i] == null) {
                return false;
            }
        }
        return true;
    }

    /**
     * Executes a card in the register according to current phase in the game.
     *
     * @param phase which register to execute
     */
    public void executeCard(int phase) {
        if (registers[phase] == null || !player.isOperational()) return;
        ProgramCard programCard = registers[phase];
        if (programCard.isRotate()) {
            player.rotate(programCard.getRotate());
        } else if (programCard.getMoveDistance() == -1) {
            player.reverse();
        } else {
            player.move(programCard.getMoveDistance());
        }
    }

    @Override
    public void lock() {
        if (unlockedRegisters > 0 && unlockedRegisters <= NUMBER_OF_REGISTERS)
            unlockedRegisters--;
    }

    @Override
    public boolean isLocked(int register) {
        return register >= unlockedRegisters;
    }

    @Override
    public void unlock() {
        if (unlockedRegisters >= 0 && unlockedRegisters < NUMBER_OF_REGISTERS) {
            unlockedRegisters++;
        }
    }

    @Override
    public void unlockAll() {
        unlockedRegisters = NUMBER_OF_REGISTERS;
    }

    /**
     * Return cards from registers into player hand. Only returns cards from unlocked registers.
     *
     */
    public void returnCards() {
        for (int i = 0; i < player.getRegisters().getNumUnlockedRegisters(); i++) {
            if (registers[i] != null) {
                player.getHand().receiveCard(registers[i]);
            }
            registers[i] = null;
        }
    }

    /**
     * Remove a specific card from one of the registers and put it back into the hand.
     *
     * @param index  which register to remove from.
     */
    public void returnCard(int index) {
        if (index < 0 || index > registers.length) {
            throw new IndexOutOfBoundsException();
        }
        ProgramCard card = registers[index];
        player.getHand().receiveCard(card);
        registers[index] = null;
    }

    public ProgramCard getCard(int register) {
        return registers[register];
    }

    /**
     * Empty slots in the register are null values. Can return null.
     *
     * @return All cards in the register
     */
    public ArrayList<ProgramCard> getAllCards() {
        ArrayList<ProgramCard> list = new ArrayList<>();
        for (ProgramCard pc : registers) {
            list.add(pc);
        }
        return list;
    }

    public int getNumUnlockedRegisters() {
        return unlockedRegisters;
    }

    public int getNumLockedRegisters() {
        return NUMBER_OF_REGISTERS - unlockedRegisters;
    }
}
