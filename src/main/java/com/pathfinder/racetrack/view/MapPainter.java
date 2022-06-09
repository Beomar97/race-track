package com.pathfinder.racetrack.view;

import com.pathfinder.racetrack.model.Car;
import com.pathfinder.racetrack.model.Coordinate;
import com.pathfinder.racetrack.model.Track;
import com.pathfinder.racetrack.model.exceptions.AllPossibleTurnsOutOfCanvasException;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * MapPainter updates/draws the Map View of the game
 */
public class MapPainter {
    private static final int CANVAS_WIDTH = 1126;
    private static final int CANVAS_HEIGHT = 826;
    private static final int BOX_SIZE = 25;
    private static final boolean SHARP = true;
    private static final double CAR_RADIUS = 12.5;
    private static final double SUGGESTION_BUTTON_RADIUS = 12.5;

    private MapPainter() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Get the number columns
     *
     * @return Number of grid columns
     */
    public static int getCountBoxesX() {
        return CANVAS_WIDTH / BOX_SIZE;
    }

    /**
     * Get the number of rows
     *
     * @return Number of grid rows
     */
    public static int getCountBoxesY() {
        return CANVAS_HEIGHT / BOX_SIZE;
    }

    /**
     * Get the radius of a button
     *
     * @return Radius of a button in pixel size
     */
    public static double getSuggestionButtonRadius() {
        return SUGGESTION_BUTTON_RADIUS;
    }

    /**
     * Draws a dot to the map canvas at the coordinate of the car
     *
     * @param drawOn Where to draw it on
     * @param car    the car to draw
     */
    public static void drawCar(Canvas drawOn, Car car) {
        GraphicsContext gc = drawOn.getGraphicsContext2D();
        Coordinate toDraw = calculatePointDrawing(car.getPosition());
        gc.setFill(car.getColor());
        gc.fillOval(toDraw.getX(), toDraw.getY(), CAR_RADIUS, CAR_RADIUS);
    }

    /**
     * Draws a dot to the map canvas at the coordinate of the car
     *
     * @param drawOn     Where to draw it on
     * @param coordinate What to draw as a dot
     * @param color      In which color
     */
    public static void drawCar(Canvas drawOn, Coordinate coordinate, Color color) {
        GraphicsContext gc = drawOn.getGraphicsContext2D();
        Coordinate toDraw = calculatePointDrawing(coordinate);
        gc.setFill(color);
        gc.fillOval(toDraw.getX(), toDraw.getY(), CAR_RADIUS, CAR_RADIUS);
    }

    /**
     * Draws the possible moves for the player of a given car
     *
     * @param drawOn Where to draw it on
     * @param car    The possible moves of this car will be painted
     * @return A list of the suggestion pixels where the user can click
     */
    public static List<int[]> drawSuggestion(Canvas drawOn, Car car) throws AllPossibleTurnsOutOfCanvasException {
        GraphicsContext gc = drawOn.getGraphicsContext2D();
        List<Coordinate> suggestions = car.getValidMoves();
        ArrayList<int[]> suggestionPixels = new ArrayList<>();
        int invalidPoints = 0;

        for (Coordinate point : suggestions) {
            Color newColor = car.getColor().brighter().brighter().desaturate().desaturate();
            gc.setFill(newColor);
            Coordinate toDraw = calculatePointDrawing(point);
            if (!onCanvas(toDraw)) {
                invalidPoints++;
            }
            suggestionPixels.add(new int[]{toDraw.getX(), toDraw.getY()});
            gc.fillOval(toDraw.getX(), toDraw.getY(), CAR_RADIUS, CAR_RADIUS);
        }
        // If all possible turns are outside of the playing field, the player gets removed from the game
        if (invalidPoints == 9) {
            throw new AllPossibleTurnsOutOfCanvasException("A Car left the canvas");
        }
        return suggestionPixels;
    }

    /**
     * Check if a given point is on the canvas
     *
     * @return  true if the point is on the canvas, else false
     */
    private static boolean onCanvas(Coordinate point) {
        return (point.getX() >= 0 && point.getX() <= CANVAS_WIDTH && point.getY() >= 0 && point.getY() <= CANVAS_HEIGHT);
    }


    /**
     * Draws the trace from the last position to the current one of a car.
     *
     * @param drawOn Where to draw it on
     * @param from   Starting point of the trace
     * @param to     Ending point of the trace
     * @param lineColor the color of the line
     */
    public static void drawMove(Canvas drawOn, Color lineColor, Coordinate from, Coordinate to) {
        GraphicsContext gc = drawOn.getGraphicsContext2D();
        Coordinate start = calculatePointDrawing(from);
        double xStart = start.getX() + (CAR_RADIUS / 2);
        double yStart = start.getY() + (CAR_RADIUS / 2);

        Coordinate end = calculatePointDrawing(to);
        double xEnd = end.getX() + (CAR_RADIUS / 2);
        double yEnd = end.getY() + (CAR_RADIUS / 2);

        gc.setLineWidth(2);
        gc.setStroke(lineColor);
        gc.strokeLine(xStart, yStart, xEnd, yEnd);
    }

    /**
     * Converts a coordinate to a printable coordinate
     *
     * @param point what should be painted
     * @return Converted converted coordinate for the canvas to be painted
     */
    private static Coordinate calculatePointDrawing(Coordinate point) {
        double x = point.getX();
        double y = point.getY();
        double radius = CAR_RADIUS / 2;
        int xPlacement = (int) Math.round(((x * BOX_SIZE) - radius));
        int yPlacement = (int) Math.round(((y * BOX_SIZE) - radius));

        return new Coordinate(xPlacement, yPlacement);
    }

    /**
     * Clears a canvas to blank
     *
     * @param toClear Which canvas to clear
     */
    public static void clearCanvas(Canvas toClear) {
        double fromX = 0;
        double fromY = 0;
        double toX = toClear.getWidth();
        double toY = toClear.getHeight();
        toClear.getGraphicsContext2D().clearRect(fromX, fromY, toX, toY);
    }

    /**
     * Draws a grid to the canvas
     *
     * @param drawOn Where to draw it on
     */
    public static void drawGrid(Canvas drawOn) {
        GraphicsContext gc = drawOn.getGraphicsContext2D();

        gc.setLineWidth(1.0);
        gc.setStroke(Color.gray(0.5));

        for (int x = 0; x < CANVAS_WIDTH; x += BOX_SIZE) {
            double x1;
            if (SHARP) {
                x1 = x + 0.5;
            } else {
                x1 = x;
            }
            gc.moveTo(x1, 0);
            gc.lineTo(x1, CANVAS_HEIGHT);
            gc.stroke();
        }

        for (int y = 0; y < CANVAS_HEIGHT; y += BOX_SIZE) {
            double y1;
            if (SHARP) {
                y1 = y + 0.5;
            } else {
                y1 = y;
            }
            gc.moveTo(0, y1);
            gc.lineTo(CANVAS_WIDTH, y1);
            gc.stroke();
        }
    }

    /**
     * Sets the images as a track background
     *
     * @param track    What to set as track
     * @param mapTrack Where to set it on
     */
    public static void drawTrack(Track track, ImageView mapTrack) {
        mapTrack.setImage(track.getBitmap());
        mapTrack.setX(25);
        mapTrack.setY(25);
    }
}
