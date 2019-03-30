package inf112.roborally.game.objects;

import inf112.roborally.game.enums.Direction;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;

/**
 * Tests for backup object
 */
public class BackupTest {

    private Player player;
    private Backup backup;

    @Before
    public void setup(){
        player = new Player(0, 0, 3);
        backup = player.getBackup();
        player.setDirection(Direction.NORTH);
    }

    @Test
    public void checkThatBackupIsUnderRobotAtSpawn(){
        assertEquals(player.position, backup.position);
    }

    @Test
    public void backupPosDoesNotEqualPlayerPos(){
        player.move(1);
        assertFalse(player.position.equals(backup.position));
    }

    @Test
    public void playerMovesBackToBackupWhenLosingALife(){
        player.move(1);
        for(int i = 0; i < player.getMaxDamage(); i++)
            player.takeDamage();
        player.respawn(); //moves the player to the backup
        assertEquals(player.position, backup.position);
    }

    @Test
    public void playerMovesBackupToItsPosition(){
        player.move(3);
        assertFalse(player.position.equals(backup.position));
        player.getBackup().moveToPosition(player.position);
        assertTrue(player.position.equals(backup.position));
    }

    @Test
    public void playerTryingToMoveToBackupWhenDeadDoesNotWork(){
        player.move(1);
        for(int i = 0; i < player.getMaxDamage()*3; i++)
            player.takeDamage();
        assertEquals(0, player.getLives());
        player.respawn();
        assertNotEquals(player.position, backup.position);
    }
}
