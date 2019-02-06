# Mandatory 2 - LwjglApplicationConfiguration
##	Subtask 1
-	Group name: LwjglApplicationConfiguration
-	Team leader: Christian Hein
-	Customer contact: Morten Bergmann
##	Subtask 2
###	Overall goal
-	Our goal is to make a functional RoboRally game, where user interactions is possible.
###	System requirements (minimum viable product)
-	Game Board
  -	One robot per tile
  -	Walls, both between tiles and on the edges of the board
  -	Holes
  -	Lasers
  -	Conveyor belt
  -	Special fields
    -	Screwdriver
      -	Move backup
      -	Repair
    -	Hammer
    -	Options
-	Player
-	Game Piece (robot)
-	Program card
  -	Movements
    -	Rotate
      -	90° L # 18 Prio 80 – 420 (80, 100, 120, …)
      -	90° R # 18 Prio 70 – 410 (70, 90, 110, …)
      -	180° # 6 Prio 10 – 60 (10, 20, 30, …)
    -	Forward
      -	1 # 18 490 – 650 (490, 500, 510, ...)
      -	2 # 12 670 – 780 (670, 680, 690, ...)
      -	3 # 6 790 – 840 (790, 800, 810, …)
    -	Backwards
      -	1 # 6 430 – 480 (430, 440, 450, …)
  -	Priority
    -	Unique value
-	Flag
-	Movements tile
-	Special field (backup)
-	Laser
-	Walls
-	Holes
-	Round (= 5 phases)
  -	Before the round
    -	Deal cards
    -	Robots have/get placements
    -	Make program
    -	Confirm program
  -	During the round
    -	Program cards are locked
    -	Phase
  -	After the round
    -	Repair damage
    -	Powerdown
    -	Deal option cards
  -	Phase
    -	Turn program card
    -	Move robots by priority
    -	Move conveyor belt
    -	Move gears
    -	Activate lasers
    -	Tally damage
    -	Move backup
    -	Registers flags

-	Deal cards
-	Make a program (max 5 cards)
-	Move robots
-	Visit flags
-	Leave backup
-	Activate lasers
-	Take fire
-	Damage
-	Repair
-	Get totaled
-	Only one robot per tile
-	Highest priority moves first

-	Multiplayer
-	Game over
-	Difficulty
-	Default boards
###	First iteration
-	To have all infrastructure in place
-	To have a two dimensional grid where it’s possible to add a Game Piece on the Game Board.
##	Subtask 3
-	Project method chosen: XP
-	Communication outside meetings: Slack
-	Distribution of tasks: Trello
-	Work follow-up: Trello
-	Files and storage: GitHub
##	Subtask 4
-	*on our GitHub repo*
