package inf112.roborally.game;

import inf112.roborally.game.objects.Player;
import inf112.roborally.game.objects.RepairSite;
import org.junit.Before;

import java.util.ArrayList;
import java.util.List;

public class BackupTest {
    private Player player;
    private List<RepairSite> repairSites;

    @Before
    public void setup() {
        player = new Player(0, 0);
        repairSites = new ArrayList<>();
        repairSites.add(new RepairSite(5, 2));
    }







}
