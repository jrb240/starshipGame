package com.example.starship;

import java.util.ArrayList;

public class ColliderBox {
    private ArrayList<DemoAsteroid> asteroidSet;
    private ArrayList<EnergyBullet> bullets;
    double leftX,rightX,topY,bottomY;

    public ColliderBox(double leftX,double rightX,double topY, double bottomY) {
        asteroidSet = new ArrayList<>();
        bullets = new ArrayList<>();

        this.leftX = leftX;
        this.rightX = rightX;
        this.topY = topY;
        this.bottomY = bottomY;
    }

    public boolean isThereConflict() {
        return (!asteroidSet.isEmpty() && !bullets.isEmpty());
    }

    public void addAsteroid(DemoAsteroid asteroid) {
        asteroidSet.add(asteroid);
    }

    public void addEnergyBullet(EnergyBullet bullet) {
        bullets.add(bullet);
    }

}
