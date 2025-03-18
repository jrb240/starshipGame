package com.example.starship;

public interface EnemyObject {
    public void move();
    public double getPositionX();
    public double getPositionY();
    public boolean isThisAHit(double x, double Y);
}
