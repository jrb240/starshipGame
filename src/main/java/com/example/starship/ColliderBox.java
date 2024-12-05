package com.example.starship;

import java.util.ArrayList;

public class ColliderBox {
    private ArrayList<DemoAsteroid> asteroidSet;
    private ArrayList<EnergyBullet> bullets;
    double leftX,rightX,topY,bottomY;
    public ColliderBox(double leftX,double rightX,double topY, double bottomY){
        asteroidSet = new ArrayList<>();
        bullets = new ArrayList<>();
    }

    public boolean isThierConflict() {
        return (!asteroidSet.isEmpty() && !bullets.isEmpty());
    }
}
