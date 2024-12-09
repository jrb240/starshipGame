package com.example.starship;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

//start this up on Model or IModel
public class MasterBoX implements PingMasterBox {
    private ArrayList<ColliderBox> collisionAreas;
    public MasterBoX(double canvasWidth,double canvasHeight){
        collisionAreas = new ArrayList<>();
        for (double xMark = 0; xMark < canvasWidth; xMark = xMark + 100){
            for (double yMark = 0; yMark < canvasHeight; yMark = yMark + 100){
                collisionAreas.add(new ColliderBox(xMark,yMark,xMark+100,yMark+100));
            }
        }
    }
    public boolean isTheirConflict(){
        for (ColliderBox area: collisionAreas){
            if (area.isThereConflict()){
                return true;
            }
        }
        return false;
    }

    public void updateBoxes(){

    }

    @Override
    public void passBulletLeft(EnergyBullet bullet) {

    }

    @Override
    public void passBulletRight(EnergyBullet bullet) {

    }

    @Override
    public void passBulletUp(EnergyBullet bullet) {

    }

    @Override
    public void passBulletDown(EnergyBullet bullet) {

    }

    @Override
    public void passAsteroidLeft(DemoAsteroid asteroid) {

    }

    @Override
    public void passAsteroidRight(DemoAsteroid asteroid) {

    }

    @Override
    public void passAsteroidUp(DemoAsteroid asteroid) {

    }

    @Override
    public void passAsteroid(DemoAsteroid asteroid) {

    }
}
