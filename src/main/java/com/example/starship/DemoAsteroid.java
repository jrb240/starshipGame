package com.example.starship;

import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

import java.awt.*;

/***
 * We are generating some circles to act as asteroids with random spawns
 * and movement speeds
 */

public class DemoAsteroid {
    public double radius;
    double positionX,positionY,dX,dY;
    double speed;

    enum Size{BIG,MED,SMALL}
    Size asteroidSize;
    private WritableImage buffer;
    private PixelReader reader;
    private Canvas offScreenCanvas;
    public DemoAsteroid(double x,double y){
        positionX = x;
        positionY = y;

//        double slot = Math.random();
//        if ( slot < 0.25){
//            //place asteroid on the top
//           positionY = 0.1;
//           positionX = Math.random();
//        } else if (slot < 0.5 ) {
//            //place asteroid on the bottom
//            positionY = 0.9;
//            positionX = Math.random();
//        } else if (slot < 0.75) {
//            //place asteroid on the left
//            positionY = Math.random();
//            positionX = 0.1;
//        } else {
//            //place asteroid on the right
//            positionY = Math.random();
//            positionX = 0.9;
//        }

        //we have placed the asteroid and now we need a direction for it to
        //move in. We have a move speed of at least 1 set.
        speed = Math.random()*5;
        if (speed < 1){
            speed = 1;
        }
        double xDir = Math.random();
        double yDir = Math.random();
        double angle = Math.random()*90;
        //get our speed
        dY = speed*Math.sin(angle);
        dX = speed*Math.cos(angle);
        //now see if they are negative change values
        if (yDir < 0.5){dY = -dY;}
        if (xDir < 0.5){dX = -dX;}
        asteroidSize = Size.BIG;

    }
    //update asteroid position
    public void move(double width, double height){
        positionX = (positionX*width + dX)/width;
        positionY = (positionY*height + dY)/height;
        //TODO: better method of checking this
        //moving left
        if (positionX < -0.002){
            positionX = 1.001;
        }
        //moving right
        if (positionX > 1.002){
            positionX = -0.001;
        }
        //moving up
        if (positionY < -0.002){
            positionY = 1.001;
        }
        //moving down
        if (positionY > 1.002){
            positionY = -0.001;
        }
    }
    public double getPositionY() {
        return positionY;
    }
    public double getPositionX() {
        return positionX;
    }

    public Size getAsteroidSize() {
        return asteroidSize;
    }
    public double getRadius(){
        switch (this.asteroidSize){
            case BIG -> {
                return 55.0;
            }
            case MED -> {
                return 24.5;
            }
            case SMALL -> {
                return 10.6;
            }
        }
        return 55.0;
    }
    public void sizeDown() {
        switch (asteroidSize){
            case BIG -> {
                asteroidSize = Size.MED;
                this.changeDirection();
            }
            case MED -> {
                asteroidSize = Size.SMALL;
                this.changeDirection();
            }
            case SMALL -> asteroidSize = Size.BIG;
        }
    }
    public boolean isDestroyed() {
        if (asteroidSize==Size.SMALL){
            return true;
        }else {
            return false;
        }
    }
    public void changeDirection(){
        //we have placed the asteroid, and now we need a direction for it to
        //move in. We have a move speed of at least 1 set.
        speed = Math.random()*5;
        if (speed < 1){
            speed = 1;
        }
        double xDir = Math.random();
        double yDir = Math.random();
        double angle = Math.random()*90;
        //get our speed
        dY = speed*Math.sin(angle);
        dX = speed*Math.cos(angle);
        //now see if they are negative change values
        if (yDir < 0.5){dY = -dY;}
        if (xDir < 0.5){dX = -dX;}
//        asteroidSize = Size.BIG;
    }

    public void setAsteroidSize(Size asteroidSize) {
        this.asteroidSize = asteroidSize;
    }
}
