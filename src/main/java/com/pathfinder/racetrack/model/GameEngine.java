package com.pathfinder.racetrack.model;

import com.pathfinder.racetrack.RaceTrack;
import com.pathfinder.racetrack.model.exceptions.NotAValidTrackException;
import com.pathfinder.racetrack.view.ExceptionDialog;
import com.pathfinder.racetrack.view.MapPainter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

/**
 * Contains the logic and rules of the game
 */
public abstract class GameEngine {
    private static final int MIN_MOVES_TO_FINISH = 10;
    private List<Player> players;
    private Player currentPlayer;
    private Track track;
    private boolean gameEnded;

    /**
     * Constructor for initialising a new game
     *
     * @param players List of players
     * @param track   Where the game is carried
     */
    public GameEngine(List<Player> players, Track track) {
        this.players = players;
        currentPlayer = getRandomPlayer();
        this.track = track;
        gameEnded = false;
        placePlayersOnStart();
    }

    /**
     * Initialise the game from a rtSave file
     *
     * @param rtSaveFile Location of the file
     * @throws FileNotFoundException   When the rtSave file was not found
     * @throws NotAValidTrackException When the track was not valid
     */
    public GameEngine(RTSaveFile rtSaveFile) throws FileNotFoundException, NotAValidTrackException {
        players = rtSaveFile.getPlayers();
        currentPlayer = getRandomPlayer();
        File trackFile = new File(RaceTrack.getConfigFolder() + "/tracks/" + rtSaveFile.getTrackName() + ".png");
        track = new Track(rtSaveFile.getTrackName(), trackFile, MapPainter.getCountBoxesX(), MapPainter.getCountBoxesY());
        gameEnded = false;

    }

    /**
     * Returns one randomly selected player.
     * @return  one random player
     */
    private Player getRandomPlayer() {
        int countPlayers = players.size();
        int rnd = (countPlayers > 1) ? new Random().nextInt(players.size() - 1) : 0;
        return players.get(rnd);
    }

    /**
     * Places all players on to the starting line
     */
    public void placePlayersOnStart() {
        ListIterator<Player> iter = players.listIterator();
        int i = 0;
        while (iter.hasNext()) {
            Car car = iter.next().getCar();
            car.resetRoute();
            try {
                Coordinate startPosition = track.getStartPoints().get(i);
                car.initPosition(startPosition);
            } catch (IndexOutOfBoundsException e) {
                ExceptionDialog.showDialog("The finish line does not have enough space for all players", e);
                iter.remove();
            }
            i++;
        }
    }

    /**
     * Returns the track of the running game
     *
     * @return The active track
     */
    public Track getTrack() {
        return track;
    }

    /**
     * Sets the track for the up coming game
     *
     * @param track The track for the up coming game
     */
    public void setTrack(Track track) {
        this.track = track;
    }

    /**
     * Checks if a given point is still on the track
     *
     * @param toCheck Coordinate to check
     * @return True if it's on the track, false otherwise
     */
    public boolean pointIsOnTrack(Coordinate toCheck) {
        return track.onTrack(toCheck);
    }

    /**
     * This method executes the move of a player and asserts that the rules of the game
     * hasn't been broken
     *
     * @param buttonNumber The button number pressed by the player
     */
    public void doGameTurn(int buttonNumber) {
        Car car = getCurrentCar();
        Coordinate carMovesTo = car.getValidMoves().get(buttonNumber);
        applyGameRules(carMovesTo);

        if (gameEnded()) {
            gameEnded = true;
        } else {
            setNextPlayer();
        }
    }

    /**
     * Calculates and return the number of players that are not (yet) finished or retired
     *
     * @return  Number of active players
     */
    public int getNumberOfActivePlayers() {
        int activePlayers = 0;
        for (Player player : getPlayers()) {
            if (!player.getFinished() && !player.getRetired()) {
                activePlayers++;
            }
        }
        return activePlayers;
    }

    /**
     * Checks if all player have completed the track
     *
     * @return True if the game has ended
     */
    public boolean gameEnded() {
        for (Player player : getPlayers()) {
            if (!player.getFinished() && !player.getRetired()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Applies the game rules to the game and takes the necessary actions
     *
     * @param playerMove Where the car wants to move
     */
    public void applyGameRules(Coordinate playerMove) {
        Car car = getCurrentCar();

        if (checkPointIsOccupied(playerMove)) {
            carCrashes();
        } else {
            car.setCrashed(false);
            car.moveTo(playerMove);
        }

        if (!pointIsOnTrack(playerMove)) {
            carLeavesTrack(playerMove);
        } else {
            car.setOffTrack(false);
        }

        if (crossedFinishLine(car)) {
            carFinished();
        }
    }

    /**
     * Checks if a given car has crossed the finish line with a minimum number of moves
     *
     * @param car Car to check
     * @return True if the car crossed the finish line
     */
    boolean crossedFinishLine(Car car) {
        boolean crossedFinishLine = track.hasCrossedFinishLine(car.getPosition(), car.getLastPosition());
        boolean hasMadeFewMoves = (car.getNumberOfMoves() >= MIN_MOVES_TO_FINISH);

        return crossedFinishLine && hasMadeFewMoves;
    }

    /**
     * Describes what happens when the car crashes
     */
    abstract void carCrashes();

    /**
     * Describes what happens when the car leaves the track
     *
     * @param offTrackPoint Coordinate where the car leaves the track
     */
    abstract void carLeavesTrack(Coordinate offTrackPoint);

    /**
     * Triggered when the player is done
     */
    void carFinished() {
        currentPlayer.setFinished(true);
    }

    /**
     * Triggered when the player is retired from the game
     */
    public void retireCurrentPlayer() {
        currentPlayer.setRetired(true);
    }

    /**
     * Gets the player who should make the move
     *
     * @return The player who should make the move
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Sets the next player as the one how should make a move now
     */
    public void setNextPlayer() {
        currentPlayer = getNextPlayer();
    }

    /**
     * Gets the player who plays next and is not retired ans has not finished yet
     *
     * @return The player who plays next
     */
    public Player getNextPlayer() {
        int countPlayers = players.size();
        int i = (players.indexOf(currentPlayer) + 1) % countPlayers;

        Player nextPlayer = players.get(i);

        while (nextPlayer.getRetired()) {
            i++;
            nextPlayer = players.get(i % countPlayers);
        }

        while (nextPlayer.getFinished()) {
            i++;
            nextPlayer = players.get(i % countPlayers);
        }

        return nextPlayer;
    }

    /**
     * Get all players who play the game
     *
     * @return All players playing the game
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Checks if a given coordinate is already occupied
     *
     * @param carMovesTo The position the
     * @return True if it has crashed, false otherwise
     */
    public boolean checkPointIsOccupied(Coordinate carMovesTo) {
        for (Player player : players) {
            Car car = player.getCar();
            Coordinate coordinate = car.getPosition();
            double x = coordinate.getX();
            double y = coordinate.getY();
            if (x == carMovesTo.getX() && y == carMovesTo.getY() && car != getCurrentCar()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the car of the current player
     *
     * @return the car of the current player
     */
    public Car getCurrentCar() {
        return getCurrentPlayer().getCar();
    }

    /**
     * Get the car of the next player
     *
     * @return the car of the next player
     */
    public Car getNextCar() {
        return getNextPlayer().getCar();
    }

    /**
     * Get all cars of all players
     *
     * @return All cars of all players
     */
    public List<Car> getAllCars() {
        ArrayList<Car> carList = new ArrayList<>();
        for (Player player : players) {
            carList.add(player.getCar());
        }
        return carList;
    }

    /**
     * Get the game status
     *
     * @return Game status
     */
    public boolean getGameEnded() {
        return gameEnded;
    }

    /**
     * Set the status of the game
     *
     * @param gameEnded True if the game has ended, false otherwise
     */
    public void setGameEnded(boolean gameEnded) {
        this.gameEnded = gameEnded;

    }

    /**
     * Save the game as an rtSaveFile
     *
     * @param saveFile Where the game should be saved
     */
    public void saveGame(File saveFile) {
        RTSaveFile rtSaveFile = new RTSaveFile(getClass().getName(), getCurrentPlayer().getId(), getTrack().getName(), getPlayers());
        rtSaveFile.saveGame(saveFile);
    }

    /**
     * Resets all players to the initial state
     */
    public void resetGame() {
        ArrayList<Player> newPlayerList = new ArrayList<>();

        for (int i = 0; i < players.size(); i++) {
            Player oldPlayer = players.get(i);
            Car oldCar = oldPlayer.getCar();
            newPlayerList.add(new Player(i, oldPlayer.getName(), oldCar.getColor()));
        }
        players = newPlayerList;
        currentPlayer = players.get(0);
        gameEnded = false;
        placePlayersOnStart();
    }

    /**
     * Save the state of the game in high score format
     */
    public void saveHighScore() {
        List<Player> finishedPlayers = new ArrayList<>();
        for(Player player : players){
            if(player.getFinished()){
                finishedPlayers.add(player);
            }
        }
        RTSaveFile rtSaveFile = new RTSaveFile(getClass().getSimpleName(), currentPlayer.getId(), track.getName(), finishedPlayers);
        rtSaveFile.saveHighScore();
    }
}
