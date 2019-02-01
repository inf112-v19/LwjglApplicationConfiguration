package inf112.skeleton.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class Player {


    private int x;
    private int y;

    public Player(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void move(){
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            //Assets.robotSprite2.setY(50);
            y -= 150;
        }else if(Gdx.input.isKeyPressed(Input.Keys.A)){
            //Assets.robotSprite2.setX(50);
            x -= 150;
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.S)){
            //Assets.robotSprite2.setY(50);
            y += 150;
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.D)){
            //Assets.robotSprite2.setX(50);
            x += 150;
        }
        //Assets.load();
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
