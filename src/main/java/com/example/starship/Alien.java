package com.example.starship;

public class Alien implements EnemyObject {
    enum DIRECTION {STRAIGHT,UP,DOWN}
    enum STATE {ALIVE,DEAD}
    private double movementY = 0.00005;
    private double movementX = 0.0001;
    private double angle;
    private  double cooldown = 400;
    private double x,y;
    DIRECTION direction = DIRECTION.STRAIGHT;
    STATE status = STATE.DEAD;

    //be created
    public Alien(){
        y = Math.random();
        if (Math.random()> 0.5){
            x = 1.001;
            movementX = -movementX;
        } else {
            x = -0.001;
        }
        changeDir();
    }
    //restart position
    public void respawn(){
        y = Math.random();
        if (Math.random()> 0.5){
            x = 1.001;
            movementX = -movementX;
        } else {
            x = -0.001;
        }
        changeDir();
        status = STATE.ALIVE;
    }
    public void die(){
        status = STATE.DEAD;
    }
    @Override
    public void move(double width, double height) {
        x = x + movementX;
        if (direction == DIRECTION.DOWN){
            y = y + movementY;
        } else if (direction == DIRECTION.UP){
            y = y + movementY;
        }
    }

    @Override
    public double getPositionX() {
        return x;
    }

    @Override
    public double getPositionY() {
        return y;
    }
    public void shoot(){
        //EnergyBullet();
    }
    public void changeDir(){
        double check = Math.random();
        if (check < .20){
            direction = DIRECTION.UP;
        } else if (0.8 < check) {
            direction = DIRECTION.DOWN;
        } else {
            direction = DIRECTION.STRAIGHT;
        }
    }
    public boolean isAlive() {
        return status == STATE.ALIVE;
    }
}
