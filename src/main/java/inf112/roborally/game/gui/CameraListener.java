package inf112.roborally.game.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;

public class CameraListener extends DragListener {
    private OrthographicCamera camera;
    private float initialX, initialY;
    private float cameraX, cameraY;

    /**
     * Handles all dynamicCamera movement. Needs constant calls to updateCards for zoom to work
     */
    public CameraListener(OrthographicCamera camera) {
        this.camera = camera;
    }

    /**
     * Handles zooming in and out
     */
    public void update() {
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_1) && camera.zoom > 0.1) {
            camera.zoom -= 0.01;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.NUM_2) && camera.zoom < 0.6f) {
            camera.zoom += 0.01;
        }
        else {
            return; // if we return here the dynamicCamera is only updated when it needs to be updated
        }
        camera.update();
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        cameraX = camera.position.x;
        cameraY = camera.position.y;
        initialX = x;
        initialY = y;
        return true;
    }

    @Override
    public void touchDragged(InputEvent event, float x, float y, int pointer) {
        float newCamPosX = cameraX + initialX - x;
        float newCamPosY = cameraY + initialY - y;

        if (newCamPosX > -293 && newCamPosX < 842)
            camera.position.x = newCamPosX;
        else
            initialX = newCamPosX;

        if (newCamPosY > -87 && newCamPosY < 537)
            camera.position.y = newCamPosY;
        else
            initialY = newCamPosY;

        camera.update();

        System.out.println(newCamPosX + ", " + newCamPosY);
    }
}
