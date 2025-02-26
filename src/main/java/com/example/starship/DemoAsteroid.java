package com.example.starship;

/***
 * We are generating some circles to act as asteroids with random spawns
 * and movement speeds
 */

public class DemoAsteroid implements EnemyObject {
    public double radius;
    private double positionX,positionY,dX,dY;
    private double speed;
    enum Size{BIG,MED,SMALL}
    Size asteroidSize;
    private boolean faces;
    private double canvasWidth,canvasHeight;
    //getRadius returns
    double bigAsteroid = 55.0;
    double medAsteroid = 24.6;
    double smallAsteroid = 10.6;

    public DemoAsteroid(double x,double y, double canvasWidth,double canvasHeight){
        positionX = x;
        positionY = y;
        faces = false;
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;

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
        asteroidSize = Size.BIG;

    }


    public Size getAsteroidSize() {
        return asteroidSize;
    }
    public double getRadius(){
        switch (this.asteroidSize){
            case BIG -> {
                return bigAsteroid;
            }
            case MED -> {
                return medAsteroid;
            }
            case SMALL -> {
                return smallAsteroid;
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
    @Override
    public void move() {
        positionX = (positionX*canvasWidth + dX)/canvasWidth;
        positionY = (positionY*canvasHeight + dY)/canvasHeight;
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

    @Override
    public double getPositionX() {
        return positionX;
    }

    @Override
    public double getPositionY() {
        return positionY;
    }
    public void addFaces() {
        faces = true;
    }
    public boolean hasFaces(){return faces;}
}
