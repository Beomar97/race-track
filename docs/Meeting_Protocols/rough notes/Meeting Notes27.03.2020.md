# Meeting Notes

## Use cases

* UC7 -> Extend so one entire round is described
* Add parameter to handlePlayerAction -> probably rename funtion to moveCar
* Specfiy which car to move in Diagram
* Methods need to start with active verb
* car should keep array of its previous positions
* update(car) -> repaint or updateView (arrow back to user)
* remove handlePlayerACTION on the buttom of ssd

## FURPS

* Game Rules to separate Paper
* Requirements are about what the system need to do
* All Players play on the same UI/Screen -> Requirement
* Load track as image -> specify conditions of image (color mapping etc.)
* Short is fine :)
* Specify that there are no requirements in terms of scalability
* Atractiveness: Responsiveness -> Normal reaction time is suficent
* write down if there is not a certain requirement

## Acitecture

* Layered architecture -> View, Loginc, Persitance
* should only call from top to down- View is NOT updated by a call from the logic
* probably function to draw a track
