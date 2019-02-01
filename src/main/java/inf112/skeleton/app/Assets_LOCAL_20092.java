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

    public static Texture robotTexture1;
    public static Sprite robotSprite1;

    public static Texture robotTexture2;
    public static Sprite robotSprite2;

    public static void load() {

        textureBackground = new Texture(Gdx.files.internal("assets/gameboard/RoboRallyBoard3.png"));
        textureBackground.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        spriteBackground = new Sprite(textureBackground);

        robotTexture1 = new Texture(Gdx.files.internal("assets/robot/testBlock.png"));
        robotTexture1.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        robotSprite1 = new Sprite(robotTexture1);

        robotTexture2 = new Texture(Gdx.files.internal("assets/robot/pil1.png"));
        robotTexture2.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        robotSprite2 = new Sprite(robotTexture2);
        robotSprite2.rotate(90);

        System.out.println(robotSprite2.getRotation());

        //spriteBackground.flip(false, true);
    }
}
