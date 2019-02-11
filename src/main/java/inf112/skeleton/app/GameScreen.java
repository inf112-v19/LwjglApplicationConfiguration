package inf112.skeleton.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameScreen implements Screen { //TODO: Should GameScreen implement ApplicationListener? Extends Game? Something else?

    private Hud hud;



    public static String mapPath = Main.TEST_MAP;

    // TODO: Better solution?
    public int mouseInputManager = 0; // Using Gdx.input.isButtonPressed would result in constant mouse events
    // This is meant to limit inputs from mouse clicks while button is pressed.

    private RoboRallyGame game;
    private OrthographicCamera camera;
    private Viewport gamePort;
    // map stuff:
    private TmxMapLoader loader;
    private TiledMap board;
    private TiledMapTileLayer floorLayer;
    private TiledMapTileLayer beltLayer;
    private TiledMapTileLayer laserLayer;
    private TiledMapTileLayer wallLayer;
    private TiledMapRenderer mapRenderer;

    private SpriteBatch batch;
    private Player player;
    private ArrayList<ProgramCard> stackOfProgramCards;


    public GameScreen(RoboRallyGame game){
        this.game = game;
        player = new Player("Player1", 0, 0);
        stackOfProgramCards = ProgramCard.makeStack();

        camera = new OrthographicCamera();


        for(int i = 0; i < 9; i++){
            player.receiveNewCard(stackOfProgramCards.remove(0));
        }


        player.loadVisualRepresentation();

        //Set to true if you want to have an inverted x y axis with 0 at the top left.
        camera.setToOrtho(true, 4000, 2200);

        batch = new SpriteBatch();
        hud = new Hud(batch, player);
    }

    /*
    Just to test
    Uses all the cards in hand
     */
    public void useCard(){

        for (int i = 0; i<9; i++){
            ProgramCard useCard = player.getCardsInHand().get(i);
            player.rotate(useCard.getRotate());
            player.move(useCard.getMoveDistance());
            System.out.println(useCard);
        }

    }

    public GameScreen(RoboRallyGame game, String mapPath){
        this.mapPath = mapPath;
        //player:
        player = new Player("Player1", 0, 0);
        stackOfProgramCards = ProgramCard.makeStack();
        player.loadVisualRepresentation();
        for(int i = 0; i < 9; i++){
            player.receiveNewCard(stackOfProgramCards.remove(0));
        }
        batch = new SpriteBatch();
        hud = new Hud(batch, player);
    }

    @Override
    public void show(){
        //map:
        loader = new TmxMapLoader();
        TmxMapLoader.Parameters parameters = new TmxMapLoader.Parameters();
        parameters.flipY = true;
        board = loader.load(mapPath, parameters);
        mapRenderer = new OrthogonalTiledMapRenderer(board, Main.UNIT_SCALE);
        //view:
        camera = new OrthographicCamera();
        camera.setToOrtho(false);
        camera.position.set(Main.GAME_WIDTH/2,Main.GAME_HEIGHT/2,0);
        camera.update();
        gamePort = new FitViewport(Main.GAME_WIDTH, Main.GAME_HEIGHT, camera);


        beltLayer =  (TiledMapTileLayer) board.getLayers().get("belts");
        floorLayer = (TiledMapTileLayer) board.getLayers().get("floor");
        laserLayer = (TiledMapTileLayer) board.getLayers().get("lasers");
        wallLayer =  (TiledMapTileLayer) board.getLayers().get("walls");
    }

    @Override
    public void render(float delta) {
        update();
        float r = 158/255f;
        float g = 158/255f;
        float b = 158/255f;

        //The function glClearColor takes in values between 0 and 1. It creates the background color.
        Gdx.gl.glClearColor(r,g,b, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mapRenderer.setView(camera);
        mapRenderer.render();
        camera.update();



        batch.setProjectionMatrix(camera.combined);
        batch.begin();




        //assetsInner.backgroundSprite.draw(batch);
        player.getSprite().draw(batch);


        batch.end();

        batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
    }

    public void update(){
        if(mouseInputManager > 0)
            mouseInputManager--;
        if (mouseInputManager == 0 && Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
            System.out.println(Gdx.input.getX() + ", " + Gdx.input.getY());
            mouseInputManager += 5;
        }
        //Just for testing
        boolean playerMoved = true;
        Direction dir = null;

        if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) || (Gdx.input.isKeyJustPressed(Input.Keys.D))){
            dir = Direction.EAST;
        }
        else if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT) || (Gdx.input.isKeyJustPressed(Input.Keys.A))){
            dir = Direction.WEST;
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.UP) || (Gdx.input.isKeyJustPressed(Input.Keys.W))){
            dir = Direction.NORTH;
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN) || (Gdx.input.isKeyJustPressed(Input.Keys.S))){
            dir = Direction.SOUTH;
        }
        else {
            playerMoved = false;
        }

        if(playerMoved){
            if(dir != null && canGo(dir))
                player.moveDir(dir);
            boardInteractsWithPlayer();
            player.loadVisualRepresentation();
        }

    }

    public void boardInteractsWithPlayer(){
        int x = (player.getX()) / Main.MOVE_DIST;
        int y = (player.getY()) / Main.MOVE_DIST;

        // check if player is on a belt:
        TiledMapTileLayer.Cell currentCell = beltLayer.getCell(x,y);
        if(currentCell != null && currentCell.getTile().getProperties().containsKey("Belt")){
            Direction dir = Direction.valueOf(currentCell.getTile().getProperties().getValues().next().toString());
            if(canGo(dir)) {
                player.moveDir(dir);
            }
        }
        //Player might have moved so we need to acquire these again:
        x = (player.getX()) / Main.MOVE_DIST;
        y = (player.getY()) / Main.MOVE_DIST;

        // check if player is standing in a laser:
        currentCell = laserLayer.getCell(x,y);
        if(currentCell != null && currentCell.getTile().getProperties().containsKey("Laser")) {
            System.out.println("Ouch!");
            player.takeDamage();
        }

        // check if player is standing on the floor:
        if (floorLayer.getCell(x,y).getTile().getProperties().containsKey("Floor")) {
            System.out.println("Floor");
        } else if (floorLayer.getCell(x,y).getTile().getProperties().containsKey("Hole")){
            System.out.println("Hole");
            //player.getsDestroyed();
        }



    }

    public boolean canGo(Direction dir){
        // first check the current tile:
        int x = (player.getX()) / Main.MOVE_DIST;
        int y = (player.getY()) / Main.MOVE_DIST;
        TiledMapTileLayer.Cell currentCell =  wallLayer.getCell(x,y);
        List<String> walls = new ArrayList<>();

        if(currentCell != null && currentCell.getTile().getProperties().containsKey("Wall")) {
            walls = splitBySpace(currentCell.getTile().getProperties().getValues().next().toString());
        }
        if (walls.contains(dir.toString())){
            System.out.println("Hit a wall!(here)");
            return false;
        }

        // then check target tile:
        switch (dir){
            case NORTH:
                y++; break;
            case SOUTH:
                y--; break;
            case WEST:
                x--; break;
            case EAST:
                x++; break;
        }

        walls = new ArrayList<>();
        TiledMapTileLayer.Cell targetCell = wallLayer.getCell(x,y);

        if(targetCell != null && targetCell.getTile().getProperties().containsKey("Wall")) {
            walls = splitBySpace(targetCell.getTile().getProperties().getValues().next().toString());
        }

        dir = dir.getOppositeDirection();

        if (walls.contains(dir.toString())){
            System.out.println("Hit a wall!(next)");
            return false;
        }
        return true;
    }

    public List<String> splitBySpace(String strToSplit){
        List<String> splitList;
        String [] items = strToSplit.split(" ");
        splitList = Arrays.asList(items);
        return splitList;
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
        board.dispose();
        player.getTexture().dispose();
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width,height);
    }
}


