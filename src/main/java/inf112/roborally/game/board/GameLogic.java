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
    private int playerIndex; // The index on the list for your player


    public GameLogic(Board board, Hud hud, RoboRallyGame game) {
        super(board.getPlayers());
        this.game = game;
        this.board = board;
        this.hud = hud;
//        player1 = players.get(0);
        // Set the name to match the current player
        player1 = board.getThisPlayer();

//        playerIndex = 0;
//        for(Player player : players) {
//            if(player.getName().equals(game.playerName)) {
//                player1 = player;
//            }
//            else {
//                playerIndex++;
//            }
//        }
        //TODO: Player choosing which direction to face needs to happen when the game initially starts.
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

    public void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.B)) {
            game.setScreen(game.settingsScreen);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            game.endGameScreen.addWinner(player1);
            game.setScreen(game.endGameScreen);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.R)) {
            player1.getRegisters().returnCards();
            hud.updateCards();
        }

        boolean updatePlayer = true;

        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            player1.move(1);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            player1.rotate(Rotate.LEFT);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            player1.rotate(Rotate.RIGHT);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            player1.reverse();
        } else {
            updatePlayer = false;
        }
        if (updatePlayer) {
            board.boardMoves();
            updatePlayers();
        }
    }

    @Override
    public void doBeforeRound() {
        super.doBeforeRound();
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                hud.updateCards();
                hud.resetPowerDown();
                hud.setButtonTouchable(true);
                if(game.AIvsAI) {
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
    protected void doPhase(){
        if (phase < 5) {
            hud.getPlayerStatusDisplay().clearCards();
            hud.getPlayerStatusDisplay().addCards(phase);
        }
        super.doPhase();
    }
}

