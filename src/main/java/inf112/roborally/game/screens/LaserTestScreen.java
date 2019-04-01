package inf112.roborally.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.board.Board;
import inf112.roborally.game.enums.Direction;
import inf112.roborally.game.objects.Player;

public class LaserTestScreen implements Screen {
    RoboRallyGame game;
    Board board;

    public LaserTestScreen(RoboRallyGame game){
        this.game = game;
        board = new Board();
        board.createBoard(RoboRallyGame.LASER_TEST_MAP);
        board.findLasers();

        for(int i = 0; i < RoboRallyGame.MAX_PLAYERS; i++){
            Player testBot = new Player("testBot" + i, "assets/robot/claptrapRefined.png", Direction.NORTH, board);
            board.addPlayer(testBot);
        }
        board.placePlayers();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
            board.robotLasersFire();
            board.lasersFire();
            updateAllRobots();
        }
        if (Gdx.input.isKeyJustPressed((Input.Keys.N))){
            rotateAllRobot(Direction.NORTH);
            System.out.println("Press Enter to fire lasers");
            System.out.println("When facing north");
        }
        if (Gdx.input.isKeyJustPressed((Input.Keys.S))){
            rotateAllRobot(Direction.SOUTH);
            System.out.println("Press Enter to fire lasers");

        }
        if (Gdx.input.isKeyJustPressed((Input.Keys.W))){
            rotateAllRobot(Direction.WEST);
            System.out.println("Press Enter to fire lasers");

        }
        if (Gdx.input.isKeyJustPressed((Input.Keys.E))){
            rotateAllRobot(Direction.EAST);
            System.out.println("Press Enter to fire lasers");

        }

        float r = 10 / 255f;
        float g = 10 / 255f;
        float b = 10 / 255f;
        Gdx.gl.glClearColor(r, g, b, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        board.render(game.dynamicCamera);

        game.batch.setProjectionMatrix(game.dynamicCamera.combined);
        game.batch.begin();
        board.drawGameObjects(game.batch);
        game.batch.end();
    }

    private void rotateAllRobot(Direction dir) {
        for(Player rob : board.getPlayers()) {
            rob.setDirection(dir);
            rob.updateSprite();
        }
    }

    private void allRobotsFire() {
        for (Player robot : board.getPlayers()){
            robot.getLaserCannon().fire(board);
        }
    }

    private void updateAllRobots(){
        for (Player p : board.getPlayers()) {
            p.updateSprite();
        }
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
