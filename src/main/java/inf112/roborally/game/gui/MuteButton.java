package inf112.roborally.game.gui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class MuteButton extends ImageButton {

    public MuteButton(TextureRegionDrawable texture, final SoundSettings soundsettings) {
        super(texture);

        addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                System.out.println("MuteButton clicked - 1");
                soundsettings.testCallThisWhenClicked();
            }
        });
    }
}
