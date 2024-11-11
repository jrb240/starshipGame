package com.example.starship;

public class ShipCollisionPoints {
    double XPos;
    double YPos;
    double diagonal;
    double XRatio,YRatio;
    public ShipCollisionPoints(double x, double y){
        XPos = x;
        YPos = y;
        diagonal = pythagoras(x,y);
        XRatio = x/diagonal;
        YRatio = y/diagonal;
    }

    public void update(double angle){
        XRatio = XRatio + Math.cos(angle);
        YRatio = YRatio + Math.sin(angle);
    }

    public double getXPos() {
        return XPos;
    }

    public double getYPos() {
        return YPos;
    }

    public void setXPos(double XPos) {
        this.XPos = XPos;
    }

    public void setYPos(double YPos) {
        this.YPos = YPos;
    }
    public double pythagoras(double x, double y){
        return Math.sqrt((x*x+y*y));
    }
}
