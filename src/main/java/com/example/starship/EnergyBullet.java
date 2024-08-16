package com.example.starship;

public class EnergyBullet {
    double positionX,positionY;
    private double dX,dY,bulletAngle;
    double bulletSpeed;
    //these are to convert our general angles into dX and dY values
    //will be positive or negative 1 only values
    private int xDirection,yDirection;
    public EnergyBullet(double startX,double startY, int xVector, int yVector, double angle,double bulletSpeed) {
        positionX = startX;
        positionY = startY;

        //set default bullet speed
        this.bulletSpeed = bulletSpeed;
        bulletAngle = angle;
        xDirection = xVector;
        yDirection = yVector;

        dX = Math.tan(bulletAngle)*xDirection*bulletSpeed;
        dY = Math.tan(bulletAngle)*yDirection*bulletSpeed;
    }
    public void move(){
        positionX = positionX + dX;
        positionY = positionY + dY;
    }

    public void setBulletSpeed(double bulletSpeed) {
        this.bulletSpeed = bulletSpeed;
        dX = Math.tan(bulletAngle)*xDirection*bulletSpeed;
        dY = Math.tan(bulletAngle)*yDirection*bulletSpeed;
    }

    public double getPositionX(){
        return this.positionX;
    }
    public double getPositionY() {
        return this.positionY;
    }
}
