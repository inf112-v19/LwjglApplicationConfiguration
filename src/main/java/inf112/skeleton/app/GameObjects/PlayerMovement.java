package inf112.skeleton.app.GameObjects;

import com.badlogic.gdx.graphics.Texture;
import inf112.skeleton.app.Main;

public class PlayerMovement extends MoveableGameObject{

    private Player player;

    public PlayerMovement(int x, int y, String filePath, Player player){
        super(x, y, filePath);
        this.player = player;
        loadVisualRepresentation();
    }

    /** ONLY FOR TESTING */
    public PlayerMovement(int x, int y){
        super(x, y, "assets/robot/tvBot.png");
        loadVisualRepresentation();
    }

    public void loadVisualRepresentation() {
        sprite.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        sprite.setSize(Main.TILE_LENGTH, Main.TILE_LENGTH);
        sprite.setOriginCenter();
        sprite.setRotation(rotationDegree);
    }

    public void moveToBackup() {
        move(player.getBackup().getX(), player.getBackup().getY());
    }

}