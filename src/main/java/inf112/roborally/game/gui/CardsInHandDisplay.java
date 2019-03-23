package inf112.roborally.game.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.objects.Player;

import java.util.ArrayList;

public class CardsInHandDisplay {

    public Stage stage;
    private final Player player;
    private CardVisuals cardVisuals;

    private ArrayList<ImageTextButton> allCards;

    private int posX, posY;

    public CardsInHandDisplay(final Player player, Stage stage) {
        this.player = player;
        this.stage = stage;
        cardVisuals = new CardVisuals();
        allCards = new ArrayList<>();
        Gdx.input.setInputProcessor(stage);
        stage.addListener(((RoboRallyGame) Gdx.app.getApplicationListener()).cameraListener);
    }

    public void updateCardsInHand(final CardDisplay cardDisplay) {
        allCards.clear();
        float scale = 0.5f;
        int j = 0;
        posX = 1250;
        posY = 200;

        for (int i = 0; i < player.getNumberOfCardsInHand(); i++) {
            ImageTextButton cardInHandButton = new ProgramCardButton().makeImageTextButton(player.getCardInHand(i));
            cardInHandButton.setTransform(true);
            cardInHandButton.setScale(scale);
            cardInHandButton.setPosition(posX + 130 * (i % 5), posY - 170 * j);

            final int index = i;
            cardInHandButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    player.getRegisters().placeCard(index);
                    cardDisplay.updateCards();
                }
            });

            allCards.add(cardInHandButton);
            stage.addActor(cardInHandButton);

            if (i % 5 == 4) {
                j++;
                posX += 50;
            }
        }
    }

    //Just trying out another way to remove objects - instead of using instanceof
    public void removeAllCardsInHand() {
        for (Actor button : stage.getActors()) {
            for (int i = 0; i < allCards.size(); i++) {
                if (button == allCards.get(i)) {
                    button.remove();
                }
            }
        }
    }

    public Player getPlayer() {
        return player;
    }

    public void dispose() {
        System.out.println("disposing CardsInHandDisplay");
        stage.dispose();
        cardVisuals.dispose();
    }
}