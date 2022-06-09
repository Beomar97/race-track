package com.pathfinder.racetrack.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import com.pathfinder.racetrack.model.exceptions.NotAValidTrackException;
import com.pathfinder.racetrack.view.ExceptionDialog;
import javafx.scene.paint.Color;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * RTSave File to save the state of the game as a file
 */
public class RTSaveFile {
    private static final Logger logger = LoggerFactory.getLogger(RTSaveFile.class);
    private static final Path DEFAULT_HIGH_SCORE_DIRECTORY = Paths.get(System.getProperty("user.home"), "RaceTrack", "high_score");
    private static final String IMPORT_ERROR = "Error occurred during import of high score file of track '{}'!";
    private final int currentPlayerId;
    private final String gameMode;
    private final String trackName;
    private List<Player> players;

    /**
     * Instantiate a rtsave file with all information to recreate the game
     *
     * @param gameMode        Game mode played
     * @param currentPlayerId Player's turn
     * @param trackName       Name of the track
     * @param players         All participants
     */
    public RTSaveFile(String gameMode, int currentPlayerId, String trackName, List<Player> players) {
        this.gameMode = gameMode;
        this.currentPlayerId = currentPlayerId;
        this.trackName = trackName;
        this.players = players;
    }

    /**
     * Instanciate a rtsave file without players
     *
     * @param gameMode  Game mode played
     * @param trackName Track name
     */
    public RTSaveFile(String gameMode, String trackName) {
        this.gameMode = gameMode;
        this.currentPlayerId = 0;
        this.trackName = trackName;
    }

    /**
     * Saves the game with the corresponding objects to a json file in the RaceTrack Folder
     *
     * @param saveFile Location where to save as a file object
     */
    public void saveGame(File saveFile) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (FileWriter fileWriter = new FileWriter(saveFile)) {
            gson.toJson(this, fileWriter);
        } catch (IOException e) {
            logger.error("Could not write the JSON to the RaceTrack Folder", e);
        }
    }

    /**
     * Fixes the weird issue, where gson could not create a color object from json
     */
    public void fixGSONColorTypeLoss() {
        for (Player player : getPlayers()) {
            Color color = player.getCar().getColor();
            player.getCar().setColor(Color.web(color.toString()));
        }
    }


    /**
     * Generate a game engine from rtsave file
     *
     * @return Game engine
     * @throws FileNotFoundException   rtsave file not found
     * @throws NotAValidTrackException Track is not a vaild image
     */
    public GameEngine getGameEngine() throws FileNotFoundException, NotAValidTrackException {
        switch (getGameMode()) {
            case "com.pathfinder.racetrack.model.BobbyCarEngine":
                return new BobbyCarEngine(this);
            case "com.pathfinder.racetrack.model.FormulaOneEngine":
                return new FormulaOneEngine(this);
            case "com.pathfinder.racetrack.model.GoKartEngine":
                return new GoKartEngine(this);
            default:
                return null;
        }
    }

    /**
     * Save the high score as a file
     */
    public void saveHighScore() {
        Path trackHighScoreFilePath = DEFAULT_HIGH_SCORE_DIRECTORY.resolve(trackName).resolve(gameMode + ".csv");
        File trackHighScoreFile = trackHighScoreFilePath.toFile();
        List<String[]> stringArray = setGameInfoInCSV();
        File parentDir = trackHighScoreFile.getParentFile();
        boolean dirAlreadyExists = parentDir.mkdir();
        if (dirAlreadyExists) {
            logger.info("{} already existed while trying to create it", parentDir);
        }
        try {
            boolean fileAlreadyExisted = trackHighScoreFile.createNewFile();
            if (fileAlreadyExisted) {
                logger.info("{} already existed while trying to create it", trackHighScoreFile);
            }
        } catch (IOException e) {
            ExceptionDialog.showDialog("Couldn't complete action! Error occurred during high score file creation...", e);
            logger.error(IMPORT_ERROR, trackHighScoreFile.getName(), e);
        }
        try (CSVWriter writer = new CSVWriter(new FileWriter(trackHighScoreFile, true))) {
            for (String[] line : stringArray) {
                writer.writeNext(line);
            }
        } catch (IOException e) {
            ExceptionDialog.showDialog("Couldn't complete action! Error occurred during high score write...", e);
            logger.error(IMPORT_ERROR, trackHighScoreFile.getName(), e);
        }
    }

    /**
     * Read the high score from a file
     */
    public void readHighScore() {
        Path trackHighScoreFilePath = DEFAULT_HIGH_SCORE_DIRECTORY.resolve(trackName).resolve(gameMode + ".csv");
        File trackHighScoreFile = trackHighScoreFilePath.toFile();
        List<String[]> list = new ArrayList<>();
        try (
                FileReader highScoreFileReader = new FileReader(trackHighScoreFile);
                Reader reader = new BufferedReader(highScoreFileReader);
                CSVReader csvReader = new CSVReader(reader)
        ) {
            list = csvReader.readAll();
        } catch (IOException | CsvException e) {
            ExceptionDialog.showDialog("Couldn't complete action! Error occurred during high score import...", e);
            logger.error(IMPORT_ERROR, trackHighScoreFile.getName(), e);
        }
        getGameInfoFromCSV(list);
    }

    private List<String[]> setGameInfoInCSV() {
        List<String[]> gameInfoList = new ArrayList<>();
        for (Player player : this.players) {
            String nbrMoves = Integer.toString(player.getCar().getNumberOfMoves());
            String playerName = player.getName();
            String carColor = player.getCar().getColor().toString();
            String playerId = Integer.toString(player.getId());

            String[] line = {nbrMoves, playerName, carColor, playerId};
            gameInfoList.add(line);
        }
        return gameInfoList;
    }

    private void getGameInfoFromCSV(List<String[]> gameInfoList) {
        List<Player> newPlayerList = new ArrayList<>();
        for (String[] line : gameInfoList) {
            Color carColor = Color.RED;
            Player player = new Player(Integer.decode(line[3]), line[1], carColor);
            player.setNbrOfMoves(Integer.decode(line[0]));
            newPlayerList.add(player);
        }
        this.players = newPlayerList;
    }

    /**
     * Gets the game mode of the saved game
     *
     * @return the game mode of the saved game as a String
     */
    public String getGameMode() {
        return gameMode;
    }

    /**
     * Gets the id of the current player (next move)
     *
     * @return id of the current player
     */
    public int getCurrentPlayerId() {
        return currentPlayerId;
    }

    /**
     * Gets the name of the track of the saved game
     *
     * @return name of the track
     */
    public String getTrackName() {
        return trackName;
    }

    /**
     * Gets a list of all participating players of the saved game
     *
     * @return a list of players in the saved game
     */
    public List<Player> getPlayers() {
        return players;
    }
}
