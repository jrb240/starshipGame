package com.example.starship;

/***
 * We are generating some circles to act as asteroids with random spawns
 * and movement speeds
 */

public class DemoAsteroid {
    double positionX,positionY,dX,dY;
    double speed;
    enum Size{BIG,MED,SMALL}
    Size asteroidSize;
    public DemoAsteroid(){

        double slot = Math.random();
        if ( slot < 0.25){
            //place asteroid on the top
           positionY = 0.1;
           positionX = Math.random();
        } else if (slot < 0.5 ) {
            //place asteroid on the bottom
            positionY = 0.9;
            positionX = Math.random();
        } else if (slot < 0.75) {
            //place asteroid on the left
            positionY = Math.random();
            positionX = 0.1;
        } else {
            //place asteroid on the right
            positionY = Math.random();
            positionX = 0.9;
        }

        //we have placed the asteroid and now we need a direction for it to
        //move in. We have a move speed of at least 1 set.
        speed = Math.random()*10;
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
    public void move(){
        positionX = positionX + dX;
        positionY = positionY + dY;
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
}
