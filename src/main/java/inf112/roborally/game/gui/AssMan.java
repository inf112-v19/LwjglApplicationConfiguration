package inf112.roborally.game.gui;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class AssMan{

    public static final AssetManager manager = new AssetManager();

    //Hud buttons
    public static final AssetDescriptor<Texture>  buttonSubmit
            = new AssetDescriptor<>("assets/cards/buttonSubmit.png", Texture.class);

    public static final AssetDescriptor<Texture>  buttonSubmitGrey
            = new AssetDescriptor<>("assets/cards/buttonSubmitGrey.png", Texture.class);

    public static final AssetDescriptor<Texture>  buttonClear
            = new AssetDescriptor<>("assets/cards/buttonClear.png", Texture.class);

    public static final AssetDescriptor<Texture> settingsButton
            = new AssetDescriptor<>("assets/img/settingsbtn.png", Texture.class);


    //Player skins
    public static final AssetDescriptor<Texture> playerBartenderClaptrap
            = new AssetDescriptor<>("assets/robot/bartenderclaptrap.png", Texture.class);

    public static final AssetDescriptor<Texture> playerClaptrapRefined
            = new AssetDescriptor<>("assets/robot/claptrapRefined.png", Texture.class);

    public static final AssetDescriptor<Texture> playerButlerRefined
            = new AssetDescriptor<>("assets/robot/butlerRefined.png", Texture.class);

    public static final AssetDescriptor<Texture> playerClaptrap3000
            = new AssetDescriptor<>("assets/robot/claptrap3000.png", Texture.class);



    public static void load(){
        manager.load(buttonSubmit);
        manager.load(buttonSubmitGrey);
        manager.load(buttonClear);
        manager.load(settingsButton);
        manager.load(playerBartenderClaptrap);
        manager.load(playerClaptrapRefined);
        manager.load(playerButlerRefined);
        manager.load(playerClaptrap3000);
    }

    public void dispose(){
        manager.dispose();
    }

}
