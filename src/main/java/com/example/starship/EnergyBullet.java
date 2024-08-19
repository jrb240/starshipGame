package com.example.starship;

//TODO: bullets are currently absolute and not relative.
public class EnergyBullet {
    double positionX,positionY;
    private double dX,dY,bulletAngle;
    double bulletSpeed;
    //these are to convert our general angles into dX and dY values
    //will be positive or negative 1 only values
    private double xRatio,yRatio;
    private int life;
    public EnergyBullet(double startX,double startY, double xVector, double yVector,double bulletSpeed) {
        positionX = startX;
        positionY = startY;
        life = 50;

        //set default bullet speed
        this.bulletSpeed = bulletSpeed;
//        xDirection = xVector*bulletSpeed;
//        yDirection = yVector*bulletSpeed;
        xRatio = xVector;
        yRatio = yVector;
//        dX = Math.tan(bulletAngle)*xDirection*bulletSpeed;
//        dY = Math.tan(bulletAngle)*yDirection*bulletSpeed;
        dX = xVector*bulletSpeed;
        dY = yVector*bulletSpeed;
    }
    public void move(){
        life -=1;
        positionX = positionX + dX;
        positionY = positionY + dY;
    }
    public void setBulletSpeed(double bulletSpeed) {
        this.bulletSpeed = bulletSpeed;
        dX = xRatio*bulletSpeed;
        dY = yRatio*bulletSpeed;
    }
    public double getPositionX(){
        return this.positionX;
    }
    public double getPositionY() {
        return this.positionY;
    }
    public Boolean isTimedOut(){
        return life < 0;
    }
}
