# LwjglApplicationConfiguration
Group 5.1

## Status
[![Build Status](https://travis-ci.com/inf112-v19/LwjglApplicationConfiguration.png)](https://travis-ci.com/inf112-v19/LwjglApplicationConfiguration)

# INF112 roborally game
Much more than a simple skeleton with libgdx. 
### Manual testing:
### GUI - How to do testing for the TestScreen:
When running Main.java you will get prompted to 'PRESS ENTER'. Hitting 'T' instead will launch a test
screen where you can test that ProgramCard and ProgramRegister GUI works as it should. Clicking the 
submit button when you have chosen five cards is not supposed to do anything here.

As instructed on the test screen, pressing S will add a card to the register. Pressing D will make
the robot take one damage. Pressing R should empty all the registers, returning all cards to the hand.
Clicking on the clear button should do the same as pressing R. Clicking on a specific card in the register should 
remove it from the register and return it to the player hand, if it is not locked that is.

##### How to test that locked cards in the register do not return:
  1. Fill up all five registers by pressing S five times.
  2. Press D at least five times to take enough damage to lock a register.
  3. Check that pressing R will only return the cards that are not locked.
  * Instead of pressing R in step (3) you can click on the individual cards to return them.
      Check that locked cards are still not removed from the locked registers.
      
      
##### How to check that the player is unable interact with the game when dead:
  1. Choose 5 cards (optional, but in the game you are not able to take damage without picking five cards).
  2. Press 'D' 30+ times. Should not be able to interact with the game since the player is dead.
  
### Tiles - How to test each of the different tiles:
* You can do this in the main game, but its much easier to test this on the testMap.
* In the GameScreen class there is a boolean called testMap, set this to true and run Main.java.
* This will begin the game on the test map.

##### Controls:
* W - Forward one tile
* A - Rotate left
* S - Backup one tile
* D - Rotate right
* Each input counts as one phase, which means that the tiles do their thing right after your input

##### Things to test here:
* The different types of tiles, you can find deeper information about each tile further down.
* You fire a laser at the end of each phase
* Other robots fire a laser at the end of each phase
* Lasers fired by robots do not penetrate walls
* Pushing of robots
* Warning: The testmap does not have garbage cleaning, and respawning.(To test this, see instructions further down)
* This means that dead robot's texture wont be removed from the map, but you wont be able to interact with them
* Also if you die, you wont respawn aswell.


### Main Game - How to do testing for the main game:
 Run Main.java and press Enter when prompted to get to the main game.


##### Conveyor belts:
 * Check that the player is moved two tiles when ending its turn on a blue conveyor belt.
 * Check that player is moved one tile when ending its turn on a red conveyor belt. 


##### When player ending its turn on a gyro:
 * Check that the gyro rotates the player once in the direction that the gyro is showing.
    
    
##### Walls stops the player:
 * Check that trying to move forward/backward into a wall will not let the player move through a wall.


##### Players get hit by board laser:
 * Ending a phase in a laser should cause the robot to take damage. Should lose a life the player has taken
 max damage (visualised by the triangular damage indicators above the health bar on the program register).

    
##### Players get hit by other players' laser:
 * Same as the board laser, but the laser is shot by other robots. End a phase in front of a robot and you take damage. 
 * Ending a phase in front of another robot should cause you to take one damage. If you are facing a robot then the
  robot you are facing should take one damage (check console for message saying e.g. "Player1 shoots testBot2").
    
##### Walking into the void (out of the map):
 * Check that the player loses a life and is not returned to the board before the round is over.
 * Check that the player scream sound is only played once.
 * If not dead: all old cards should be gone and the player should be ready for the next round with new cards.
 * If dead, meaning the player has no more lives: the player should not be returned to the map and should 
   not be dealt any cards.
      
    
##### Checking win condition:
 * To win you need to walk into flag 1, followed by flag 2 and then finally ending a phase on flag 3 should 
   bring up the winner screen.
     
     
##### Testing power down:
   Pressing the power down button should cause you to power down after you have played the current round. The robot
    should not move the next round and all damage taken should be removed when the power down is finished.
 * Pressing the power down button before clicking the submit button should result in going into power down mode.
      
      
    
      
#### Settings button testing for game screen and test screen
 * Pressing 'B' or clicking on the settings button located to the bottom right of the screen should
 bring up the settings screen. 
 * You should be able to mute/unmute the game by pressing M.
 * Pressing 'B' or Escape when in settings screen should return you to the screen you came from.
 * Pressing 'N' should create a new game. You should end up at the Menu Screen where you are prompted to 'PRESS ENTER'. 
   - Pressing Enter: A new game should be initiated and all progress you potentially
    had in an earlier game should be gone.
   
    * Pressing 'T' should bring up the test screen instead.
      
      
### How to choose flag location and skin on robot:
When prompted to press Enter at startup, press 'S' instead. This should bring up a page where you can click on the skin
you want to use. After choosing a skin, click on the map that is to the right of the black vertical line to place 
a flag. After placing all three flags the game should start with your chosen skin and flag locations.

Pressing 'T' instead of choosing skin and flag locations should start the game with the preset values.
## Known bugs
- Currently throws "WARNING: An illegal reflective access operation has occurred", 
  when the java version used is >8. This has no effect on function or performance, and is just a warning.
