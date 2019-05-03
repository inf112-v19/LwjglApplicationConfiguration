package inf112.roborally.game.board;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.enums.GameState;
import inf112.roborally.game.enums.Rotate;
import inf112.roborally.game.gui.Hud;
import inf112.roborally.game.player.Player;
import inf112.roborally.game.tools.AiRobo;

public class GameLogic extends BoardLogic implements Runnable {
    private final Hud hud;
    private Board board;
    private RoboRallyGame game;
    private Player player1;

    public GameLogic(Board board, Hud hud, RoboRallyGame game) {
        super(board.getPlayers());
        this.game = game;
        this.board = board;
        this.hud = hud;
        player1 = board.getThisPlayer();
    }

    @Override
    public void run() {
        while (state != GameState.GAME_OVER) update();
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                endGame();
            }
        });
    }

    public void update() {
        updatePlayers();
        executeLogic();
    }

    @Override
    public void aiRobosReady() {
        AiRobo.makeDecisionsForRobos(aiBots);
    }

    @Override
    public void doBeforeRound() {
        super.doBeforeRound();
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                hud.updateCardButtons();
                hud.resetPowerDown();
                hud.setButtonTouchable(true);
                if (game.AIvsAI) {
                    hud.setButtonTouchable(false);
                }
                hud.getPlayerStatusDisplay().clearCards();
            }
        });
    }

    @Override
    protected void boardMoves() {
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                board.boardMoves();
            }
        });
        super.boardMoves();
    }

    @Override
    protected void endGame() {
        game.endGameScreen.addWinner(checkIfAPlayerHasWon());
        game.setScreen(game.endGameScreen);
    }

    @Override
    protected void cleanBoard() {
        super.cleanBoard();
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                board.cleanUp();
            }
        });
    }

    @Override
    protected void doPhase() {
        if (phase < 5) {
            hud.getPlayerStatusDisplay().clearCards();
            hud.getPlayerStatusDisplay().addCards(phase);
        }
        super.doPhase();
    }
}