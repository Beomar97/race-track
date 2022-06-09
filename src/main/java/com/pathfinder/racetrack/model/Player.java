package com.pathfinder.racetrack.model;

import javafx.scene.paint.Color;

/**
 * A class which represents a player of the game RaceTrack
 */
public class Player {
    private final int id;
    private final Car car;
    private String name;
    private boolean finished;
    private boolean retired;

    private int rank;

    private int nbrOfMoves;

    /**
     * Constructor of a player
     *
     * @param id    player's id as int
     * @param name  player's name as string
     * @param color player's color which will be set to his car
     */
    public Player(int id, String name, Color color) {
        this.id = id;
        this.name = name;
        finished = false;
        car = new Car(color);
        this.rank = 0;
        this.nbrOfMoves = 0;
    }

    /**
     * Get the car of the player
     *
     * @return car of the player
     */
    public Car getCar() {
        return car;
    }

    /**
     * Check if the player is out of the game
     *
     * @return Retired status of the player
     */
    public boolean getRetired() {
        return retired;
    }

    /**
     * Set the retired status of the player
     *
     * @param retired Retired status of the player
     */
    public void setRetired(boolean retired) {
        this.retired = retired;
    }

    /**
     * Get the id number of the player
     *
     * @return player id
     */
    public int getId() {
        return id;
    }

    /**
     * Get the name of the player
     *
     * @return the name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the player
     *
     * @param name name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the information, whenever the player has completed the game or not.
     *
     * @return Finished status of the player
     */
    public boolean getFinished() {
        return finished;
    }

    /**
     * Set the status if the player has completed the game session
     *
     * @param hasFinished True if the player has completed the game, otherwise set false
     */
    public void setFinished(boolean hasFinished) {
        this.finished = hasFinished;
    }

    /**
     * Get the rank of the player
     *
     * @return rank of the player
     */
    public int getRank() {
        return rank;
    }

    /**
     *  Set the rank of the player
     * @param rank rank to be set
     */
    public void setRank(int rank) {
        this.rank = rank;
    }

    /**
     * Get the number of moves the player already has made
     *
     * @return number of moves of the player
     */
    public int getNbrOfMoves() {
        return nbrOfMoves;
    }

    /**
     * Set the number of moves the player already has made
     *
     * @param nbrOfMoves number of moves to be set
     */
    public void setNbrOfMoves(int nbrOfMoves) {
        this.nbrOfMoves = nbrOfMoves;
    }

    /**
     * Compare the number of moves aready made of two players
     *
     * @param o player to compare to
     * @return boolean value if the number of moves are the same or not
     */
    public int compareTo(Player o) {
        return this.getNbrOfMoves() - o.getNbrOfMoves();
    }

}
