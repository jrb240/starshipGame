package com.example.starship;

public class ShipCollisionPoints {
    double XPos;
    double YPos;
    double diagonal;
    double XRatio,YRatio,currentXRatio,currentYRatio;
    public ShipCollisionPoints(double x, double y,double scaler){
        XPos = x;
        YPos = y;
        diagonal = pythagoras(x,y);
        XRatio = x/diagonal;
        YRatio = y/diagonal;
        currentXRatio = XRatio;
        currentYRatio = YRatio;
        diagonal = diagonal*scaler;
    }

    public void update(double angle,double x, double y){
        currentXRatio = XRatio + Math.cos(angle);
        currentYRatio = YRatio + Math.sin(angle);

        XPos = x + currentXRatio*diagonal;
        YPos = y + currentYRatio*diagonal;
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
