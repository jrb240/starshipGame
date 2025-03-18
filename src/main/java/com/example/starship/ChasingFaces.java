package com.example.starship;

public class ChasingFaces implements EnemyObject {
    private double x,y;
    private double speed = 0.003;
    private double angle;
    public ChasingFaces(double spawnX,double spawnY, double playerX,double playerY){
        x = spawnX;
        y = spawnY;
        angle = Math.random()*360;
        correction(playerX,playerY);
    }
    @Override
    public void move() {

    }
    public void pushed(){}

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

    public void correction(double playerXPosition,double playerYPosition){

    }
}
