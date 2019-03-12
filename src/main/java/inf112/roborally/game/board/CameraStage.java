package inf112.roborally.game.board;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import inf112.roborally.game.RoboRallyGame;

public class CameraStage extends Stage {
    OrthographicCamera camera;
    float oldX, oldY;
    float cameraX, cameraY;

    public CameraStage(FitViewport viewport, SpriteBatch batch) {
        super(viewport, batch);
        camera = ((RoboRallyGame) Gdx.app.getApplicationListener()).camera;

        addListener(new DragListener() {
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
        });
    }
}
