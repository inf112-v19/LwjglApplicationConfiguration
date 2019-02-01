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
    public static Texture backgroundTexture; //The image
    public static Sprite backgroundSprite; //The game object-version of the image


    public static Texture player1Texture;
    public static Sprite player1Sprite;


    public static void load() {

        backgroundTexture = new Texture(Gdx.files.internal("assets/gameboard/RoboRallyBoard2.png"));
        backgroundTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        backgroundSprite = new Sprite(backgroundTexture);


        player1Texture = new Texture(Gdx.files.internal("assets/robot/pil1.png"));
        player1Texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        player1Sprite = new Sprite(player1Texture);

        //backgroundSprite.flip(false, true);
    }
}
