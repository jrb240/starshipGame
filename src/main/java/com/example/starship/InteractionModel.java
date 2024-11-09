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
        double positionY;
        double positionX;
        for(int x=1;x<=3;x++){
            double slot = Math.random();
        if ( slot < 0.25){
            //place asteroid on the top
           positionY = 0.1;
           positionX = Math.random();
        } else if (slot < 0.5 ) {
            //place asteroid on the bottom
            positionY = 0.9;
            positionX = Math.random();
        } else if (slot < 0.75) {
            //place asteroid on the left
            positionY = Math.random();
            positionX = 0.1;
        } else {
            //place asteroid on the right
            positionY = Math.random();
            positionX = 0.9;
        }
            asteroidSet.add(new DemoAsteroid(positionX,positionY));
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
//        if (!bullets.isEmpty() && !asteroidSet.isEmpty()){
//            asteroidSet.forEach(asteroid->{
//                System.out.println("haza");
//            });
//        }

        //Terrible design but we need something
        collider();

        notifySubscribers();
    }

    private void collider() {
        if (!bullets.isEmpty() && !asteroidSet.isEmpty()) {
            int bulletStep = 0;
            for (int i = 0; i < bullets.size(); i++) {
                int asteroidStep = 0;
                for (int j = 0; j < asteroidSet.size(); j++) {
                    //test if bullet hit asteroid
                    if (distance(bullets.get(bulletStep).positionX, bullets.get(bulletStep).positionY,
                            asteroidSet.get(asteroidStep).positionX * canvasWidth, asteroidSet.get(asteroidStep).positionY * canvasHeight, asteroidSet.get(asteroidStep).getRadius())) {
                        //is the asteroid destroyed
                        if (asteroidSet.get(asteroidStep).isDestroyed()) {
                            asteroidSet.remove(asteroidStep);
                            bullets.remove(bulletStep);
                            if (asteroidSet.isEmpty() || bullets.isEmpty() || bulletStep <= bullets.size()){
                                break;
                            }
                            //not destroyed just downsize
                        } else {
                            asteroidSet.add(new LesserAsteroid(asteroidSet.get(asteroidStep).getPositionX(),asteroidSet.get(asteroidStep).getPositionY(),asteroidSet.get(asteroidStep)));
                            asteroidSet.get(asteroidStep).sizeDown();
                            bullets.remove(bulletStep);
                            asteroidStep++;
                            if (bullets.isEmpty() || bulletStep <= bullets.size()){
                                break;
                            }
                        }
                    } else {
                        asteroidStep++;
                    }
                }
                bulletStep++;
                if (bullets.isEmpty()){
                    break;
                } else if (bullets.size() <= bulletStep) {
                    break;
                }
            }
        }
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
    public boolean distance(double bX, double bY, double aX, double aY, double aRadius){
        return pythagoras(bX, bY, aX, aY) < aRadius;
    }
    public double pythagoras(double bX, double bY, double aX, double aY){
        return Math.sqrt((bX-aX)*(bX-aX)+(bY-aY)*(bY-aY));
    }
}
