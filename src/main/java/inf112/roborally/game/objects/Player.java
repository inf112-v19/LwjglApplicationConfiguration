package inf112.roborally.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import inf112.roborally.game.ProgramRegisters;
import inf112.roborally.game.world.Direction;

public class  Player extends MovableGameObject {
    private static final int MAX_DAMAGE = 9;
    private static final int MAX_LIVES = 3;

    private String name;
    private int lives;
    private int damage;
    private Backup backup;
    private ProgramRegisters registers;


    public Player(String name, int x, int y, Direction direction) {
        super(x, y, "assets/robot/tvBot.png");
        this.name = name;
        damage = 0;
        lives = MAX_LIVES;

        setDirection(direction);
        makeSprite();
        loadVisualRepresentation();

        backup = new Backup(x, y);
        registers = new ProgramRegisters();
    }

    /** FOR TESTING ONLY */
    public Player(int x, int y){
        super(x, y, "assets/robot/tvBot.png");
        damage = 0;
        lives = MAX_LIVES;
        registers = new ProgramRegisters();
    }

    @Override
    public void updateSprite() {
        boolean flipSprite = false;
        switch (getDirection()){
            case SOUTH:
                flipSprite = true;
            case NORTH:
                rotationDegree = 270; break;
            case WEST:
                flipSprite = true;
            case EAST:
                rotationDegree = 180;
                break;
        }
        sprite.setFlip(flipSprite, false);
        super.updateSprite();
    }


    public void update(){
        handleInput();

        if (isDestroyed() && !outOfLives()){
            Gdx.app.log("Player", "is destroyed!");
            lives--;
            repairAllDamage();
            backup.movePlayer(this);
            }
        else if (isDestroyed() && outOfLives()){
            Gdx.app.log("Player", "is dead!");
            Gdx.app.log("GAME OVER", "");
        }
        updateSprite();
    }

    public void repairAllDamage() {
        registers.unlockRegisters();
        damage = 0;
    }

    public void handleInput() {
        //Just for testing
        moved = true;

        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) || (Gdx.input.isKeyJustPressed(Input.Keys.D))) {
            setDirection(Direction.EAST);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT) || (Gdx.input.isKeyJustPressed(Input.Keys.A))) {
            setDirection(Direction.WEST);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.UP) || (Gdx.input.isKeyJustPressed(Input.Keys.W))) {
            setDirection(Direction.NORTH);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN) || (Gdx.input.isKeyJustPressed(Input.Keys.S))) {
            setDirection(Direction.SOUTH);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.B)) {
            backup.move(getX(), getY());
            backup.updateSprite();
            moved = false;
        } else {
            moved = false;
        }
    }

    public void takeDamage() {
        this.damage++;
        if (damage < 10 && damage >= 5) {
            registers.lockRegister();
        }
    }

    public void destroy(){
        damage = MAX_DAMAGE +1;
    }

    public boolean isDestroyed() {
        return damage > MAX_DAMAGE;
    }

    public boolean outOfLives() {
        return lives <= 0;
    }

    public int getDamage() {
        return damage;
    }


    public int getLives() {
        return this.lives;
    }

    public String getName() {
        return name;
    }

    public GameObject getBackup() {
        return backup;
    }

    public ProgramRegisters getRegisters(){
        return registers;
    }

    @Override
    public String toString() {
        return getName() + " | Health: " + (10 - damage) + " | Lives: " + lives;
    }
}
