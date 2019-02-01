package inf112.skeleton.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameScreen implements Screen {

    private RoboRallyGame game;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Robot robot;

    public GameScreen(RoboRallyGame game){
        this.game = game;
        camera = new OrthographicCamera();
        robot = new Robot(0, 0);

        //Set to true if you want to have an inverted x y axis with 0 at the top left.
        camera.setToOrtho(true, 4000, 2200);
        batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        float r = 158/255f;
        float g = 158/255f;
        float b = 158/255f;

        //The function glClearColor takes in values between 0 and 1. It creates the background color.
        Gdx.gl.glClearColor(r,g,b, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        generalUpdate();
        //robot.move();

        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        //All rendering code goes here

        batch.draw(Assets.spriteBackground, 0, 0);
        //batch.draw(Assets.robotSprite1, Assets.robotSprite1.getX(),Assets.robotSprite1.getY()); //TODO: Transparency/smaller picture?
        //batch.draw(Assets.robotSprite2, Assets.robotSprite2.getX(),Assets.robotSprite2.getY()); //TODO: Make rotation work

        batch.draw(Assets.textureRobot, robot.getX(),robot.getY());

        System.out.println("x: " + robot.getX());
        System.out.println("y: " + robot.getY());
        batch.end();
    }


    //Does the same as robot.move(), though it is "quicker"
    public void generalUpdate(){
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            robot.setY(robot.getY()-150);
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.A)){
            robot.setX(robot.getX()-150);
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.S)){
            robot.setY(robot.getY()+150);
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.D)){
            robot.setX(robot.getX()+150);
        }
    }

    @Override
    public void show() {

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
        batch.dispose();
    }

    @Override
    public void resize(int width, int height) {

    }
}
