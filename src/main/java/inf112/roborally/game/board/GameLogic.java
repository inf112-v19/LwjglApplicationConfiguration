package inf112.roborally.game.board;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.enums.GameState;
import inf112.roborally.game.enums.PlayerState;
import inf112.roborally.game.enums.Rotate;
import inf112.roborally.game.gui.Hud;
import inf112.roborally.game.objects.Player;

import java.util.ArrayList;

public class GameLogic extends BoardLogic {

    private final Hud hud;
    private Board board;
    private RoboRallyGame game;
    private Player player1;


    public GameLogic(Board board, Hud hud, RoboRallyGame game) {
        super(board.getPlayers());
        this.game = game;
        this.board = board;
        this.hud = hud;

        player1 = players.get(0);

        //TODO: Player choosing which direction to face needs to happen when the game initially starts.
        update();
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.B)) {
            game.setScreen(game.settingsScreen);
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            System.out.println("Switched to EndGame screen");
            game.endGameScreen.addWinner(player1);
            game.setScreen(game.endGameScreen);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.R)) {
            player1.getRegisters().returnCards(players.get(0));
            hud.clearAllCards();
            hud.updateCards();
        }

        boolean updatePlayer = true;

        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            player1.move(1);
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            player1.rotate(Rotate.LEFT);
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            player1.rotate(Rotate.RIGHT);
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            player1.reverse();
        }
        else {
            updatePlayer = false;
        }
        if (updatePlayer) {
            board.boardMoves();
            updatePlayers();
        }
    }

    public void update() {
        handleInput();
        updatePlayers();
        switch (state) {
            case BETWEEN_ROUNDS:
                doBeforeRound();
                break;
            case PICKING_CARDS:
                checkIfReady();
                break;
            case ROUND:
                doPhase();
                break;
            case BOARD_MOVES:
                boardMoves();
                break;
            case GAME_OVER:
                endGame();
        }
    }

    public void doBeforeRound() {
        super.doBeforeRound();
        hud.clearAllCards();
        hud.updateCards();
    }

    @Override
    public void checkIfReady() {
        if (player1.isReady()) {
            state = GameState.ROUND;
            if (player1.playerState == PlayerState.READY)
                player1.playerState = PlayerState.OPERATIONAL;
            state = GameState.ROUND;
        }
    }

    @Override
    protected void boardMoves() {
        board.boardMoves();
        super.boardMoves();
    }

    @Override
    protected void endGame() {
        game.endGameScreen.addWinner(checkIfAPlayerHasWon());
        game.setScreen(game.endGameScreen);
    }

    @Override
    protected void cleanBoard() {
        board.cleanUp();
    }

}

