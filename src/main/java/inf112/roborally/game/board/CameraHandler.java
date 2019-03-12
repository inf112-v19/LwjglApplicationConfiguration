package inf112.roborally.game.board;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class CameraHandler implements InputProcessor {

    OrthographicCamera camera;
    float oldX, oldY;
    float cameraX, cameraY;

    public CameraHandler(OrthographicCamera camera){
        this.camera = camera;
    }
    @Override
    public boolean keyDown(int i) {
        return false;
    }

    @Override
    public boolean keyUp(int i) {
        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        cameraX = camera.position.x;
        cameraY = camera.position.y;
        oldX = x;
        oldY = y;
        return false;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchDragged(int x, int y, int pointer) {
        camera.position.x = cameraX - x + oldX;
        camera.position.y = cameraY + y - oldY;
        camera.update();
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }

    @Override
    public boolean scrolled(int i) {
        return false;
    }
}
