package inf112.roborally.game.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import inf112.roborally.game.player.Player;
import inf112.roborally.game.player.ProgramCard;
import inf112.roborally.game.tools.AssMan;
import inf112.roborally.game.tools.ImageTool;
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
        statusTab.setY(138 * 7 - 138 * (players.size() - 1));
        ImageTool.setAlpha(statusTab, .8f);
        tab.addActor(statusTab);
        tab.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (hidden) tab.setPosition(0, 0);
                else tab.setPosition(33 - statusTab.getWidth(), 0);
                hidden = !hidden;
            }
        });
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

    public void dispose() {
        font.dispose();

        for (Actor card : cards.getChildren()) {
            if (card instanceof ProgramCardButton) {
                ((ProgramCardButton) card).dispose();
            }
        }
        cards.clear();

        for (Actor cards : tab.getChildren()) {
            if (cards instanceof ProgramCardButton) {
                ((ProgramCardButton) cards).dispose();
            }
        }
        tab.clear();
    }

    private class PlayerStatus {
        private final Image robotSkin;
        private Player player;
        private ArrayList<Image> lives;
        private Label damageNumber;
        private float x, y;
        private Image targetFlag;

        PlayerStatus(int index) {
            this.player = otherPlayers.get(index);
            int size = 256;
            int height = 384;
            // add the skin of the current robot and adjust its position on the display board
            // robotSkin is used as an anchor point for the rest of the items
            robotSkin = new Image(new TextureRegion(player.getSprite().getTexture(), size * 2, 0, size, height));
            robotSkin.setSize(84, 113);
            x = 75;
            y = 1080 - robotSkin.getHeight() - (robotSkin.getHeight() + 24.5f) * index;
            robotSkin.setPosition(x, y);
            tab.addActor(robotSkin);
            // add damage tokens
            Image damageToken = new Image(AssMan.manager.get(AssMan.REGISTER_DAMAGE_TOKEN));
            damageToken.setPosition(robotSkin.getX() + 70, robotSkin.getY() + 15);
            damageToken.setSize(32, 32);
            tab.addActor(damageToken);
            // add life tokens:
            lives = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                Image img = new Image(AssMan.manager.get(AssMan.REGISTER_LIFE_TOKEN));
                img.setSize(29, 29);
                img.setPosition(robotSkin.getX() + (29 + 6) * i + 37, robotSkin.getY() - 20);
                lives.add(img);
                tab.addActor(img);
            }
            // add name label:
            Label name = new Label(player.getName(), new Label.LabelStyle(font, Color.WHITE));
            name.setFontScale(1);
            name.setPosition(robotSkin.getX() + 100, robotSkin.getY() + 56);
            tab.addActor(name);
            // add damage number:
            damageNumber = new Label("x" + player.getDamage(), new Label.LabelStyle(font, Color.BLACK));
            damageNumber.setFontScale(1.3f);
            damageNumber.setPosition(damageToken.getX() + damageToken.getWidth() + 3, damageToken.getY());
            tab.addActor(damageNumber);
            // add the current target flag
            targetFlag = new Image(AssMan.getFlagAtlasRegion(player.getTargetFlag()));
            targetFlag.setScale(.18f);
            targetFlag.setPosition(robotSkin.getX() + robotSkin.getWidth() + 58, robotSkin.getY() - 20);
            tab.addActor(targetFlag);
        }

        public void update() {
            damageNumber.setText("x" + player.getDamage());
            for (int i = 0; i < 3; i++) {
                lives.get(i).setVisible(player.getLives() > i);
            }
            targetFlag.setDrawable(new TextureRegionDrawable(AssMan.getFlagAtlasRegion(player.getTargetFlag())));
            if (player.outOfLives() || player.isDestroyed()) {
                ImageTool.setAlpha(robotSkin, .5f);
            } else {
                ImageTool.setAlpha(robotSkin, 1);
            }
        }

        void addCard(int phase) {
            if (!player.isOperational() || player.isGameOver() || player.isPoweredDown()) return;

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
}
