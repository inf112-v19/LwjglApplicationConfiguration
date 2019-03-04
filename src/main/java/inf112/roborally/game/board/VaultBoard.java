package inf112.roborally.game.board;

import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Array;
import inf112.roborally.game.Main;
import inf112.roborally.game.RoboRallyGame;
import inf112.roborally.game.enums.Direction;
import inf112.roborally.game.objects.Flag;
import inf112.roborally.game.objects.Player;
import inf112.roborally.game.objects.RepairSite;

import java.util.ArrayList;

import static inf112.roborally.game.screens.GameScreen.mapPath;

public class VaultBoard extends Board {


    public VaultBoard() {
        flags = new Array<>();
        flags.add(new Flag(1, 10, 1));
        flags.add(new Flag(6, 2, 2));
        flags.add(new Flag(6, 10, 3));

        repairSites = new ArrayList<>();
        repairSites.add(new RepairSite(5, 2));

        loader = new TmxMapLoader();
        TmxMapLoader.Parameters parameters = new TmxMapLoader.Parameters();
        parameters.flipY = true;
        map = loader.load(RoboRallyGame.VAULT, parameters);
        mapRenderer = new OrthogonalTiledMapRenderer(map, Main.UNIT_SCALE);
        createLayers();



        Player player1 = new Player("Player1", 6, 6, Direction.NORTH, flags.size);

        Player player2 = new Player("Player2", 5, 7, Direction.SOUTH, flags.size);

        players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
    }


}
