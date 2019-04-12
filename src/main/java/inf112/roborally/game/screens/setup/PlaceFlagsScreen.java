package inf112.roborally.game.screens.setup;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import inf112.roborally.game.RoboRallyGame;
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
    private int flagNumber;

    public PlaceFlagsScreen(final RoboRallyGame game, Texture mapFilepath, int mapChoiceIndex, int robotChoiceIndex) {
        this.game = game;
        this.stage = new Stage(game.fixedViewPort, game.batch);
        this.mapFilepath = mapFilepath;
        this.mapChoiceIndex = mapChoiceIndex;
        this.robotChoiceIndex = robotChoiceIndex;
        flagNumber = 0;
        flagPositions = new ArrayList<>();
        Image background = new Image(new TextureRegionDrawable(AssMan.manager.get(AssMan.GAMESCREEN_BACKGROUND2)));

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
                System.out.println("Clicked map!");
                handleClick(x,y);

            }
        });

        stage.addActor(map);
    }

    private void handleClick(float x, float y) {
//        System.out.printf("Inside handleclick(), x = %.2f and y = %.2f%n", x, y);

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
                System.out.println("Cannot place a flag here, either it's a hole, " +
                        "or a flag has already been placed here!");
            }
            else {
//                System.out.printf("Position: x = %d, y = %d%n", clickedPos.getX(), clickedPos.getY());
                flagPositions.add(clickedPos);
                game.board.getFlags().add(new Flag(clickedPos.getX(), clickedPos.getY(), ++flagNumber));

                // If we have placed 3 flags, we are done
                // This can be changed later if we want to add more flags
                if(flagPositions.size() == 3) {
                    doneWithSetup();
                }
            }
        }

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
        game.createDefaultGameScreen();
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
