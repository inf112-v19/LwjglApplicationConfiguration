package inf112.roborally.game.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import inf112.roborally.game.board.ProgramCard;
import inf112.roborally.game.player.Player;
import inf112.roborally.game.tools.AssMan;

import java.util.ArrayList;
import java.util.List;

public class PlayerStatusDisplay {
    private ArrayList<Player> otherPlayers;
    private Image statusTab;
    private Group tab;
    private Group cards;
    private BitmapFont font;
    private List<PlayerStatus> statuses;
    private boolean hidden;

    public PlayerStatusDisplay(Player player, List<Player> players, final Hud hud) {
        tab = new Group();
        hud.stage.addActor(tab);
        font = AssMan.manager.get(AssMan.FONT_GROTESKIA);
        statuses = new ArrayList<>();
        cards = new Group();
        otherPlayers = new ArrayList<>();
        for (Player maybeOther : players) {
            if (!maybeOther.equals(player)) otherPlayers.add(maybeOther);
        }
        statusTab = new Image(AssMan.manager.get(AssMan.STATUS_DISPLAY_ROBOTS));
        statusTab.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (hidden) tab.setPosition(0, 0);
                else tab.setPosition(60 - statusTab.getWidth(), 0);
                hidden = !hidden;
            }
        });
        tab.addActor(statusTab);
        tab.addActor(cards);
        for (int i = 0; i < otherPlayers.size(); i++)
            statuses.add(new PlayerStatus(i));
        hidden = false;
    }

    public void update() {
        for (PlayerStatus status : statuses) {
            status.update();
        }
    }

    public void addCards(int phase) {
        for (PlayerStatus status : statuses) {
            status.addCard(phase);
        }
    }

    public void clearCards() {
        cards.clear();
    }

    private class PlayerStatus {
        private Player player;
        private ArrayList<Image> lives;
        private Label damageNumber;
        private float x, y;

        public PlayerStatus(int index) {
            this.player = otherPlayers.get(index);
            int size = 256;
            int height = 384;
            Image robotSkin = new Image(new TextureRegion(player.getSprite().getTexture(), size * 2, 0, size, height));
            robotSkin.setSize(84, 113);
            x = 75;
            y = 1080 - robotSkin.getHeight() - (robotSkin.getHeight() + 24.5f) * index;
            robotSkin.setPosition(x, y);
            Image damageToken = new Image(AssMan.manager.get(AssMan.REGISTER_DAMAGE_TOKEN));
            damageToken.setPosition(robotSkin.getX() + 70, robotSkin.getY() + 15);
            damageToken.setSize(32, 32);
            tab.addActor(robotSkin);
            tab.addActor(damageToken);
            lives = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                Image img = new Image(AssMan.manager.get(AssMan.REGISTER_LIFE_TOKEN));
                img.setSize(29, 29);
                img.setPosition(robotSkin.getX() + (29 + 6) * i + 37, robotSkin.getY() - 20);
                lives.add(img);
                tab.addActor(img);
            }
            Label name = new Label(player.getName(), new Label.LabelStyle(font, Color.WHITE));
            name.setFontScale(1);
            name.setPosition(robotSkin.getX() + 100, robotSkin.getY() + 56);
            tab.addActor(name);
            damageNumber = new Label("x" + player.getDamage(), new Label.LabelStyle(font, Color.BLACK));
            damageNumber.setFontScale(1.3f);
            damageNumber.setPosition(damageToken.getX() + damageToken.getWidth() + 3, damageToken.getY());
            tab.addActor(damageNumber);
        }

        public void update() {
            damageNumber.setText("x" + player.getDamage());
            for (int i = 0; i < 3; i++) {
                lives.get(i).setVisible(player.getLives() > i);
            }
        }

        public void addCard(int phase) {
            if (!player.isOperational()) return;

            ProgramCard card = player.getRegisters().getCard(phase);
            card.setUpSkin();
            ProgramCardButton but = new ProgramCardButton(card);
            float k = .3f;
            but.setTransform(true);
            but.setRotation(20);
            but.setScale(k);
            but.setPosition(40, y);
            cards.addActor(but);
        }
    }

    public void dispose(){
        System.out.println("Disposing PlayerStatusDisplay");
        font.dispose();

        //Need to dispose cards/tab?
        cards.clear();
        tab.clear();
    }
}
