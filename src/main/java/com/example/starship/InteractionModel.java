package com.example.starship;

import java.util.ArrayList;
import java.util.List;

public class InteractionModel {
    ArrayList<DemoAsteroid> asteroidSet;
    ArrayList<EnergyBullet> bullets;
    ArrayList<Subscriber> subscribers;
    InteractionModel(){
        asteroidSet = new ArrayList<>();
        bullets = new ArrayList<>();
        subscribers = new ArrayList<>();
    }
    public void start(){
        for(int x=1;x<=3;x++){
            asteroidSet.add(new DemoAsteroid());
        }
        subscribers.forEach(Subscriber::modelChanged);
    }

    public void update() {
    }

    public void restart() {
    }

    public ArrayList<DemoAsteroid> getAsteroids() {
        return asteroidSet;
    }
}
