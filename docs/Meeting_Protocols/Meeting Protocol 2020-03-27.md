# 📋 Meeting Protocol

**Project/Meeting Name:** PSIT3-FS20-IT18ta_WIN-Team5 - Weekly Status Meeting

**Date of Meeting:** 26.03.2020, **Time:** 17:30 (GMT+1), **Location:** Remote

## 👨🏼‍💻 Attendees

✅<img src="img/teams_icon.png" alt="MS Teams Icon" height="20" /> Hans-Peter Hutter (Chair)

✅<img src="img/teams_icon.png" alt="MS Teams Icon" height="20" /> Marco Forster (Project Manager)

✅<img src="img/teams_icon.png" alt="MS Teams Icon" height="20" /> Dan Hochstrasser

✅<img src="img/teams_icon.png" alt="MS Teams Icon" height="20" /> Manuel Berweger

✅<img src="img/teams_icon.png" alt="MS Teams Icon" height="20" /> Marvin Tseng

## ✏️ Agenda

### Use Case Model

- Should remove the dotted line from UC14 to UC7 (old relict).

- Should extend UC7 (Move Car) to describe an entire game round.
  - Change *Move Car* to *Play a Round of Game*.
- *handlePlayerAction* seems a bit too generic, specify it more. How does the engine handle a player? Maybe add an **ID** as a parameter. Engine knows to handle which player thanks to the ID.
- Should be more specific with the name, rename the function to *moveCar*.
- How does the system know which car to move and where? Which parameters need to be added and how does the system get the necessary information?
- Don't use statements like *currentPlayerMoves*, but use actions like *moveCar*.
- *addPosition(Coordinate)* sounds weird to a car, not when you add a position to a track/route. Maybe add a new object/actor called track/route?
- Is the call to the *GridPaper* necessary?
- Who checks if a crash happens? GameEngine? Needs to be specified.
- Add precondition that the user is able to see the buttons for the moves.
- Player 2 can be removed from the System Sequence Diagram for the loop.

### Additional Requirements

- ~~Game Rules should be stated in a seperate section and not in the additional requirements.~~

- State that the game is played on the same GUI (screen) and not distributed.
- Track will be loaded from an image -> specify the conditions of said image.
- The usability and design needs to be attractive for the user.
- Don't need to *invent* requirements if there aren't any more. Just need to state it.
- No special requirements about scalability needed.
- Response time should be in the *normal* levels.

### Architecture

- A Design Pattern is smaller than the architecture.
- Client-Server-, Peer-to-Peer-Architecture?

- If there aren't any special architecture requirements needed, we should opt for a **layered architecture**.
- Divide into multiple layers -> View, Logic, Persistence, Communication etc.
- Seperate the UI from the logic.
- Should always call from **Top-to-Down**. View to Logic and not vice versa.
- If there is still time, adding the capability of drawing a track directly in the game would be a *killer feature*.

