package com.example.starship;

import java.util.Timer;
import java.util.TimerTask;

public class Alien implements EnemyObject {
    enum DIRECTION {STRAIGHT,UP,DOWN}
    enum STATE {ALIVE,DEAD}
    private double movementY = 0.0005;
    private double movementX = 0.001;
    private double angle;
    private  double cooldown = 400;
    private double x,y;
    private Timer timer;
    private TimerTask task;
    private DIRECTION direction = DIRECTION.STRAIGHT;
    private STATE status = STATE.DEAD;

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
        timer = new Timer();
    }
    //restart position
    public void respawn(){
        y = Math.random();
        if (Math.random()> 0.5){
            x = 1.001;
            if (movementX > 0){
                movementX = -movementX;
            }
        } else {
            x = -0.001;
            if (movementX < 0 ){
                movementX = -movementX;
            }
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
        //death by travel
        if (x > 1.0015 || x < -0.0015){
            die();
        }
        if (y > 1.0015){
            y = -0.001;
        } else if (y < -0.0015) {
            y = 1.001;
        }
        if ( x % .2 == 0 ){
            System.out.println("roll");
            changeDir();
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
        if (check < .3){
            direction = DIRECTION.UP;
        } else if (0.7 < check) {
            direction = DIRECTION.DOWN;
        } else {
            direction = DIRECTION.STRAIGHT;
        }
    }
    public boolean isAlive() {
        return status == STATE.ALIVE;
    }
    public void startTimer(){
        task = new TimerTask() {
            @Override
            public void run() {
                status = STATE.ALIVE;
            }
        };
        timer.schedule(task,10000);
    }
    public void stopTimer(){
    }
}
