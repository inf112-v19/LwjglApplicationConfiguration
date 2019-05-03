package inf112.roborally.game.player;

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
        for(int i = 0; i < player.getMaxDamage()*3; i++) {
            player.takeDamage();
            player.respawn();
        }
        assertEquals(0, player.getLives());
    }

    @Test
    public void confirmThatBackupIsRemovedFromEarlierPosition(){
        player.move(1);
        backup.moveToPosition(player.position);
        assertEquals(player.position, backup.position);
        player.reverse();
        assertNotEquals(player.position, backup.position);
    }

    @Test
    public void moveBackupTwoTimesWorks(){
        player.move(2);
        backup.moveToPosition(player.position);
        player.move(1);
        backup.moveToPosition(player.position);
        assertEquals(player.position, backup.position);
    }

    @Test
    public void moveBackupThreeTimesAndBackupLocationIsTheNewestLocation(){
        for(int i = 0; i < 3; i++) {
            player.move(1);
            backup.moveToPosition(player.position);
        }
        assertEquals(player.position, backup.position);
    }

    @Test
    public void moveBackupFifteenTimesAndBackupLocationIsTheNewestLocationOnly(){
        for(int i = 0; i < 15; i++) {
            player.move(1);
            backup.moveToPosition(player.position);
        }
        assertEquals(player.position, backup.position);

        for(int i = 0; i < 15; i++) {
            player.reverse();
            assertNotEquals(player.position, backup.position);
        }
    }

    @Test
    public void positionAndGetMethodIsEqual(){
        assertEquals(backup.getX(), backup.position.getX());
        assertEquals(backup.getY(), backup.position.getY());
    }

    @Test
    public void backupMoveMethodWorks(){
        player.move(2);
        backup.move(player.position.getX(), player.position.getY());
        assertEquals(player.position, backup.position);
    }

    @Test
    public void newBackupAtSameLocationIsDifferent(){
        Player player2 = new Player(0, 0, 3);
        Backup backup2 = player2.getBackup();
        assertNotEquals(backup, backup2);
    }
}
