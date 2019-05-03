package inf112.roborally.game.board;

import com.badlogic.gdx.Gdx;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.gui.Hud;
import inf112.roborally.game.tools.AiRobo;

public class GameLogic extends BoardLogic {
    private final Hud hud;
    private Board board;
    private RoboRallyGame game;

    public GameLogic(Board board, Hud hud, RoboRallyGame game) {
        super(board.getPlayers());
        this.game = game;
        this.board = board;
        this.hud = hud;
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