package com.example.starship;

public class ShipCollisionPoints {
    double XPos;
    double YPos;
    double diagonal;
    double xAddedAngle,yAddedAngle;
    double XRatio,YRatio,currentXRatio,currentYRatio;
    enum FLAG{POSXY,POSXNEGY,NEGXPOSY,NEGXY,BLANK}
    FLAG startTile = FLAG.BLANK;
    public ShipCollisionPoints(double x, double y,double scaler){
        if (x>=0 && y>=0){
            startTile = FLAG.POSXY;
        } else if (x>=0 && y<0){
            startTile = FLAG.POSXNEGY;
        } else if (x<0 && y>=0){
            startTile = FLAG.NEGXPOSY;
        } else {
            startTile = FLAG.NEGXY;
        }
        XPos = x*scaler;
        YPos = y*scaler;
        diagonal = pythagoras(x*scaler,y*scaler);
        xAddedAngle = Math.acos(XPos/ diagonal);
        yAddedAngle = Math.asin(YPos/diagonal);
    }

    public void update(double updatedAngle,double x, double y){
        if (startTile==FLAG.POSXY){
            currentXRatio = Math.cos(Math.toRadians(updatedAngle)+xAddedAngle);
            currentYRatio = Math.sin(Math.toRadians(updatedAngle)+yAddedAngle);
        } else if (startTile==FLAG.POSXNEGY) {
            currentXRatio = Math.cos(Math.toRadians(updatedAngle)-xAddedAngle);
            currentYRatio = Math.sin(Math.toRadians(updatedAngle)+yAddedAngle);
        } else if (startTile==FLAG.NEGXPOSY){
            currentXRatio = Math.cos(Math.toRadians(updatedAngle)+xAddedAngle);
            currentYRatio = Math.sin(Math.toRadians(updatedAngle)-yAddedAngle)*-1;
        } else {
            currentXRatio = Math.cos(Math.toRadians(updatedAngle)-xAddedAngle);
            currentYRatio = Math.sin(Math.toRadians(updatedAngle)-yAddedAngle)*-1;
        }
        XPos = currentXRatio*diagonal;
        YPos = currentYRatio*diagonal;
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

    public double getXRatio() {
        return XRatio;
    }

    public double getYRatio() {
        return YRatio;
    }
}
