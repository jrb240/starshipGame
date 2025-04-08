package com.example.starship;

import java.util.Timer;
import java.util.TimerTask;

public class Alien implements EnemyObject {

    enum DIRECTION {STRAIGHT,UP,DOWN}
    enum STATE {ALIVE,DEAD,BEGIN,TIMER}
    private double movementY = 0.002;
    private double movementX = 0.002;
    private final double closeEdge = -.002;
    private final double farEdge = 1.002;
    private final double height = 20; //double the value we want for math
    private final double width = 50;  // ^ ditto

    private double x,y,xRatio,yRatio;
    private Timer timer;
    private TimerTask task;
    private DIRECTION direction = DIRECTION.STRAIGHT;
    private STATE status = STATE.BEGIN;
    private boolean shoot;
    private int shootChance;
    private double spawnTimer;

    //be created
    public Alien(){
        y = Math.random();
        if (Math.random()> 0.5){
            x = farEdge;
            movementX = -movementX;
        } else {
            x = closeEdge;
        }
        shootChance = 7;
        spawnTimer = 30;
        changeDir();
        timer = new Timer();

    }
    //restart position
    public void respawn(){
        y = Math.random();
        if (Math.random()> 0.5){
            x = farEdge;
            if (movementX > 0){
                movementX = -movementX;
            }
        } else {
            x = closeEdge;
            if (movementX < 0 ){
                movementX = -movementX;
            }
        }
        task = new TimerTask() {
            @Override
            public void run() {
                flipRequest();
            }
        };
        status= STATE.TIMER;
        double delay = spawnTimer+(spawnTimer*Math.random())/10;
        timer.schedule(task, (long) delay*1000);
    }

    @Override
    public void move() {
        x = x + movementX;
        if (direction == DIRECTION.DOWN){
            y = y + movementY;
        } else if (direction == DIRECTION.UP){
            y = y + movementY;
        }
        //death by travel
        if (x > farEdge || x < closeEdge){
            die();
        }
        //roll over to the other side on vertical travel
        if (y > farEdge){
            y = closeEdge;
        } else if (y < closeEdge) {
            y = farEdge;
        }
        //roll to change direction and if you should shoot
        if ( x % .2 < 0.003 ){
            changeDir();
            rollShoot();
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

    @Override
    public boolean isThisAHit(double x, double Y) {

        return false;
    }

    public double getxRatio() {
        return xRatio;
    }

    public double getyRatio() {
        return yRatio;
    }

    public boolean shoot(){
        if (shoot){
            shoot = false;
            return true;
        }
        return false;
    }
    public void rollShoot(){
        xRatio = (Math.random()-.5)/0.5;
        yRatio = (Math.random()-.5)/.5;
        shoot = Math.round(Math.random() * 10) > shootChance;
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
    public boolean isDead() {
        return status == STATE.DEAD;
    }
    public void die(){
        status = STATE.DEAD;
    }
    public void restart(){
        status = STATE.BEGIN;
        spawnTimer = 30.0;
        shootChance = 7;
    }
    public void startTimer(){
        status = STATE.TIMER;
        task = new TimerTask() {
            @Override
            public void run() {
                flipRequest();
            }
        };
        timer.schedule(task,30000);
    }
    private void flipRequest(){
        if (status != STATE.BEGIN){
            status = STATE.ALIVE;
        }
    }
    public void levelUpAlien(){
        if (shootChance>0){
            shootChance -=1;
        }
        if (spawnTimer >5){
            spawnTimer -=2;
        }
        System.out.println("Shoot Cahnce: "+shootChance+"\nSpawn Timer: "+spawnTimer);
    }
    public double pythagoras(double bX, double bY, double aX, double aY){
        return Math.sqrt((bX-aX)*(bX-aX)+(bY-aY)*(bY-aY));
    }
    //TODO: Divide the by the canvas demensions
    public double elipticalRadius(double x,double y){


        double xDif = Math.abs(this.x - x);
        double yDif = Math.abs(this.y - y);
        double angle = Math.atan(y/x);

        return  ((height*width)/2)/ Math.sqrt((width/2)*(width/2)*Math.sin(angle)*Math.sin(angle)
                +(height/2)*(height/2)*Math.cos(angle)*Math.cos(angle));
//        return 0;
    }
    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
}
