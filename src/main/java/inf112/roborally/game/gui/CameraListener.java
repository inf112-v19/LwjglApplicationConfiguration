package inf112.roborally.game.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;

public class CameraListener extends DragListener {
    private static final float MIN_X = -293;
    private static final float MIN_Y = -87;
    private static final float MAX_X = 842;
    private static final float MAX_Y = 537;

    private OrthographicCamera camera;
    private float initialX, initialY;
    private float cameraX, cameraY;

    /**
     * Handles all dynamicCamera movement. Needs constant calls to updateCards for zoom to work
     */
    public CameraListener(OrthographicCamera camera) {
        this.camera = camera;
    }

    public void updateZoom() {
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_1) && camera.zoom > 0.1) {
            camera.zoom -= 0.01;
        } else if (Gdx.input.isKeyPressed(Input.Keys.NUM_2) && camera.zoom < 0.6f) {
            camera.zoom += 0.01;
        } else {
            return;
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
        float moveX = initialX - x;
        float moveY = initialY - y;
        float newCamPosX = cameraX + moveX;
        float newCamPosY = cameraY + moveY;

        if (newCamPosX > MIN_X && newCamPosX < MAX_X) {
            camera.position.x = newCamPosX;
        } else {
            cameraX = camera.position.x;
            initialX = x;
        }
        if (newCamPosY > MIN_Y && newCamPosY < MAX_Y) {
            camera.position.y = newCamPosY;
        } else {
            cameraY = camera.position.y;
            initialY = y;
        }
        camera.update();
    }
}
