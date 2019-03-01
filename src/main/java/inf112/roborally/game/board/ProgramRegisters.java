package inf112.roborally.game.board;

import inf112.roborally.game.objects.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class ProgramRegisters {
    public static final int NUMBER_OF_REGISTERS = 5;
    public static final int MAX_NUMBER_OF_CARDS = 9;
    private final Player player;

    private int unlockedRegisters;
    private ProgramCard[] registers;
    private PriorityQueue<Integer> cardsToRemove;


    public ProgramRegisters(Player player) {
        this.player = player;
        registers = new ProgramCard[NUMBER_OF_REGISTERS];
        unlockedRegisters = NUMBER_OF_REGISTERS;

        cardsToRemove = new PriorityQueue<>(5, Collections.reverseOrder());
    }

    public boolean registerIsFull() {
        for (int i = 0; i < NUMBER_OF_REGISTERS; i++) {
            if (registers[i] == null)
                return false;
        }
        return true;
    }

    public int getCardLimit(Player player) {
        return MAX_NUMBER_OF_CARDS - player.getDamage();
    }

    public ProgramCard getCardInRegister(int register) {
        return registers[register];
    }

    public ArrayList<ProgramCard> getCardsInRegisters() {
        ArrayList<ProgramCard> list = new ArrayList<>();
        for (ProgramCard pc : registers)
            list.add(pc);

        return list;
    }

    public List<ProgramCard> returnCardsFromRegisters(List<ProgramCard> cardsInHand){
        for(int i = 0; i < unlockedRegisters; i++) {
            if(registers[i] != null) {
                cardsInHand.add(registers[i]);
            }
            registers[i] = null;
        }
        return cardsInHand;
    }

    public void lockRegister(){
        unlockedRegisters--;
    }

    public void unlockRegisters(){
        unlockedRegisters = NUMBER_OF_REGISTERS;
    }

    /**
     * Picks a card from from the player hand and puts it into the register.
     * @param cardPosition the position of the card in hand
     * @return index at which it is stored if adding a card to the register was successful, -1 if not.
     */
    public int pickCard(int cardPosition) {
        if(cardPosition < 0 || cardPosition >= player.getNumberOfCardsInHand()){
            throw new IndexOutOfBoundsException("CardPosition: " + cardPosition + ". Number of cards in hand: " + player.getNumberOfCardsInHand());
        }

        for(int i = 0; i < unlockedRegisters; i++){
            if(registers[i] == null) {
                registers[i] = player.getCardsInHand().remove(cardPosition); //get
                return i;
            }
        }
        System.out.println("Registers are full");
        return -1;
    }

    public boolean isLocked(int register) {
        return register >= unlockedRegisters;
    }

    public int getUnlockedRegisters() {
        return unlockedRegisters;
    }
}
