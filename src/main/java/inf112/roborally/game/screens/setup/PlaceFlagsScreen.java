package inf112.roborally.game.screens.setup;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.board.Board;
import inf112.roborally.game.board.BoardCreator;
import inf112.roborally.game.objects.Flag;
import inf112.roborally.game.objects.Position;
import inf112.roborally.game.tools.AssMan;

import java.util.ArrayList;

public class PlaceFlagsScreen implements Screen {
    protected final RoboRallyGame game;

    private Stage stage;

    private Image map;
    // Width and height AFTER "scaling"
    private float mapWidth;
    private float mapHeight;
    // The pixel size of a tile on the map. Since we scale it to double size,
    // a tile is 32x2 = 64 pixels wide
    private int tileSize = 64;

    // Choices from the last screens
    private Texture mapFilepath;
    private int mapChoiceIndex;
    private int robotChoiceIndex;
    private ArrayList<Position> flagPositions;

    // Text:
    private Label informationText;
    private Label clickedMessage;
    private int remainingFlags = 3;

    // Create a board in the background so that we can check if the player places the flags
    // on legal positions
    private Board board;

    // Display a "check" wherever we placed a flag
//    private TextureRegionDrawable[] visuallyShowClick;
    private Image[] visuallyShowClick;

    public PlaceFlagsScreen(final RoboRallyGame game, Texture mapFilepath, int mapChoiceIndex, int robotChoiceIndex) {
        this.game = game;
        this.stage = new Stage(game.fixedViewPort, game.batch);
        this.mapFilepath = mapFilepath;
        this.mapChoiceIndex = mapChoiceIndex;
        this.robotChoiceIndex = robotChoiceIndex;
        flagPositions = new ArrayList<>();

        board = new BoardCreator(game.chosenMap(mapChoiceIndex));


        Image background = new Image(new TextureRegionDrawable(AssMan.manager.get(AssMan.SETUP_PLACEFLAGS_BACKGROUND)));
        stage.addActor(background);

        map = new Image(new TextureRegionDrawable(mapFilepath));
        mapWidth = map.getWidth() * 2f;
        mapHeight = map.getHeight() * 2f;
        map.setSize(mapWidth, mapHeight);
        float mapX = 1920 / 2f - mapWidth / 2;
        float mapY = 1080 / 2f - mapHeight / 2;
        map.setPosition(mapX, mapY);

        map.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                handleClick(x,y);

            }
        });
        stage.addActor(map);

        // The text labels on the left side
        informationText = new Label("Remaining flags: " + remainingFlags,
                new Label.LabelStyle(AssMan.manager.get(AssMan.FONT_GROTESKIA), Color.WHITE));
        informationText.setPosition(50, 1000);
        informationText.setFontScale(2);

        // The error message
        clickedMessage = new Label("",
                new Label.LabelStyle(AssMan.manager.get(AssMan.FONT_GROTESKIA), Color.WHITE));
        clickedMessage.setPosition(1550,1010, Align.center);
        clickedMessage.setAlignment(Align.center);
        clickedMessage.setFontScale(2);

        // "Checks" for when a flag is placed
//        visuallyShowClick = new TextureRegionDrawable[remainingFlags];
//        for (int i = 0; i < visuallyShowClick.length; i++) {
//            visuallyShowClick[i] = new TextureRegionDrawable(AssMan.manager.get(AssMan.SETUP_CHECK_FLAG));
//        }
        visuallyShowClick = new Image[remainingFlags];
        for (int i = 0; i < visuallyShowClick.length; i++) {
            visuallyShowClick[i] = new Image(new TextureRegionDrawable(AssMan.manager.get(AssMan.SETUP_CHECK_FLAG)));
        }

        stage.addActor(informationText);
        stage.addActor(clickedMessage);
    }

    private void handleClick(float x, float y) {
        // Check if invisible part around map was clicked
        if(x < tileSize || x > mapWidth - tileSize || y < tileSize || y > mapHeight - tileSize) {
            System.out.println("Pressed inside map png, but in the invisible part");
        }
        else if(x < tileSize * 5) {
            System.out.println("Clicked on the starting part on the map");
        }
        else {
            Position clickedPos = convertMouseClickIntoMapPosition(x,y);
            if (!checkIfLegalPosition(clickedPos)) {
                clickedMessage.setText("Cannot place flag here");
            }
            else {
                clickedMessage.setText("Placed flag at x=" + clickedPos.getX() + ", y=" + clickedPos.getY());

                // Place a "check" on the right position
                Image currCheck = visuallyShowClick[remainingFlags-1];
                currCheck.setScale(0.1f);
                // Set the position to clicked x plus the x value of where the map starts
                // Same with y value
                currCheck.setPosition(x + map.getX(),y + map.getY());
                stage.addActor(currCheck);

                flagPositions.add(clickedPos);
                remainingFlags--;
                // Update the information
                informationText.setText("Remaining flags: " + remainingFlags);


                // If we have placed 3 flags, we are done
                // This can be changed later if we want to add more flags
                if (remainingFlags == 0) {
                    doneWithSetup();
                }
            }
        }

    }

    // Check if the clicked position is not either a hole or a previously clicked position
    private boolean checkIfLegalPosition(Position clickedPos) {
        boolean result = true;
        if(board.getFloorLayer().getCell(clickedPos.getX(), clickedPos.getY()) == null){
            result = false;
        }
        for(Position pos : flagPositions) {
            if(clickedPos.equals(pos)) {
                result = false;
                break;
            }
        }

        return result;
    }

    private Position convertMouseClickIntoMapPosition(float mouseX, float mouseY) {

        int tileSize = 64;

        // Calculate how many tiles there are on the map
        // minus two at the end, because the map has invisible tiles around it
        int nTilesOnMap = (int) map.getWidth() / tileSize;
        
        int x = 0;
        int y = 0;

        boolean xDone = false;
        boolean yDone = false;
        // Do the "math" for the x and y value:
        for (int i = 0; i < nTilesOnMap; i++) {
            if(!xDone) {
                float compareX = i * tileSize;
                if(compareX > mouseX) {
                    x = i - 1;
                    xDone = true;
                }
            }
            if(!yDone) {
                float compareY = i * tileSize;
                if(compareY > mouseY) {
                    y = i - 1;
                    yDone = true;
                }
            }
        }
        return new Position(x, y);
    }

    private void doneWithSetup() {
        game.createGameScreen(robotChoiceIndex, flagPositions, mapChoiceIndex);
        game.setScreen(game.gameScreen);
        dispose();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0 / 255f, 20 / 255f, 15 / 255f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            dispose();
            Gdx.app.exit();
        }
    }

    @Override
    public void resize(int width, int height) {
        game.fixedViewPort.update(width, height);
        game.dynamicViewPort.update(width, height);

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
        System.out.println("Disposing PlaceFlagsScreen");
        stage.dispose();
    }
}
