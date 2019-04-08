package inf112.roborally.game.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import inf112.roborally.game.player.Player;
import inf112.roborally.game.tools.AssMan;

import java.util.ArrayList;
import java.util.List;

public class PlayerStatusDisplay {
    private ArrayList<Player> otherPlayers;
    private Image bg;
    private Group statusBoard;
    private boolean hidden;
    private BitmapFont font;
    private List<PlayerStatus> statuses;

    public PlayerStatusDisplay(Player player, List<Player> players, final Hud hud) {
        font = new BitmapFont();
        statuses = new ArrayList<>();
        statusBoard = new Group();
        hud.stage.addActor(statusBoard);
        otherPlayers = new ArrayList<>();
        for (Player maybeOther : players) {
            if (!maybeOther.equals(player)) otherPlayers.add(maybeOther);
        }
        hidden = false;
        bg = new Image(new Texture("assets/register/robot_status_display.png"));
        bg.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (hidden) {
                    statusBoard.setPosition(0, 0);
                }
                else {
                    statusBoard.setPosition(60 - bg.getWidth(), 0);
                }
                hidden = !hidden;
            }
        });
        statusBoard.addActor(bg);
        for(int i = 0; i < otherPlayers.size(); i++)
            statuses.add(new PlayerStatus(i));
    }

    public void update() {
        for(PlayerStatus status : statuses) status.update();
    }

    private class PlayerStatus {
        private Player player;
        private ArrayList<Image> lives;
        private Label damageNumber;

        public PlayerStatus(int index) {
            this.player = otherPlayers.get(index);
            int size = 256;
            int height = 384;
            Image robotSkin = new Image(new TextureRegion(player.getSprite().getTexture(), size * 2, 0, size, height));
            robotSkin.setSize(84, 113);
            robotSkin.setPosition(75, 1080 - robotSkin.getHeight() - (robotSkin.getHeight()+24.5f)*index);
            Image damageToken = new Image(AssMan.manager.get(AssMan.REGISTER_DAMAGE_TOKEN));
            damageToken.setPosition(robotSkin.getX() + 70, robotSkin.getY() + 15);
            damageToken.setSize(32, 32);
            statusBoard.addActor(robotSkin);
            statusBoard.addActor(damageToken);
            lives = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                Image img = new Image(AssMan.manager.get(AssMan.REGISTER_LIFE_TOKEN));
                img.setSize(29, 29);
                img.setPosition(robotSkin.getX() + (29 + 6) * i + 37, robotSkin.getY() - 20);
                lives.add(img);
                statusBoard.addActor(img);
            }
            Label name = new Label(player.getName(), new Label.LabelStyle(font, Color.BLACK));
            name.setFontScale(2);
            name.setPosition(robotSkin.getX() + 100, robotSkin.getY() + 63);
            statusBoard.addActor(name);
            damageNumber = new Label("x" + player.getDamage(), new Label.LabelStyle(font, Color.BLACK));
            damageNumber.setFontScale(2.5f);
            damageNumber.setPosition(damageToken.getX() + damageToken.getWidth() + 3, damageToken.getY() + 8);
            statusBoard.addActor(damageNumber);
        }

        public void update() {
            damageNumber.setText("x" + player.getDamage());
            for (int i = 0; i < 3; i++) {
                lives.get(i).setVisible(player.getLives() > i);
            }
        }
    }
}
