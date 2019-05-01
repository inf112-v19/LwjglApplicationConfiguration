package inf112.roborally.game.server;

import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.player.ProgramCard;

import java.util.Stack;

public class Dealer {
    protected Stack<ProgramCard> returnedProgramCards;
    protected Stack<ProgramCard> stackOfProgramCards;
    RoboRallyGame game;
    Server server;

    public Dealer(RoboRallyGame game, Server server){
        this.game = game;
        this.server = server;
        stackOfProgramCards = ProgramCard.makeProgramCardDeck();
        returnedProgramCards = new Stack<>();
    }

    public void dealCard(String name){

    }

}
