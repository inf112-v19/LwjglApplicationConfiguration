package inf112.roborally.game.board;

import inf112.roborally.game.objects.Player;

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
        if (cardPosition < 0 || cardPosition >= player.getNumberOfCardsInHand()) {
            throw new IndexOutOfBoundsException(
                    "Card position: " + cardPosition + ". Number of cards in hand: "
                            + player.getNumberOfCardsInHand()
                            + ". Card position should be one less than number of cards in hand.");
        }

        for (int i = 0; i < unlockedRegisters; i++) {
            if (registers[i] == null) {
                registers[i] = player.removeCardInHand(cardPosition);
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

    public void executeCard(int phase) {
        if (registers[phase] == null || !player.isOperational()) return;

        ProgramCard programCard = registers[phase];
        if (programCard.isRotate()) {
            player.rotate(programCard.getRotate());
        }
        else if (programCard.getMoveDistance() == -1) {
            player.reverse();
        }
        else {
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
     * Return cards from registers into player hand. Only returns the cards from unlocked registers.
     *
     * @param player the player who is returning cards.
     */
    public void returnCards(Player player) {
        for (int i = 0; i < player.getRegisters().getNumUnlockedRegisters(); i++) {
            if (registers[i] != null) {
                player.receiveCard(registers[i]);
            }
            registers[i] = null;
        }
    }

    public ProgramCard returnCard(Player player, int index) {
        if (index < 0 || index > registers.length) {
            throw new IndexOutOfBoundsException();
        }
        ProgramCard card = registers[index];
        player.receiveCard(card);
        registers[index] = null;
        return card;
    }

    public ProgramCard getCard(int register) {
        return registers[register];
    }

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
}
