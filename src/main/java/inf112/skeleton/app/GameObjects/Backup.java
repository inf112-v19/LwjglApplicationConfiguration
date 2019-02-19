package inf112.skeleton.app.GameObjects;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import inf112.skeleton.app.Main;

public class Backup extends GameObject {

    private Sprite sprite;

    public Backup(int x, int y){
        super(x, y);
        sprite = new Sprite(new Texture("assets/robot/backup.png"));
        sprite.setSize(Main.TILE_LENGTH, Main.TILE_LENGTH);
        updateSprite();
    }

    public void movePlayer(Player player){
        player.move(getX(), getY());
    }

    public void updateSprite(){
        sprite.setPosition(getX(), getY());
    }

    @Override
    public Sprite getSprite() {
        return sprite;
    }
}
