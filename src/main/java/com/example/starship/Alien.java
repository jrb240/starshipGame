package com.example.starship;

public class Alien implements EnemyObject {
    enum DIRECTION {STRAIGHT,UP,DOWN}
    private double movementY = 0.00005;
    private double movementX = 0.0001;
    private double angle;
    private  double cooldown = 400;
    private double x,y;
    public Alien(){
        y = Math.random();
        if (Math.random()> 0.5){
            x = 1.001;
            movementX = -movementX;
        } else {
            x = -0.001;
        }
    }
    @Override
    public void move(double width, double height) {
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
}
