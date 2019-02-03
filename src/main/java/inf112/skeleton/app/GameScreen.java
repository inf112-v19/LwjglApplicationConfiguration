package inf112.skeleton.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.Scanner;

public class GameScreen implements Screen {

    private RoboRallyGame game;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Player player;
    private ProgramCardCD programCards;
    private ArrayList<ProgramCardCD> listOfAllProgramCards;

    public GameScreen(RoboRallyGame game){
        this.game = game;
        camera = new OrthographicCamera();
        player = new Player("Player1", 0, 0);
        programCards = new ProgramCardCD();
        listOfAllProgramCards = programCards.makeStack();

        for(int i = 0; i < 9; i++){
            player.receiveNewCard(listOfAllProgramCards.get(i));
        }


        //Set to true if you want to have an inverted x y axis with 0 at the top left.
        camera.setToOrtho(true, 4000, 2200);
        batch = new SpriteBatch();
    }

    // Todo: This might need an update after changes in player class
    //Where to call the function?
    public void chooseCardsForRound(){
        printCards();

        Scanner sc = new Scanner(System.in);
        while(!player.registerIsFull()){
            System.out.println("Choose a card. (Choose id)");
            int chosenCard = sc.nextInt();
            player.pickCard(chosenCard);
            System.out.print("You chose card: " + chosenCard + ". "
                    + player.getRegisters().get(player.getRegisters().size()-1).toString());
        }
        System.out.println("Loop finished");

    }

    public void printCards(){
        for(int i = 0; i < player.getCardLimit(); i++){
            System.out.println("id: " + i + ". " + player.getCardDeck().get(i).toString());
        }
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

        //Just for testing. If you want to move the player with given steps then use player.move(steps).
        if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) || (Gdx.input.isKeyJustPressed(Input.Keys.D))){
            player.setX(player.getX()+150);
        }
        else if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT) || (Gdx.input.isKeyJustPressed(Input.Keys.A))){
            player.setX(player.getX()-150);
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.UP) || (Gdx.input.isKeyJustPressed(Input.Keys.W))){
            player.setY(player.getY()-150);
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN) || (Gdx.input.isKeyJustPressed(Input.Keys.S))){
            player.setY(player.getY()+150);
        }



        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        //All rendering code goes here
        batch.draw(Assets.backgroundSprite, 0, 0);
        batch.draw(Assets.player1Texture, player.getX(),player.getY());
        batch.end();
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

    public Player getPlayer() {
        return player;
    }
}
