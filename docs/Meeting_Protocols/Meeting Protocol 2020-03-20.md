# 📋 Meeting Protocol

**Project/Meeting Name:** PSIT3-FS20-IT18ta_WIN-Team5 - Weekly Status Meeting

**Date of Meeting:** 20.03.2020, **Time:** 17:05 (GMT+1), **Location:** Remote

## 👨🏼‍💻 Attendees

✅<img src="img/teams_icon.png" alt="MS Teams Icon" height="20" /> Hans-Peter Hutter (Chair)

✅<img src="img/teams_icon.png" alt="MS Teams Icon" height="20" /> Marco Forster (Project Manager)

✅<img src="img/teams_icon.png" alt="MS Teams Icon" height="20" /> Dan Hochstrasser

✅<img src="img/teams_icon.png" alt="MS Teams Icon" height="20" /> Manuel Berweger

✅<img src="img/teams_icon.png" alt="MS Teams Icon" height="20" /> Marvin Tseng

## ✏️ Agenda

### Domain Model

- The domain model should show the problem and not solution.
- *Game Master* is more part of the solution and shouldn't be in the domain model (can be deleted).
- The track, street or course should be a part of the "problem", which is drawn on the *GridPaper*.
- Need to pay attention to the naming of the associations -> Why does a player *has* a car? Why is it there? Better would be a player *drives, controls or steers* a car.

- A *Car* has a *Position or Coordinate* which should reference back to the *GridPaper*.
- Why does a *Car* has a *Route*? Needs to be better explained. Maybe not necessary.
- Don't use arrows for the associations, using some special symbols for *is a* or *has a* association would be acceptable.

- Associations should readable in the *normal* reading order (from top to bottom and left to right). Else  use arrows to classify the reading order.

- Number / count also very important for associations -> Player controls how many cars? etc.

- Should *PossibleTurn* be part of the "problem"? Is ok if we already want to use it.
  - If yes, *PossibleTurn* should have an association back to the *GridPaper*
- Domain model is primarily here to help us to describe the problem. With a good model it should be easy to explain the product with only information in the domain model and it's visualization.
- Create a description a the bottom of the domain model (*we want to implement the paper game racetrack...*) and explain mentioned nouns in the model.

- The solution structure should have a familiarity with the problem (domain model) later on.

- Not all concepts of the domain model will result to classes during implementation, but the core of classes should already be in the model.

### Use Cases & Use Case Model

- UC7 (Play round of game) will be the biggest and most important use case to implement.
- *Save a Game* may not be an use case, for *Set Game Options* the same.
- Implementation of *Save a Game* using *ASCII art*-like files may be too complex. Should be easy to import it later in the game.

- Is it possible to draw your own tracks? Maybe with existing drawing apps? -> Easy to create a track from it, as in Java every pixel has a color which you can get -> E.g. black route, white border etc.
  - This implementation would be much more scalable than converting characters.
  - Would also add the easy ability to draw own tracks 😯
  - Shouldn't be too hard to implement (he thinks).

- The load of a track could than be an use case
  - Draw the grid paper later on the track, not already in the "drawing".

- UC7 can be explained a little more specific. You can accelerate, hold your velocity, break etc.
  - What happen when crashing is an extension.
  - Adjust steps. May add alternative forms.
  - Which possible moves will be shown? Also *wrong* moves?
  - An alternative path when you get outside of the track? Make a reference to alternative flows.
  - Only crash when moving (into a car or border).

- Use cases should be explainable with only terms in the domain model.
- Minimal use cases to implement for this phase should be to *Load a Track* and *Move a Car (with Crash)*.

### System Sequence Diagram

- No need to show multiple players.

- Player chooses move on the GUI, which will trigger a method *move()* in the system etc.
- System will change the GUI which gives feedback to the player.
- Define most important system calls like *move()*.
- *move car* should be a dotted line.

- Define what methods or information is needed from the GUI to the system.

### UI Sketch

- Should also show a grid, like in the actual game.

- Looks pretty generic at the moment 😕.

- Buttons are not really important.
- Important is what is seen on the track (the race itself). Which car's turn is it now? What are his moves?

### Additional Rquirements

- Should be as specific as possible. Don't use sentences like *It should load as fast as possible*, more like *It should load in under 2s*.

### Design Artifacts

- It is more important to think about the architecture.
- Implementation in a basic form.

