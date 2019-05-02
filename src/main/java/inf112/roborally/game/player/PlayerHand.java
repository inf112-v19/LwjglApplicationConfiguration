package inf112.roborally.game.player;

import java.util.ArrayList;

public class PlayerHand {
    private ArrayList<ProgramCard> cardsInHand;
    private Player player;

    public PlayerHand(Player player) {
        this.player = player;
        cardsInHand = new ArrayList<>();
    }

    public void receiveCard(ProgramCard programCard) {
        if (programCard == null) {
            throw new NullPointerException("Trying to add a programCard that has value null");
        } else if (cardsInHand.size() == ProgramRegisters.MAX_NUMBER_OF_CARDS) {
            System.out.println(player.getName() + " can not receive more cards");
            return;
        }
        cardsInHand.add(programCard);
    }

    boolean isFull() {
        return cardsInHand.size() >= player.getCardLimit();
    }

    ProgramCard removeCard(int cardPos) {
        if (cardPos < 0 || cardPos >= cardsInHand.size()) {
            throw new IndexOutOfBoundsException("Trying to remove index: " + cardPos
                    + ", but number of cards in hand: " + cardsInHand.size());
        }
        return cardsInHand.remove(cardPos);
    }

    public ArrayList<ProgramCard> returnCards(ArrayList<ProgramCard> list) {
        while (!cardsInHand.isEmpty()) list.add(cardsInHand.remove(0));
        return list;
    }

    ArrayList<ProgramCard> getCardsInHand() {
        return cardsInHand;
    }

    public int size() {
        return cardsInHand.size();
    }

    public ProgramCard getCard(int cardPos) {
        return cardsInHand.get(cardPos);
    }

    public void removeAllCards() {
        cardsInHand.clear();
    }
}

