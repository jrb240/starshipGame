package com.example.starship;

public class Star {
    double locationX,locationY;
    public Star(){
        locationY = Math.random();
        locationX = Math.random();
    }
    public double getLocationX() {
        return locationX;
    }
    public double getLocationY() {
        return locationY;
    }
}
