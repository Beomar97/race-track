package com.pathfinder.racetrack.model;

import com.pathfinder.racetrack.model.exceptions.NotAValidTrackException;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * This class represent the track of the game where the cars can drive around
 */
public class Track {
    private static final Logger logger = LoggerFactory.getLogger(Track.class);
    private static final Color asphaltColor = Color.web("0xc5c5c5ff");
    private static final Color finishLineBlack = Color.BLACK;
    private static final Color finishLineWhite = Color.WHITE;
    private static final String INVALIDTRACKEXCEPTION = "Invalid Track Bitmap";
    private final double xScale;
    private final double yScale;
    private final String name;
    private final File file;
    private final Image trackBitMap;
    private final PixelReader pixelReader;
    private final int xGrids;
    private final int yGrids;
    private Line finishLine;

    /**
     * Create an instance of a track and analyse the bitmap to find the finish line
     * @param name      Human readable identifier of a track
     * @param imageFile File of the bitmap image that contains the track
     * @param xGrids    Number of grids on the x-axis. Used to map coordinates recived from the engine to the bitmap
     * @param yGrids    Number of grids on the y-axis. Used to map coordinates recived from the engine to the bitmap
     * @throws FileNotFoundException When the track was not found
     * @throws NotAValidTrackException When the track is not valid
     */
    public Track(String name, File imageFile, int xGrids, int yGrids) throws FileNotFoundException, NotAValidTrackException {
        this.name = name;
        this.file = imageFile;
        this.trackBitMap = new Image(new FileInputStream(imageFile.getPath()));
        this.pixelReader = trackBitMap.getPixelReader();
        this.xGrids = xGrids;
        this.yGrids = yGrids;
        // Image.getWidth and Image.getHeight return the number od pixels in a row or column.
        // As the index of the pixels starts at 0, 1 pixel needs to be subtracted here to prevent exceptions
        this.xScale = (this.trackBitMap.getWidth() - 1) / xGrids;
        this.yScale = (this.trackBitMap.getHeight()- 1) / yGrids;
        findFinishLine();
    }

    /**
     * Gets the name of the track
     *
     * @return name of the track
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the Image object of the track
     *
     * @return Image object of the track
     */
    public Image getBitmap() {
        return trackBitMap;
    }

    /**
     * Gets the File object of the track
     *
     * @return File object of the track
     */
    public File getFile() {
        return file;
    }

    /**
     * Gets the finish line of the track
     *
     * @return the finish line of the track
     */
    public Line getFinishLine() {
        return finishLine;
    }

    /**
     * Check if the coordinate c is on the track
     *
     * @param c Coordinate that needs to be checked. It will be converted to the bitmaps coordinates before it is checked
     * @return true if the coordinate c is on the track, else it is false
     */
    public boolean onTrack(Coordinate c) {
        c = convertCoordinateFormGridToBitmap(c);
        return onTrack(c.getX(), c.getY());
    }

    /**
     * Check if the pixel at the position x/y is on the track
     *
     * @param x Pixel index in x direction
     * @param y Pixel index in y direction
     * @return true if the the pixel is on the track, else it is false
     */
    private boolean onTrack(int x, int y) {
        if (x > trackBitMap.getWidth() || x < 0) {
            return false;
        }
        if (y > trackBitMap.getHeight() || y < 0) {
            return false;
        }
        Color color = pixelReader.getColor(x, y);
        return asphaltColor.equals(color) || onFinishLine(x, y);
    }

    /**
     * Maps a coordinate from the game engine to a coordinate of the bitmap
     *
     * @param c Coordinate that needs to be converted
     * @return new Coordinate that is applicable directly on the bitmap
     */
    private Coordinate convertCoordinateFormGridToBitmap(Coordinate c) {
        return new Coordinate((int) Math.round(c.getX() * xScale), (int) Math.round(c.getY() * yScale));
    }

    /**
     * Maps a coordinate from the bitmap to a coordinate of the game engine
     *
     * @param c Coordinate that needs to be converted
     * @return new Coordinate that is applicable to the game engine
     */
    private Coordinate convertCoordinateFormBitmapToGrid(Coordinate c) {
        return new Coordinate((int) Math.round(c.getX() / xScale), (int) Math.round(c.getY() / yScale));
    }

    /**
     * Check if the line from one coordinate to the other crosses the finish line
     *
     * @param endpoint   Endpoint of the line that will be checked
     * @param startPoint Startpoint of the line that will be checked
     * @return true if the line between the points crosses the finish line
     */
    public boolean hasCrossedFinishLine(Coordinate endpoint, Coordinate startPoint) {
        return finishLine.crosses(new Line(endpoint, startPoint));
    }

    /**
     * Check if the pixel at x/y is on the finish line
     *
     * @param x x coordinate of the pixel
     * @param y y coordinate of the pixel
     * @return true if x/y is on the finish line, else false
     */
    private boolean onFinishLine(int x, int y) {
        return pixelReader.getColor(x, y).equals(Color.WHITE) || pixelReader.getColor(x, y).equals(Color.BLACK);
    }

    /**
     * Check if the game engine's coordinate c is on the finish line
     *
     * @param c The coordinate that needs to be checked
     * @return true if the coordinate is on the finish line, else false
     */
    private boolean onFinishLine(Coordinate c) {
        c = convertCoordinateFormGridToBitmap(c);
        boolean matchesWhite = pixelReader.getColor(c.getX(), c.getY()).equals(finishLineWhite);
        boolean matchesBlack = pixelReader.getColor(c.getX(), c.getY()).equals(finishLineBlack);
        return matchesWhite || matchesBlack;
    }


    /**
     * find the finish line in the bitmap. This method should only be called once per track. It sets the datafield
     * finishLineFunction to the function that is parallel to the longer edge of the finish line and cuts the shorter
     * edge in half. This function is also aligned on the game engines grid.
     *
     * @throws IllegalArgumentException is thrown, when there is no finish line in the bitmap or if it is so small,
     *                                  that it is not larger than the smallest area four different coordinates can limit.
     */
    private void findFinishLine() throws NotAValidTrackException {
        Coordinate gridSearch;
        Coordinate hit = null;
        Coordinate topLeftFinishLineCoordinate;
        Coordinate bottomLeftFinishLineCoordinate;
        Coordinate topRightFinishLineCoordinate;
        Coordinate bottomRightFinishLineCoordinate;
        Coordinate finishLinePointA;
        Coordinate finishLinePointB;
        boolean firstHit = false;

        // search for first grid coordinate that is on the finish line
        for (int i = 0; i < xGrids; i++) {
            for (int j = 0; j < yGrids; j++) {
                gridSearch = new Coordinate(i, j);
                if (onFinishLine(gridSearch)) {
                    hit = gridSearch;
                    firstHit = true;
                    break;
                }
            }
            if (firstHit) {
                break;
            }
        }

        //if there is no point on the grid with a finish line pixel beneath it, throw an exception
        if (hit == null) {
            throw new NotAValidTrackException(INVALIDTRACKEXCEPTION);
        }

        // get the borders of the finish line
        hit = convertCoordinateFormGridToBitmap(hit);
        int rightX = searchEndOfFinishLineX(hit, true);
        int leftX = searchEndOfFinishLineX(hit, false);
        int bottomY = searchEndOfFinishLineY(hit, true);
        int topY = searchEndOfFinishLineY(hit, false);
        topLeftFinishLineCoordinate = new Coordinate(leftX, topY);
        bottomLeftFinishLineCoordinate = new Coordinate(leftX, bottomY);
        topRightFinishLineCoordinate = new Coordinate(rightX, topY);
        bottomRightFinishLineCoordinate = new Coordinate(rightX, bottomY);

        // Check orientation of finish line
        Line a = new Line(topLeftFinishLineCoordinate, topRightFinishLineCoordinate);
        Line b = new Line(topRightFinishLineCoordinate, bottomRightFinishLineCoordinate);
        Line c = new Line(bottomLeftFinishLineCoordinate, bottomRightFinishLineCoordinate);
        Line d = new Line(topLeftFinishLineCoordinate, bottomLeftFinishLineCoordinate);

        if (a.getLength() < b.getLength()) {
            finishLinePointA = convertCoordinateFormBitmapToGrid(a.getMiddle());
            finishLinePointB = convertCoordinateFormBitmapToGrid(c.getMiddle());
        } else {
            finishLinePointA = convertCoordinateFormBitmapToGrid(b.getMiddle());
            finishLinePointB = convertCoordinateFormBitmapToGrid(d.getMiddle());
        }

        finishLine = new Line(finishLinePointA, finishLinePointB);

        logger.info("Track {}", name);
        logger.info("  finish line starts at {}/{}", finishLinePointA.getX(), finishLinePointA.getY());
        logger.info("  and ends at {}/{}", finishLinePointB.getX(), finishLinePointB.getY());
    }

    /**
     * Based on the finish line this method returns all possible and mostly fair placed start coordinates for the players.
     *
     * @return An arrayList of game engine coordinates is returned where the cars should start
     */
    public List<Coordinate> getStartPoints() {
        List<Coordinate> list = finishLine.getAllIntCoordinates();
        list.removeIf(c -> !onFinishLine(c));
        return list;
    }

    /**
     * Helper funciton to search for the end of the finish line in x direction
     *
     * @param start     where to start searching in the bitmap. This point has to be already on the finish line
     * @param increment whether to search to the right or to the left
     * @return          the x coordinate where the finish line ends
     */
    private int searchEndOfFinishLineX(Coordinate start, boolean increment) throws NotAValidTrackException {
        int step;
        int end;
        int res = 0;
        int loopStart = start.getX();
        if (increment) {
            step = 1;
            end = (int) trackBitMap.getWidth();
        } else {
            step = -1;
            end = 0;
        }
        for (int i = loopStart; i != end; i += step) {
            if (!onFinishLine(i, start.getY())) {
                if (i < trackBitMap.getWidth() / xGrids) {
                    throw new NotAValidTrackException("Finish line is too small");
                }
                res = i;
                break;
            }
        }
        return res;
    }

    /**
     * Helper funciton to search for the end of the finish line in y direction
     *
     * @param start     where to start searching in the bitmap. This point has to be already on the finish line
     * @param increment whether to search to the bottom or to the top
     * @return          the y coordinate where the finish line ends
     */
    private int searchEndOfFinishLineY(Coordinate start, boolean increment) throws NotAValidTrackException {
        int step;
        int end;
        int res = 0;
        int loopStart = start.getY();
        if (increment) {
            step = 1;
            end = (int) trackBitMap.getHeight();
        } else {
            step = -1;
            end = 0;
        }
        for (int i = loopStart; i != end; i += step) {
            if (!onFinishLine(start.getX(), i)) {
                if (i < trackBitMap.getHeight() / yGrids) {
                    throw new NotAValidTrackException("Finish line is too small");
                }
                res = i;
                break;
            }
        }
        return res;
    }
}