package com.example.starship;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class InteractionModel {
    ArrayList<DemoAsteroid> asteroidSet;
    ArrayList<EnergyBullet> bullets;
    ArrayList<EnergyBullet> cleanBullets;
    ArrayList<Subscriber> subscribers;
    double canvasWidth,canvasHeight;
    double bulletSpawn = 23;
    InteractionModel(double width, double height){
        asteroidSet = new ArrayList<>();
        bullets = new ArrayList<>();
        subscribers = new ArrayList<>();
        canvasWidth = width;
        canvasHeight = height;
    }
    public void start(){
        System.out.println("Started game");
        for(int x=1;x<=3;x++){
            asteroidSet.add(new DemoAsteroid());
        }
        subscribers.forEach(Subscriber::modelChanged);
    }
    public void update() {
        ArrayList<EnergyBullet> cleanBullets = new ArrayList<>();
        asteroidSet.forEach(demoAsteroid->{
            demoAsteroid.move(canvasWidth,canvasHeight);
        });
        AtomicInteger popBullet = new AtomicInteger();

        bullets.forEach(bullet->{
            if (bullet.isTimedOut()){
                popBullet.getAndAdd(1);
//                bullets.remove(bullet);
            } else {
                bullet.move();
            }
        });
        if (popBullet.get()>0){
            bullets.remove(0);
            popBullet.set(0);
        }
        if (!bullets.isEmpty() && !asteroidSet.isEmpty()){
            asteroidSet.forEach(asteroid->{
            });
        }

        notifySubscribers();
    }

    public void restart() {
    }
    public ArrayList<DemoAsteroid> getAsteroids() {
        return asteroidSet;
    }
    public void addSubscriber(SpaceView view) {
        subscribers.add(view);
    }
    public void notifySubscribers(){
        subscribers.forEach(Subscriber::modelChanged);
    }
    public ArrayList<EnergyBullet> getBullets() {
        return bullets;
    }

    public void shoot(double mouseX, double mouseY, double playerX, double playerY) {
        //using this to get our ratio
        double hypotenuse = Math.sqrt((mouseX-playerX)*(mouseX-playerX)
                        +(mouseY-playerY)*(mouseY-playerY));
        double xRatio = (mouseX-playerX)/hypotenuse;
        double yRatio = (mouseY-playerY)/hypotenuse;

        double startX = bulletSpawn* xRatio + playerX;
        double startY = bulletSpawn* yRatio + playerY;

        bullets.add(new EnergyBullet(startX,startY,xRatio,yRatio,5));
    }
}
