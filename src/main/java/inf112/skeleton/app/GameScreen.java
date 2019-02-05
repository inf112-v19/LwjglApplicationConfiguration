package inf112.skeleton.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
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


    //Rotation degree at start. (Change it depending on which picture is being used to represent the player).
    private float playerRotationDegree;

    private AssetsInner assetsInner;



    public GameScreen(RoboRallyGame game){
        this.game = game;
        camera = new OrthographicCamera();
        player = new Player("Player1", 0, 0);
        playerRotationDegree = player.getRotationDegree();
        programCards = new ProgramCardCD();
        listOfAllProgramCards = programCards.makeStack();

        assetsInner = new AssetsInner();
        assetsInner.load(); //loads the sprites

        for(int i = 0; i < 9; i++){
            player.receiveNewCard(listOfAllProgramCards.remove(0));
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
            System.out.println("id: " + i + ". " + player.getCardsInHand().get(i).toString());
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

        //Just for testing.
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

        assetsInner.load(); //to visually update the player position

        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        //All rendering code goes here
        assetsInner.backgroundSprite.draw(batch);
        assetsInner.player1Sprite.draw(batch);
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


    //Inner class containing assets. (Replaced Assets.java)
    private class AssetsInner {

        //A tile on the game board is 150x150 px.

        private Texture backgroundTexture; //The image
        private Sprite backgroundSprite; //The game object-version of the image

        private Texture player1Texture;
        private Sprite player1Sprite;


        protected void load() {
            backgroundTexture = new Texture(Gdx.files.internal("assets/gameboard/RoboRallyBoard2.png"));
            backgroundTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            backgroundSprite = new Sprite(backgroundTexture);
            backgroundSprite.setPosition(0, 0);

            player1Texture = new Texture(Gdx.files.internal("assets/robot/tvBot.png"));
            player1Texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            player1Sprite = new Sprite(player1Texture);
            player1Sprite.setOriginCenter();
            player1Sprite.setPosition(player.getX(), player.getY());
            player1Sprite.setRotation(player.getRotationDegree());
        }

    }
}


