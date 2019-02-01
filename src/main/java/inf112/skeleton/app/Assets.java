package inf112.skeleton.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

    /*
     RoboRallyBoard1.png and RoboRallyBoard2.png has width = 1800 px, height = 1800 px.
     RoboRallyBoard3.png has width = 2400 px, height = 1500 px.
     testBlock.png has width = 150 px, height = 150 px.
     A tile on the game board is 150x150 px.
    */

public class Assets {
    public static Texture textureBackground; //The image
    public static Sprite spriteBackground; //The game object-version of the image

    public static Texture player1Texture;
    public static Sprite player1Sprite;

    public static Texture player2Texture;
    public static Sprite player2Sprite;

    public static Texture textureRobot;
    public static Sprite spriteRobot;


    public static void load() {

        textureBackground = new Texture(Gdx.files.internal("assets/gameboard/RoboRallyBoard2.png"));
        textureBackground.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        spriteBackground = new Sprite(textureBackground);

        player1Texture = new Texture(Gdx.files.internal("assets/robot/testBlock.png"));
        player1Texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        player1Sprite = new Sprite(player1Texture);

        player2Texture = new Texture(Gdx.files.internal("assets/robot/pil1.png"));
        player2Texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        player2Sprite = new Sprite(player2Texture);
        player2Sprite.setY(400);
        player2Sprite.setX(400);
        player2Sprite.rotate(90);

        textureRobot = new Texture(Gdx.files.internal("assets/robot/testBlock.png"));
        spriteRobot = new Sprite(textureRobot);

        //spriteBackground.flip(false, true);
    }
}
