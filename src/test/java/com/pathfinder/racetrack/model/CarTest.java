package com.pathfinder.racetrack.model;

import javafx.scene.paint.Color;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CarTest {
    @Test
    public void carShouldInit() {
        Car car = new Car(0, 0, Color.RED);

        assertEquals(Color.RED, car.getColor());

        assertEquals(0, car.getPosition().getX());
        assertEquals(0, car.getPosition().getY());

        assertEquals(0, car.getCurrentVelocity().getX());
        assertEquals(0, car.getCurrentVelocity().getY());
    }

    @Test
    public void carShouldMove() {
        Color color = new Color(1, 1, 1, 0);
        Car car = new Car(0, 0, Color.RED);

        for (int i = 1; i < 100; i++) {
            car.moveTo(new Coordinate(i, i));
            assertEquals(i, car.getNumberOfMoves());
            assertEquals(i, car.getPosition().getX());
            assertEquals(i, car.getPosition().getY());
        }
    }

    @Test
    public void carShouldAccelerate() {
        Color color = new Color(1, 1, 1, 0);
        Car car = new Car(0, 0, color);

        car.moveTo(new Coordinate(1, 1));
        assertEquals(1, car.getCurrentVelocity().getX());
        assertEquals(1, car.getCurrentVelocity().getY());

        car.moveTo(new Coordinate(2, 2));
        assertEquals(1, car.getCurrentVelocity().getX());
        assertEquals(1, car.getCurrentVelocity().getY());

        car.moveTo(new Coordinate(10, 5));
        assertEquals(8, car.getCurrentVelocity().getX());
        assertEquals(3, car.getCurrentVelocity().getY());

        car.moveTo(new Coordinate(18, 13));
        assertEquals(8, car.getCurrentVelocity().getX());
        assertEquals(8, car.getCurrentVelocity().getY());
    }

    @Test
    public void carShouldReturnMaxVelocity() {
        Car car = new Car(0, 0, Color.RED);
        Velocity velocity1 = new Velocity(0, 23);
        Velocity velocity2 = new Velocity(2, 2);
        Velocity velocity3 = new Velocity(10, 23);
        Velocity velocity4 = new Velocity(40, 23);
        Velocity velocity5 = new Velocity(12, 2);
        Velocity velocity6 = new Velocity(213, 500);
        Velocity velocity7 = new Velocity(2, 213);

        car.setVelocity(velocity1);
        car.setVelocity(velocity2);
        car.setVelocity(velocity3);
        car.setVelocity(velocity4);
        car.setVelocity(velocity5);
        car.setVelocity(velocity6);
        car.setVelocity(velocity7);

        assertEquals(velocity6, car.getMaxVelocity());
    }

    @Test
    public void pTurnShouldReturnNine() {
        Car car = new Car(0, 0, Color.RED);
        Coordinate coordinate = new Coordinate(2, 2);
        List<Coordinate> reply = car.getValidMoves();
        assertEquals(9, reply.size());
    }

    @Test
    public void pTurnShouldBeValid() {
        Car car = new Car(8, 4, Color.RED);

        ArrayList<Coordinate> validPoints = new ArrayList<>();
        validPoints.add(new Coordinate(7, 4));
        validPoints.add(new Coordinate(7, 3));
        validPoints.add(new Coordinate(7, 5));
        validPoints.add(new Coordinate(8, 3));
        validPoints.add(new Coordinate(8, 4));
        validPoints.add(new Coordinate(8, 5));
        validPoints.add(new Coordinate(9, 3));
        validPoints.add(new Coordinate(9, 4));
        validPoints.add(new Coordinate(9, 5));

        List<Coordinate> reply = car.getValidMoves();

        for (Coordinate validPoint : validPoints) {
            double x2 = validPoint.getX();
            double y2 = validPoint.getY();

            boolean found = false;
            for (Coordinate replyPoint : reply) {
                double x3 = replyPoint.getX();
                double y3 = replyPoint.getY();
                if (x2 == x3 && y2 == y3) {
                    found = true;
                    break;
                }
            }
            assertTrue(found);
        }
    }
}
