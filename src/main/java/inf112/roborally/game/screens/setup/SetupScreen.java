package inf112.roborally.game.screens.setup;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.board.Board;
import inf112.roborally.game.board.VaultBoard;
import inf112.roborally.game.enums.SetupState;
import inf112.roborally.game.screens.AbstractScreen;
import inf112.roborally.game.tools.AssMan;
import inf112.roborally.game.objects.Position;

import java.util.ArrayList;

public class SetupScreen extends AbstractScreen {
    // One tile, when width is 1920, is 90 pixels
    // That makes it 1 of 23,333 parts of the screen
    private final int TILE_SIZE_IN_FULLSCRREEN = 90;
    private final int N_TILES = 12; //12x12 tiles
    private Stage stage;
    private String[] possibleFilepaths;
    private ImageButton[] skins;
    private SetupState state;
    private ArrayList<Sprite> information;
    private ArrayList<Sprite> flags;
    private ArrayList<Sprite> flagCheck;
    // Information about current map size
    private float currentTileSize;
    private float currentMapSize;
    private float mapStartX;
    private float screenWidth;
    private float screenHeight;

    // Player choices to be added to the game screen
    private int robotChoiceIndex;
    private ArrayList<Position> flagPositions;

    //Keeps track of the converted clickPos
    private ArrayList<Position> flagClickedPositions;

    // Try to render a board
    private Texture board;

    public SetupScreen(RoboRallyGame game, String[] possibleFilepaths) {
        super(game, AssMan.SETUP_SETUP_SCREEN.fileName);

        stage = new Stage(game.fixedViewPort, game.batch);
        Gdx.input.setInputProcessor(stage);

        this.possibleFilepaths = possibleFilepaths;
        information = new ArrayList<>();
        flags = new ArrayList<>();
        flagCheck = new ArrayList<>();
        flagPositions = new ArrayList<>();
        flagClickedPositions = new ArrayList<>();

        createSkinButtons();
        state = SetupState.PICKINGSKIN;

    }


    private void createSkinButtons() {
        int nSkins = possibleFilepaths.length;
        Sprite selectSkinText = new Sprite(AssMan.manager.get(AssMan.SETUP_SELECT_SKIN_TEXT));
        selectSkinText.setPosition(Gdx.graphics.getWidth() / 2 + 125, 500);
        information.add(selectSkinText);

        skins = new ImageButton[nSkins];
        // Add all the buttons for the skins to the stage
        float scale = 0.4f;
        int x1 = 450; // The left x value
        int x2 = 1050; // The right x value
        // Starting Y value, this will change after 2 buttons are added to get the next to below the first two
        int y = 350;
        for (int i = 1; i < skins.length + 1; i++) {
            int index = i - 1;
            skins[index] = new ImageButton(new TextureRegionDrawable(new Texture(possibleFilepaths[index])));
            ImageButton skin = skins[index];
            skin.setSize(skin.getWidth() * scale, skin.getHeight() * scale);

            // If i is odd, place it on the left side
            if (i % 2 != 0) {
                skin.setPosition(x1, y);
            } else {
                skin.setPosition(x2, y);
                y = y / 2 - 100; // next time, add below
            }

            final int finalIndex = index;
            skin.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    System.out.printf("You selected skin number %d!%n", finalIndex + 1);
                    setRobotChoice(finalIndex);
                }
            });
            stage.addActor(skin);
        }

    }

    private void createMapForPlacingFlags() {

        // One tile here, inn full screen, is approx. 90x90
//        updateBackground(AssMan.SETUP_SETUP_SCREEN_PLACE_FLAGS.fileName);
        updateBackground(AssMan.GAMESCREEN_BACKGROUND2.fileName);
        updateMapNumbers();

        for (int i = 3; i > 0; i--) {
            Sprite flag = new Sprite(new TextureRegion(new Texture(AssMan.FLAG_SKIN.fileName),
                    150 * (i - 1), 0, 150, 150));
            flag.setPosition(100, 200 * i);
            flags.add(flag);
        }


//        board = new Texture(AssMan.SETUP_MAP_VAULT.fileName);

//        state = SetupState.PLACINGFLAGS;
//        state = SetupState.TESTPPLACINGFLAGS;
    }


    @Override
    public void render(float v) {
        super.render(v);

        batch.setProjectionMatrix(game.fixedCamera.combined);
        batch.begin();
        background.draw(batch);
        for (Sprite sprite : information) {
            sprite.draw(batch);
        }

        for (Sprite flag : flags) {
            flag.draw(batch);
        }
        for (Sprite check : flagCheck) {
            check.draw(batch);
        }
        if(board != null) {
            batch.draw(board, screenWidth, screenHeight);
        }

        batch.end();

        handleInput();

        stage.draw();

    }


    private void handleInput() {

        if (Gdx.input.isKeyJustPressed(Input.Keys.T)) {
            game.createGameScreen(); // Choose the standard skin index
            game.setScreen(game.gameScreen);
            dispose();
        }

        // Possible to add more SetupStates
        switch (state) {
//            case TESTPPLACINGFLAGS:



//                break;

            case PLACINGFLAGS:
                // The map has 2 invisible tiles at all sides
                if (Gdx.input.justTouched()) {
                    float mouseX = Gdx.input.getX();
                    float mouseY = Gdx.input.getY();

                    if (mouseX >= mapStartX) {
                        Position clickedPos = convertMouseClickIntoMapPosition(mouseX, mouseY);

                        //Quick-fix to not let the player put flags in holes
                        if ((clickedPos.getX() == 4 && clickedPos.getY() == 10) ||
                                (clickedPos.getX() == 9 && clickedPos.getY() == 10) ||
                                (clickedPos.getX() == 4 && clickedPos.getY() == 3) ||
                                (clickedPos.getX() == 9 && clickedPos.getY() == 3)) {
                            System.out.println("You can not place the flag in a pithole");
                            return;
                        }

                        //If a flag has already been placed on this position then return
                        for (int i = 0; i < flagPositions.size(); i++) {
                            for (int j = 0; j < flagPositions.size(); j++) {
                                if (clickedPos.getX() == flagPositions.get(j).getX() - 4 &&
                                        clickedPos.getY() == flagPositions.get(j).getY()) {
                                    System.out.println("You have already placed a flag here");
                                    return;
                                }
                            }
                        }
                        flagClickedPositions.add(clickedPos);

                        flags.remove(0); // Remove a flag from the left side

                        // Because the map we use has 4 more tiles to the left (where we find the starting positions,
                        // we have to add 4 to the clicked x value
                        Position properPos = new Position(clickedPos.getX() + 4, clickedPos.getY());
                        flagPositions.add(properPos);
                        if (flagPositions.size() == 3) {
                            state = SetupState.DONE;
                        }

                    } else {
                        System.out.println("Please click inside the map!");
                    }
                }
                break;
            case DONE:
                createGameScreenFromSetup();
        }
    }

    private Position convertMouseClickIntoMapPosition(float mouseX, float mouseY) {
        // How many tiles there are on the starting position part of the map,
        // where we cant place flags
        int nStarterMapTiles = 4;

        float mapMouseX = mouseX - mapStartX;
        float mapMouseY = mouseY;

        int x = 1;
        int y = 1;

        // Do the "math" for the x value;
        for (int i = 1; i <= N_TILES; i++) {
            float compareX = i * currentTileSize;
            if (compareX > mapMouseX) {
                x = i;
                break;
            }
        }
        // y value:
        for (int i = N_TILES; i >= 0; i--) {
            float compareY = i * currentTileSize;
            if (compareY < mapMouseY) {
                y = N_TILES - i;
                break;
            }
        }

        return new Position(x, y);
    }

    private void updateMapNumbers() {
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();
        final float NUMBERS_OF_TILES_IN_WIDTH = 21.333f;
        currentTileSize = screenWidth / NUMBERS_OF_TILES_IN_WIDTH;
        currentMapSize = currentTileSize * N_TILES;
        // Map starts at left, so we need to find the map starting x value:
        mapStartX = screenWidth - currentMapSize;
    }

    private void setRobotChoice(int index) {
        robotChoiceIndex = index;
        System.out.println("The robot choice is robot at index " + index + "!");

        // Remove the text and buttons:
        // Currently only one information in the ArrayList
        information.remove(0);
        for (ImageButton btn : skins) {
            btn.remove();
        }

        // Now it's time to place the flags:
        createMapForPlacingFlags();

    }

    private void createGameScreenFromSetup() {
//        game.createGameScreen(robotChoiceIndex, flagPositions);
//        game.setScreen(game.gameScreen);
        dispose();
    }

    @Override
    public void resize(int w, int h) {
        super.resize(w, h);
        // Only need to updateZoom the map numbers whenever the screen is resized
        updateMapNumbers();
    }

    @Override
    public void dispose() {
        System.out.println("Disposing SetupScreen");
        super.dispose();
        stage.dispose();
    }
}
