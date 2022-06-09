# Main Use Case

//TODO add additional rules if any are missing or added

The main use case is the player, or a group of players playing a round of RaceTrack:

1. On his computer, the player starts the application, which he already downloaded and installed.

2. After the main menu has finished loading, the player starts a new game by selecting the corresponding entry in the menu.

3. The player configures his game session with his options, e.g. which track, his car, how many players etc.

5. After the game has started, the player is able to make his first turn.

6. The Player chooses his move for his current turn, based on the given possible moves the player can take. The possibilities are being shown on the track.
   - The player's possible moves are calculated at the beginning of his turn, based on his current velocity and last moves.

7. The player's car moves to the selected position on the track

*As a turn-based game, every other player needs to do his turn first, before a player gets the ability to do his next turn. Each player repeats steps 6-7 until i'ts the first player's turn again.*

9. The game continues, turn after turn, until the first player has reached the finishing line.

10. While playing the game, following rules are being enforced:

    - You can not drive backwards.

    - You can not drive to a position already occupied by another player.

    - You can not drive past the track limits, doing so will result in a crash in which the crashed player get's disqualified.

11. After the first player has reached the finish line, the game can be continued until every other player reaches the finish line or it can be finished at that moment.

12. After the session has finished, the player's results are displayed in comparison with the track's highscore.
13. The player can choose to return to the main menu, restart the session with the same settings or to end the game (application) completely.

