## Domain Model

- First Problem
- second is the track
- has is unspecific player drives car or controls the car
- position is refered to the grid (reference as normal lines without arrows maybe is a connection or as a connection)
- idea the association in reading order top down left right
- player stears a car
- Car: Postition association of gridpaper
- PossibleTurn: association of gridpaper straight line
- make discription at buttom: domain model schows the problem. we want to implement the game vector race. (mentioned nowns in description show up in domain model). good domain model explain domain model in words with the visualization easy to explain.
- second important thing. solution structure should look similar to problem. not all concept are classes in solution some added some removed
- route maybe not necessary. explain it.

## Use Cases

- uc7 is a large use case
- saving game is maybe not a usecase
- set game options maybe
- do you think you can choose or draw your own tracks
- map is maybe complicated. load somehow a picture with pixels, if you see a logiacal pixel image is there a line or not. the only thing that you need to know is the position between two lines. or take any drawing tool and is filed with a color, than you know each pixel is within the track. don't want to allow that you can move pixelised. read an image and see colors in java ask for what color the pixel has. Run the game on any image youve got.
- minimal use case for first prototype. check if crashed and play one round succesfully. a case of crash would be an extension
- ac7 ajust step 3 to 1 and 2. leave 5 out. may add alternative forms. question is which possible moves are shown (also wrong moves?). alternative path wenn you get outside of track. may make a refference to alternative flows, only wenn moving you make a crash. crash in a car or a border. think about domain model weather you can explain use case with domain model only. see if something is missing. explain use cases on the domain model for most important use cases.
- sequence diagram: show all system operation you need to show this use case. weather you need to show the different player. makes a move is the first line. first line be aware what you see in the diagram, here is the player together with the gui he presses a button to move here. ok the ui knows and tells it to the system operation. find name of method and information that is passed. after car is moved gives feedback to user with the user interface (interupted line, because its not a call just an information). only mention one player.

### UI Sketsch

see more details a grid and one or two moves of the car. it is quite generic. buttom line is not that important (buttons), important is the race itself. and know which car is active at the moment. buttons with non touch display. load if you intent to load the button.

UC10

- if your going with these images you have a use case of loading an image file, how to load the game and draw a grid. if the grid is in the image you have draw the grid. it would be a use case. for that you dont need a system sequence diagram only to push a button to load the game and file browser opens and select the image.
- in the end of the phase it is important that you can show the use case 7 and 10.

## System Sequence diagram

shows most important system calls etc.

## Design artifacts

- More important to think about the architecture
- implement in basic form...

