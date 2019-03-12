package inf112.roborally.game.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import inf112.roborally.game.RoboRallyGame;

public class CameraDragListener extends DragListener {

    OrthographicCamera camera;
    float oldX, oldY;
    float cameraX, cameraY;

    public CameraDragListener(){
        this.camera = ((RoboRallyGame) Gdx.app.getApplicationListener()).camera;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        cameraX = camera.position.x;
        cameraY = camera.position.y;
        oldX = x;
        oldY = y;
        return true;
    }

    @Override
    public void touchDragged(InputEvent event, float x, float y, int pointer) {
        camera.position.x = cameraX - x + oldX;
        camera.position.y = cameraY - y + oldY;
        camera.update();
    }
}
