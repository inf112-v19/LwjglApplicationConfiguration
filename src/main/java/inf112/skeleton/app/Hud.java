package inf112.skeleton.app;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;



public class Hud {

    public Stage stage;
    private com.badlogic.gdx.utils.viewport.Viewport viewport;

    private Integer lives;
    private Integer cards;
    private Integer damage;
    Label livesLabel;
    Label damageLabel;

    public Hud(SpriteBatch sb, Player player){
        lives = player.getLives();
        damage = player.getDamage();
        cards = 0;


        viewport = new FitViewport(1920, 1080, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        livesLabel = new Label(Integer.toString(lives), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        damageLabel = new Label(Integer.toString(damage), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        table.add(livesLabel).width(50).padTop(10);
        table.add(damageLabel).width(50).padTop(10);

        stage.addActor(table);


    }


}
