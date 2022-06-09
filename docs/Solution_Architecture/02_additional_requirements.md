# Additional Requirements

## Functionality

### General

- The game will be played on the same device and GUI.
- The game will be implemented to allow distributed sessions in the future.

### Game Rules

#### Goal

The objective of the game is to be the first one to reach the finish line.

#### Environment

The game board is a checkered piece of paper with a track, starting line and finish line. All the grid points on the track are accessible by the players with their respective moves.

#### The Course of the game

All players start from the starting line. Player 1, who is registered first in RaceTrack, starts with the first move. After each move of a participant, the next competitor takes his turn until the first player reaches the finish line. After that, the players can choose to end the running game session at that moment or to continue until every other player also reaches the finish line. A finished player will not be able to move again.

##### Moving around

Each player's first move must lead to one of the five neighbors of the starting point moving forward. On the first move, it is not possible to go backwards. In each following move, the so-called base for this move is determined. The base is dependent on repeating the previous move, both horizontally and vertically. This calculation in RaceTrack is also known as velocity in the game.

**Example**

> If the player last moved two boxes to the right and four boxes up, the main point is now two boxes to the right and four above the current starting point. The player now can move directly to the main point or one of his eight neighbors.

##### Conditions

The cars must stay within the track boundaries. This applies to every move. Leaving the road will lead to a crash. Depending on the selected game mode, the game will react differently to a crash. Points that are occupied by another car cannot be approached.

##### Game Modes and Crash Handling

There are currently **three** unique game modes available:

**Game mode 1:** When the game mode *Continue with minimum velocity and no cceleration until the track is reached again* is selected during game creation:

1. The player's velocity resets.
2. The player does not accelerate until the track is reached again.

**Game mode 2:** When the game mode *Retire from the race* is selected during game creation:

1. The player *crashes* and receives a corresponding notification about it.
2. The player retires from the race and will not be able to do any more moves during this game session.

**Game mode 3:** When the game mode *Reset position to last valid point and reset velocity* is selected during game creation:

1. The player *crashes* and receives a corresponding notification about it.
2. The player's velocity resets.
3. The player's car resets to his last valid position on the track.

### Special Requirements

Custom tracks to be loaded into the game (UC13) need to comply with following requirements:

- The image must only consist of following colors *(can still be changed during development)*:
  - <span style="color: #C5C5C5">**#C5C5C5**</span> - represents the drivable track.
  - <span style="color: #000000">**#000000**</span>/<span style="color: #FFFFFF; background-color: #000000">**#FFFFFF**</span> - represents the start/finish line. <img src="img/Use-Case-Model_UC13_Track-Requirements_Start-Finish-Line.png" height=20>

  - Every other color will be interpreted as not to be part of the drivable track (out of track boundaries).

## Usability

RaceTrack's GUI must implement the fundamental concepts of usability to ensure a usable and accessible user experience. The following requirements are defined:

1. Minimalistic GUI
   1. 9 simple buttons to steer the car
   2. A map to show
      1. The grid paper
      2. The Track
      3. Finish/start line
2. Target interaction components should be large enough for users to both discern what it is and to accurately select them.
   1. minimum of 24 Pixels in width and height for buttons
3. Provide user feedback if computation actions take longer than five seconds.

## Reliability

The following reliability requirements are defined for RaceTrack.

- _Reliability_: 99.999% at 10'000 cycles
- _Failure definition_: A failed cycle is a session of RaceTrack which was interrupted by a crash of the application.

## Performance

Due to the simple architecture of the application, no special performance requirements are defined. Notice the usability requirement number four, which requires the response time for user feedback to be in *normal* levels (< 1s).

## Supportability

Serviceability requirements:

1. The Java code must be documented.
2. Clean code concepts will be integrated when developing RaceTrack (See PDF *PROG1 Clean-Code-Regeln*)
3. RaceTrack is developed using the [Semantic Versioning specification](https://semver.org/).

## Scalability

There are no special requirements about scalability.

