# Mandatory exercise 4

## Part 1: Project and project structure

- Introduce a test role. Choose a person who can have a test role (it can be the same person as the teamlead or customer responsibility, or it can be someone else). This person will have the main responsibility for testing the application and can delegate both tasks to be implemented / executed, and find a strategy for testing (eg what to test manually and what is to be tested automatically) in collaboration with the rest of the team. If the team uses manual tests, you have to provide a detailed description of them.
- Are there any experiences either team-wise or regarding project methodology worth mentioning? Does the team think the choices you've made are good? If not, what can you do differently to improve the way the team works?
- How are the group dynamics?
- How does communication work for you?
- Make a brief retrospective in which you consider what you have done so far and what can be improved. This is about project structure, not code. You can of course discuss code, but this is not about error correction, but about how to work and communicate.
- Agree on maximum three improvement points from the retrospective, which will be followed up during the next sprint.
- Reports from meetings since the previous delivery must be added.
- During evaluation, it will be emphasized that everyone contribute to the code base. During this delivery, it is important that team members who have not previously programmed much should contribute to the code base. Those who have not coded much should do more of it, while those who have only coded should try to do something else (eg design). It's wise to cooperate, but so so in a way that the one who has the least experience is the one who sits with the machine and does the actual work. Write briefly on how you handle this to ensure the best possible transfer of expertise. Remember that anyone who is least experienced needs to spend some time thinking and exploring new areas, and that everyone has valuable input even with little experience.
- Group leader must have access to project board. Groups that do not have a project board on github (eg Trello) must attach a link to their solution.


## Part 2: requirements

The rest of the requirements for the solution,are given in the following list. Since everyone works at different pace, there will be some difference regarding which requirements can be met, but everyone should be able to reach MVP during the project. Those who have reached the MVP can choose to implement more features, but we also recommend taking time to get the functionality really good (removing errors and fixing details).

When you start with this, explain what you choose and why.

### List of requirements to reach MVP:
- One must be able to play a complete round
- One must be able to win the game game by visiting the last flag (completing a game) 
- There must be lasers on the board
- There must be holes on the board
- Injury mechanisms (player gets fewer cards in case of injury)
- Game mechanisms to shoot other players within reach with laser pointing straight ahead
- Acting assembly line on the board that moves the robots
- Functioning gyros on the board that move the robots
- Game over after 3 lost lives
- Multiplayer over LAN or Internet (don't need to do anything fancy here but must be able to play on different machines against each other)
- Power down
- Assembly line that runs at double speed
- Options cards
- Place flags even before the game starts
- Assemble different boards to larger spills
- Play against AI (single-player mode), possibly play against random robots

After these requirements have been implemented, the following requirements can be considered. These are very rough requirements that you have to work with to find out which will be a good addition and how. If so, choose a single feature and work with that one until it's finished before you move on to the next. Teams can also define their own requirements, but then you have to justify why you think it is more important than the ones listed below and get approval for going in a different direction.

- Scoring system: remember how many games you have won, possibly what the different placements you have had
- More advanced scoring system: the number of moves to reach the flag? Maybe have a game mode that is on a few types of boards with fixed flags where the point is to get ahead as quickly as possible?
- Several types of routes on the board (eg teleports, pushbuttons that fire off at different stages) 
- Create your own boards
- Generate boards
- Save games along the way so that it is possible to quit and play games again later
- Play over the Internet and not just LAN
- First-player-view of when the robot moves (possibly zoomed in so that it becomes more "video-feeling" of the whole)
- "Replay" of important moments such as walking in death / winning the game, etc.
- Simpler variant of replay: show where the robot was and how it ended up as it is after all the phases are over (possibly show this to all players on the board)
- Give robots a little more "personality" so that all robots have pros / cons which means that it means a little more which one you choose in the game
- All robots can have an option card by default. Or a tradeoff where you have to take something away for the options card to work? For example: Random movement cards cannot be distributed, such as turning right or moving 3? There are many opportunities to vary. If you choose this, it is important to refine the game mechanics such that it becomes fair regardless of which robot you choose. You can also choose to create your own stats (positive or negative) on the robots regardless of the option cards.


## Part 3: Code


- You need to document how the project builds, tests, and runs so it's easy to test the code. Under evaluation, the code will be user tested as well.
- The product should work on Linux (Ubuntu / Debian), Windows and OSX.
- Document **how** the tests should be run, as well.
- Code quality and test coverage will be emphasized. Note that the tests you write must be used in the product.

## Assessment criteria and weighting

- At this point in the project you are expected to work on the project even when there's no lectures.
- In this delivery there will be no presentation.
- If the team wants, all text can be delivered in English.
- Assessment criteria will be published before the deadline.
 
 
**Deadline for submission: April 1, 2019, at 4 pm**
 
 
To get the thesis approved, the group must:
 
 
- Write sensible Commit messages when changes are uploaded in the group's repo.
- Upload the requested results in repo.
- Upload the delivery to github in markdown format.
- Delivery should be uploaded in separate folder in the repo, called "Deliverables". Each oblig becomes
"ObligX.md" in that folder. Any slides can be uploaded to the same folder.
- Take a commit of the source code that is the delivery of each oblig. This makes it easier for those who are
considering doing so at the right time. **In addition to the code, you will also be evaluated on the
team work throughout the semester.**
 
 
To get the assignment approved, each team member must:
 
 
- Meet and participate actively in at least 75% of weekly meetings with their TA and oblig.2 presentation.
- Perform the assigned tasks during and between these weekly meetings, as agreed by the team.
- Report back to the team on progress and / or problems that arise, as agreed in the team.
 
 
**Weighting**: This delivery counts 12.5% of the final grade.
 
## Tips

Mockito can be a good mock framework in addition to JUnit. In general, it is also possible to write manual tests.
Remember that the tests you write will be used (as mentioned in part 3).
 

