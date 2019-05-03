package inf112.roborally.game.tools;


import com.badlogic.gdx.scenes.scene2d.Actor;

public class ImageTool {

    // because Image does not have a setAlpha method and using setColor over and over is tedious
    public static void setAlpha(Actor actor, float alpha) {
        actor.setColor(actor.getColor().a, actor.getColor().g, actor.getColor().b, alpha);
    }
}

