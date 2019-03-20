package inf112.roborally.game.gui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SoundSettingsVisuals extends Sprite {

    Texture muteBtnTexture;
    TextureRegion muteBtbBack;

    public SoundSettingsVisuals() {
        super();
//            setBounds(500,500, 100, 100);
//            setSize(50, 50);
//        setPosition(1000,1000);
        muteBtnTexture = new Texture("assets/img/mute.png");
        muteBtbBack = new TextureRegion(muteBtnTexture);
    }

    public void dispose() {
        System.out.println("disposing SoundSettingsVisuals");
        muteBtnTexture.dispose();
        muteBtbBack.getTexture().dispose();
    }

}
