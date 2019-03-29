# LwjglApplicationConfiguration
Group 5.1

# INF112 roborally game
Much more than a simple skeleton with libgdx. 
### Manual testing:
How to do testing for the TestScreen:
When running Main.java you will get prompted to 'PRESS ENTER'. Hitting 'T' instead will launch a test
screen where you can test that ProgramCard and ProgramRegister GUI works as it should. Clicking the 
submit button when you have chosen five cards is not supposed to do anything here.
As instructed on the test screen, pressing S will add a card to the register. Pressing D will make
the robot take a damage. Pressing R will empty all the registers, returning all cards to the hand.
Clicking on the clear button does the same as pressing R. Clicking on a specific card in the register will 
remove it from the register and return it to the player hand, if it is not locked that is.

How to test that locked cards in the register do not return:
  * (1) Fill up all five registers by pressing S five times.
  * (2) Press D at least five times to take enough damage to lock a register.
  * (3) Check that pressing R will only return the cards that are not locked.
  * Instead of pressing R in step (3) you can click on the individual cards you want to return to the hand.
      See that locked cards are still not removed from the locked registers.
    
      
How to check that the player is unable interact with the game once dead:
  * (1) Choose 5 cards (optional, but in the game you are not able to take damage without picking five cards).
  * (2) Press D 30+ times. Should not be able to interact with the game since the player is dead.




Testing of the main game:
Run Main.java and press Enter when prompted to get to the main game.

Conveyor belts:
 * Check that the player is moved two tiles when ending its turn on a blue conveyor belt.
 * Check that player is moved one tile when ending its turn on a red conveyor belt. 

When player ending its turn on a gyro:
 * Check that the player rotates the player once in the direction that the gyro is showing.
    
Walls stops the player:
 * Check that trying to move forward/backward into a wall will not let the player move through the wall.

Players get hit by board laser:
 * Ending a phase in a laser causes the robot to take damage. Loses a life if the player has taken 10 damage.
    
Players get hit by other players' laser:
 * Same as board laser, but the laser is shot by other robots. End a phase in front of a robot and you take damage. 
    
Walking into the void (out of the map):
 * The player loses a life and is not returned to the board before the round is over.
 * Check that the player scream sound is only played once.
 * If not dead: all old cards will be gone and the player should be ready for the next with new cards.
 * If dead, meaning the player has no more lives: the player should not be returned to the map and should 
   not be dealt cards anymore.
      
    
Checking win condition:
 * To win you need to walk into flag 1, followed by flag 2 and then finally ending a phase on flag 3 should 
   bring up the winner screen.
     
Testing power down:
     Pressing the power down button causes you to power down after you have played the current round. This means 
     the robot will not move the next round and all damage taken should be removed after the power down has finished.
 * Pressing the power down button before clicking the submit button will result in going into power down mode.
      
## Known bugs
- Currently throws "WARNING: An illegal reflective access operation has occurred", 
  when the java version used is >8. This has no effect on function or performance, and is just a warning.
