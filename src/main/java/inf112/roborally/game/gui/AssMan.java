package inf112.roborally.game.gui;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class AssMan{

    public static final AssetManager manager = new AssetManager();
    public static final AssetDescriptor<Texture>  buttonSubmit
            = new AssetDescriptor<>("assets/cards/buttonSubmit.png", Texture.class);

    public static final AssetDescriptor<Texture>  buttonSubmitGrey
            = new AssetDescriptor<>("assets/cards/buttonSubmitGrey.png", Texture.class);

    public static final AssetDescriptor<Texture>  buttonClear
            = new AssetDescriptor<>("assets/cards/buttonClear.png", Texture.class);

    public static final AssetDescriptor<Texture> settingsButton
            = new AssetDescriptor<>("assets/img/settingsbtn.png", Texture.class);


    public static void load(){
        manager.load(buttonSubmit);
        manager.load(buttonSubmitGrey);
        manager.load(buttonClear);
        manager.load(settingsButton);
    }

    public void dispose(){
        manager.dispose();
    }

}
