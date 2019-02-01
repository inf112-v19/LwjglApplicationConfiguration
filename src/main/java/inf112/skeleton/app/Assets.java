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

    public static Texture robotTexture;
    public static Sprite robotSprite;

    public static void load() {


        textureBackground = new Texture(Gdx.files.internal("assets/gameboard/RoboRallyBoard3.png"));
        textureBackground.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        spriteBackground = new Sprite(textureBackground);

        robotTexture = new Texture(Gdx.files.internal("assets/robot/testBlock.png"));
        robotTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        robotSprite = new Sprite(robotTexture);

        //spriteBackground.flip(false, true);
    }
}
