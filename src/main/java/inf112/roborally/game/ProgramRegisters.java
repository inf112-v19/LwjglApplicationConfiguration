package inf112.roborally.game;

import inf112.roborally.game.objects.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;

public class ProgramRegisters {
    public static final int NUMBER_OF_REGISTERS = 5;
    public static final int MAX_NUMBER_OF_CARDS = 9;

    private int unlockedRegisters;
    private ProgramCard[] registers;
    private ArrayList<ProgramCard> cardsInHand;
    private PriorityQueue<Integer> cardsToRemove;


    public ProgramRegisters() {
        registers = new ProgramCard[NUMBER_OF_REGISTERS];
        unlockedRegisters = NUMBER_OF_REGISTERS;
        cardsInHand = new ArrayList<>();
        cardsToRemove = new PriorityQueue<>(5, Collections.reverseOrder());
    }

    public void receiveCard(ProgramCard programCard) {
        cardsInHand.add(programCard);
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

    public ArrayList<ProgramCard> returnCards(){
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

    public int pickCard(int cardPosition) {
        for(int i = 0; i < unlockedRegisters; i++){
            if(registers[i] == null) {
                registers[i] = cardsInHand.remove(cardPosition); //get
                cardsToRemove.add(cardPosition);
                return i;
            }
        }
        return -1;
    }

    public void removeCardsFromHand(){
        while (!cardsToRemove.isEmpty()) {
            System.out.println(cardsToRemove.peek());
            cardsInHand.remove(cardsToRemove.poll());
        }
    }

    public boolean isLocked(int register) {
        return register >= unlockedRegisters;
    }

    public ArrayList<ProgramCard> getCardsInHand(){
        return cardsInHand;
    }

    public int getUnlockedRegisters() {
        return unlockedRegisters;
    }
}
