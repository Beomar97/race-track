# 📋 Meeting Protocol

**Project/Meeting Name:** PSIT3-FS20-IT18ta_WIN-Team5 - Weekly Status Meeting

**Date of Meeting:** 24.04.2020, **Time:** 14:25 (GMT+1), **Location:** Remote

## 👨🏼‍💻 Attendees

✅<img src="img/teams_icon.png" alt="MS Teams Icon" height="20" /> Hans-Peter Hutter (Chair)

✅<img src="img/teams_icon.png" alt="MS Teams Icon" height="20" /> Marco Forster (Project Manager)

✅<img src="img/teams_icon.png" alt="MS Teams Icon" height="20" /> Dan Hochstrasser

✅<img src="img/teams_icon.png" alt="MS Teams Icon" height="20" /> Manuel Berweger

✅<img src="img/teams_icon.png" alt="MS Teams Icon" height="20" /> Marvin Tseng

## ✏️ Agenda

### Feedback Solution Architecture

#### Use Case Model

- Use Case Model Overview
  - Use Case Overview was quite clear
  - Storage is not an external actor but a part of the system (can be excluded). Only add it if it is really external for example with a cloud storage solution
- Use Case UC7 (Play one Game Session)
  - Fully-dressed, very good!
  - Good details
  - Always describe in an active form ("System places car" instead of "Player's car gets placed")
  - Not clear where the players start, how they are placed and who do the first move
    - First player entered is the first to start
    - Players are placed from top left to bottom right
  - When the first player who started finishes first, do the other players get to do their move? Else they had one move less than the first player
    - Is game just finished after every player's turn in the round where the first one finishes?
      - If yes, who wins the race when two people needed the same number of turns? Needed to calculate the velocity for each player who finishes the race?
  - System Sequence Diagram
    - GUI classes and components don't belong in the SSD -> Start wouldn't be with the GUI but with the system
    - handleCrash() from the Car would be a straight line and not a dashed one, as it is a method call
    - getValidPositions() from GameEngine? Doesnt seem to be the same in the source code... Needs to come from the Car!
    - Player and GUI are together -> Call back to the player instead of to the GUI
- Use Case UC13 (Load Track)
  - Don't put the solution into the use case (e.g. the main menu is already a solution)
  - The System uploads the image into the game and not the player
  - Contradiction: Player selects a valid image but the system verifies it?
  - Last two steps are not part of the use case anymore and can be deleted
  - Numbering of the alternative flows seem to be wrong
  - SSD is missing the logic for the verification of a valid track
- Use Case 14 (Save Game) & 15 (Load Game) might not be really use cases, as they don't have a lot of interaction
- But all in all very good

#### Additional Requirements

- Who wins the game after changes above needs to be documented then
- What do we mean with usable and accessible? Accessible to whom? Don't need to invent requirements.
- The ability to zoom into the track would be a cool addition
- Feedback should be <1s and not <5s
- 99.999% reliability doesn't make sense, change to something like 1 Break every 20 game sessions
- What about Touch capabilites?

#### Domain Model

- Track has *track limits* but is more a part of the image (color) than a Track -> maybe add an *Image* object into the domain model which has the whole *view* or *world* and not just the track
- Can put more information into the description like, Who starts the game? How are the cars placed? etc.
- All in all a good domain model

#### Software Architecture

- Event Handle and Logic should be separated -> don't put all in the controller
- UI controllers are part of the user interface (View) and the GameEngine should be part of the Model
- First layered diagram not so good, package diagram much better

#### Design Artifacts

- Design Class Diagram
  - Why so much compositions (diamonds)? Probably just normal references?
  - Cardinality is missing from the class diagram
  - Design class diagram should only show the logic/domain layer without the view (No UI controllers and UI classes)
- Communication Diagrams
  - Numbering in the communication diagrams seem not to be correct
    - Calls in the same method have same numbering like 1.1, 1.2 etc.
  - Only domain classes are important
  - Start is always a systemoperation from the user interface
- It is better to use SSD whenever possible

#### Implementation

- Document how the work is/was distributed (Git, Boards)

#### Glossary

- NPC is not used outside of the glossary and can be removed

