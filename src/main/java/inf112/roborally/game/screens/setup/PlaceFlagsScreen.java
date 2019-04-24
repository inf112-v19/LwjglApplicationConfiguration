package inf112.roborally.game.screens.setup;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.board.Board;
import inf112.roborally.game.objects.Flag;
import inf112.roborally.game.objects.Position;
import inf112.roborally.game.tools.AssMan;
import inf112.roborally.game.tools.ButtonFactory;

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
    private Texture mapTexture;
    private ArrayList<Position> flagPositions;
    private int mapChoiceIndex;
    private int robotChoiceIndex;

    // Text:
    private Label informationText;
    private Label clickedMessage;
    private int remainingFlags = 3;

    // Create a board in the background so that we can check if the player places the flags
    // on legal positions
    private Board board;

    // Display wherever we placed a flag
    private Image[] visuallyDisplayFlag;
    // We need an index for the flags, to get them to display right flags in the right order
    private int currentVisuallyFlagIndex = 0;
    private boolean allFlagsPlaced = false;
    // Buttons for confirming or clearing the placed flags
    private TextButton confirm;
    private TextButton reset;


//    public PlaceFlagsScreen(final RoboRallyGame game, int mapChoiceIndex, int robotChoiceIndex) {
    public PlaceFlagsScreen (final RoboRallyGame game) {
        this.game = game;
        this.stage = new Stage(game.fixedViewPort, game.batch);
        this.mapTexture = game.selectMapScreen.getMapTexture();
        this.mapChoiceIndex = mapChoiceIndex;
        this.robotChoiceIndex = robotChoiceIndex;
        flagPositions = new ArrayList<>();

        Image background = new Image(new TextureRegionDrawable(AssMan.manager.get(AssMan.SETUP_PLACEFLAGS_BACKGROUND)));
        stage.addActor(background);

        map = new Image(new TextureRegionDrawable(mapTexture));
        mapWidth = map.getWidth() * 2f;
        mapHeight = map.getHeight() * 2f;
        map.setSize(mapWidth, mapHeight);
        float mapX = 1920 / 2f - mapWidth / 2;
        float mapY = 1080 / 2f - mapHeight / 2;
        map.setPosition(mapX, mapY);

        map.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!allFlagsPlaced) {
                    handleClick(x,y);
                }
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

        visuallyDisplayFlag = new Image[remainingFlags];
        for (int i = 0; i < visuallyDisplayFlag.length; i++) {
            visuallyDisplayFlag[i] = new Image(new TextureRegionDrawable(AssMan.manager.get(AssMan.FLAG_ATLAS).createSprite(Integer.toString(i+1))));
        }

        stage.addActor(informationText);
        stage.addActor(clickedMessage);

        // Create the reset and confirm button, but don't show them yet
//        confirm = ButtonFactory.createConfirmButton();
        confirm = ButtonFactory.createTextButton("Confirm", 2f);
        // Scale
//        confirm.setSize(confirm.getWidth() * 0.5f, confirm.getHeight() * 0.5f);
        confirm.setPosition((1920 / 2f - confirm.getWidth() / 2)  - 200, 40);
        confirm.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                doneWithSetup();
            }
        });

//        reset = ButtonFactory.createResetButton();
        reset = ButtonFactory.createTextButton("Reset", 2f);
        //Scale
//        reset.setSize(reset.getWidth() * 0.5f, reset.getHeight() * 0.5f);
        reset.setPosition((1920 / 2f - confirm.getWidth() / 2)  + 200, 40);
        reset.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                resetFlags();
            }
        });

        confirm.setVisible(false);
        reset.setVisible(false);
        stage.addActor(confirm);
        stage.addActor(reset);

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

                flagPositions.add(clickedPos);
                remainingFlags--;

                Image currCheck = visuallyDisplayFlag[currentVisuallyFlagIndex++];
                currCheck.setScale(0.1f);
                // Set the position to clicked x plus the x value of where the map starts
                // Same with y value
                currCheck.setPosition(x + map.getX(), y + map.getY());
                stage.addActor(currCheck);

                // Update the information
                informationText.setText("Remaining flags: " + remainingFlags);

                // If we have placed 3 flags, we are done
                // This can be changed later if we want to add more flags
                if (remainingFlags == 0) {
//                    doneWithSetup();
                    allFlagsPlaced = true;
                    showConfirmAndReset();

                }
            }
        }

    }

    private void showConfirmAndReset() {
        confirm.setVisible(true);
        reset.setVisible(true);
    }

    // Check if the clicked position is not either a hole or a previously clicked position
    private boolean checkIfLegalPosition(Position clickedPos) {
        boolean result = true;
        if(game.board.getFloorLayer().getCell(clickedPos.getX(), clickedPos.getY()) == null){
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
        int flagNumber = 1;
        for (Position pos : flagPositions) {
            game.board.getFlags().add(new Flag(pos.getX(), pos.getY(), flagNumber++));
        }
        game.createCustomGameScreen();
        // game.createGameScreen(robotChoiceIndex, flagPositions, mapChoiceIndex);
        game.setScreen(game.gameScreen);
        dispose();
    }

    private void resetFlags() {
        remainingFlags += flagPositions.size();
        currentVisuallyFlagIndex = 0;
        flagPositions = new ArrayList<>();
        allFlagsPlaced = false;
        informationText.setText("Remaining flags: " + remainingFlags);
        clickedMessage.setText("Cleared all flags");

        // Remove the flag actors from the stage
        // For each flag in the array for visually displaying flags, remove it from the stage
        for (Image flag : visuallyDisplayFlag) {
            for(Actor act : stage.getActors()) {
                if(act.equals(flag)) {
                    act.remove();
                }
            }
        }

        confirm.setVisible(false);
        reset.setVisible(false);
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

        if (Gdx.input.isKeyJustPressed(Input.Keys.T)){
            game.createDefaultGameScreen();
            game.setScreen(game.gameScreen);
            dispose();
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
