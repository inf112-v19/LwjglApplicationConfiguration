# LwjglApplicationConfiguration
Group 5.1

# INF112 roborally game
Much more than a simple skeleton with libgdx. 
### Manual testing:
How to do testing for the TestScreen:
When running the game you will get prompted to 'PRESS ENTER'. Hitting 'T' instead will launch a test
screen where you can test that ProgramCard and ProgramRegister GUI works as it should. Clicking the 
submit button when you have chosen five cards is not supposed to do anything here.
As instructed on the test screen, pressing S will add a card to the register. Pressing D will make
the robot take a damage. Pressing R will empty all the registers, returning all cards to the hand.
Clicking on the clear button does the same as pressing R. Clicking on a specific card in the register will 
remove it from the register and return it to the player hand, if it is not locked that is.

How to test that locked cards in the register do not return:
    - (1) Fill up all five registers by pressing S five times.
    - (2) Press D at least five times to take enough damage to lock a register.
    - (3) Check that pressing R will only return the cards that are not locked.
    - Instead of pressing R in step (3) you can click on the individual card(s) you want to return to the hand.
      See that locked cards are still not removed from the locked registers(s).
    
      
How to check that the player is unable interact with the game once dead:
    - (1) Choose 5 cards (optional, but in the game you are not able to take damage without picking five cards).
    - (2) Press D 30+ times. Should not be able to interact with the game since the player is dead.




## Known bugs
- Currently throws "WARNING: An illegal reflective access operation has occurred", 
  when the java version used is >8. This has no effect on function or performance, and is just a warning.
