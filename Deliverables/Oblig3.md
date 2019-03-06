# Mandatory 3 - LwjglApplicationConfiguration

## About Commits
Commits are somewhat uneven. Christian has been on holiday for a week and Morten has been stuck at home alot and had nothing else to do than work on the project.

## Part 1: Project and project structure
- The roles in the team work fine. We don't see the need to update/change the roles within the team, neither create new ones. 
For us; a team leader is a person that keeps us on track on deadlines and assignments, while a customer contact is a person that has a broad view over the project and a deeper understanding over how the classes work together.
- We are satisfied with how the teamwork has been so far. Whenever there is time sensitive work that needs to be done, we find time to do that.
Last week we started dividing more tasks to each of us individual. This has increased the amount of work we are able to do, since we no longer overlap with each other.
- The group dynamic has been great so far, we have different kinds of expertise. Which enables us to do certain tasks better.
- Our group mainly communicates on Slack. There we call for meetings, let people know of changes, and problems. And share relevant information to the rest of the group.
The communication in our group has been great aswell, we show up at meetings and help each other when we have issues. 
- As we mentioned in our earlier presentation; at the beginning of the project we started with a "free-for-all" when doing tasks. This caused that the majority of the project to be written by a few members,
which meant that some members didnt understand how the system worked, and had to read up on it. Last week we started dividing the tasks, and we now have better understanding of the project altogether.
- There is a big difference in commits because of how we started the project, mainly because of the "free-for-all". But also since this is a project where it is actually exciting to work on.
We started the project with having one branch for each person and then merging that into the "dev" branch whenever we finished a task. And then merged "dev" into master when we finished major changes and its stable.
The commits to master doesnt reflect all of our works. 
- We get together every friday and wednesday, sometimes mondays aswell. But we usually just sit together and work on the project together, we wouldnt classify that as a "meeting".
We associate meetings as a time when we discuss certain issues together and plan how to work ahead. Which means we have not had a meeting since 8th of february.
- Follow up points:
    - Better communication on Slack
    - Get together more often if needed
    - Continue dividing tasks for each person
    - Commit better and more often

## Part 2: Requirements
- Requirements given by customer:
    - Possible to get all types of movement-cards
    - Deal nine cards
    - Execute program by chosen cards
    - Choose five cards(Confirm/Cancel)
    - Visit flags
    - If robot walks off the board, it dies and respawns at last backup
    - Update backup if robot ends phase on cogwheel tile
    - Move backup when visiting flag
    - Able to play a full round with all phases
    - Deal new cards on a new round

- Requirements we made given requirements by customer:
    - Create ProgramRegister class to keep track on how many registers a player have available
    - Complete a HUD which shows the cards in your hand, and make them clickable
    - Add visual representations to programcards
    - Be able to play an entire round with all phases
    - Visit flags and change backup location
    - Finish implementing programcards
    - Execute program by chosen cards
    - Choose five cards and confirm/cancel choice
    - Make a player permamently die when loses all lives.
    - Connect playermovement to programcards
    - Add new TiledMap textures
    - Animate the game
- We prioritized the core mechanics of the game. Mainly clickable cards, playermovement from programcards, visit flags and backups and play an entire round with all phases.
- If changes are made from the requirements given by the customer, its mainly because our system depends on specific classes to work before we can do other tasks.
- To verify that a requirement is met, we ask other members of the team if they are satisfied with the product. 
If they are, we consider the task/requirement done.
- Since last time we have finished: 
    - ProgramRegister
    - Hud (display cards in player hand and display player program register)
    - Able to play an entire round
    - Execute program cards in program register / Connect playermovement to program cards
    - Chose five cards(confirm/cancel selection)
    - GameState Enum
    - BoardLogic
    - MenuScreen
    - TestScreen for Gui
    
## Part 3: Code
Project:
- The project 
Tests:
- We have a few JUnit tests that make sure we don't break existing code when implementing new features.
- We have implemented a test screen for debugging gui. In RoborallyGame class set debugging to true to launch the test screen.
