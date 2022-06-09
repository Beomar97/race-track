package com.pathfinder.racetrack.util;

import com.pathfinder.racetrack.model.Player;

import java.util.Comparator;

/**
 * Comparer util class to compare a player with another based on a certain criteria
 */
public class AlphaComparer implements Comparator<Player> {
    @Override
    public int compare(Player o1, Player o2) {
        int comparer = o1.compareTo(o2);
        if (comparer > 0) {
            return 1;
        } else if (comparer < 0) {
            return -1;
        }
        return 0;
    }
}
