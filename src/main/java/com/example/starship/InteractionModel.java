package com.example.starship;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class InteractionModel {
    private ArrayList<DemoAsteroid> asteroidSet;
    private ArrayList<EnergyBullet> bullets;
    private ArrayList<Subscriber> subscribers;
    private double canvasWidth,canvasHeight;
    private double bulletSpawn = 23;
    private int level,cooldown;
    private long score;
    private Alien alien;

    private enum GAMESTATE {DEAD,GOING,STANDBY}
    private GAMESTATE gameState;
    private double alienBulletSpeed = 6;
    private double playerBulletSpeed = 6;
    InteractionModel(double width, double height){
        asteroidSet = new ArrayList<>();
        bullets = new ArrayList<>();
        subscribers = new ArrayList<>();
        canvasWidth = width;
        canvasHeight = height;
        level = 0;
        cooldown = 200;
        score = 0;
        alien = new Alien();
        gameState = GAMESTATE.STANDBY;
    }
    public void start(){
        System.out.println("Started game");
        level++;
        gameState = GAMESTATE.GOING;
        alien.startTimer();
        createAstroids();
    }

    public void createAstroids(){
        double positionY;
        double positionX;
        for(int x=1;x<=2+level;x++){
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
            asteroidSet.add(new DemoAsteroid(positionX,positionY,canvasWidth,canvasHeight));
        }
        subscribers.forEach(Subscriber::modelChanged);
    }

    public void update() {
        ArrayList<EnergyBullet> cleanBullets = new ArrayList<>();
        asteroidSet.forEach(demoAsteroid->{
            demoAsteroid.move();
        });
        //clean up old bullets
        AtomicInteger popBullet = new AtomicInteger();
        bullets.forEach(bullet->{
            //bullets will expire one at a time
            //TODO:convert bullets to linkedList()
            if (bullet.isTimedOut()){
                popBullet.getAndAdd(1);
            } else {
                bullet.move();
            }
        });
        //bullet has expired, remove it
        if (popBullet.get()>0){
            bullets.removeFirst();
            popBullet.set(0);
        }

        //Terrible design but we need something
        collider();
        //our asteroid set is empty, and thus we must be done the level
        if (asteroidSet.isEmpty()&&level!=0){
            if (cooldown <=0){
                level++;
                if (level > 3 && level % 2 == 0){
                    alien.levelUpAlien();
                }
                createAstroids();
                cooldown = 200;
            }
            cooldown--;
        }
        callAlien();
        notifySubscribers();
    }

    private void collider() {
        if (!bullets.isEmpty() && !asteroidSet.isEmpty()) {
            int bulletStep = 0;
            for (int i = 0; i < bullets.size(); i++) {
                int asteroidStep = 0;
//                if ()
                for (int j = 0; j < asteroidSet.size(); j++) {
                    //test if bullet hit asteroid
                    if (distance(bullets.get(bulletStep).positionX, bullets.get(bulletStep).positionY,
                            asteroidSet.get(asteroidStep).getPositionX() * canvasWidth, asteroidSet.get(asteroidStep).getPositionY() * canvasHeight, asteroidSet.get(asteroidStep).getRadius())) {
                        //is the asteroid destroyed
                        if (asteroidSet.get(asteroidStep).isDestroyed()) {
                            asteroidSet.remove(asteroidStep);
                            bullets.remove(bulletStep);
                            score=score+100;
                            if (asteroidSet.isEmpty() || bullets.isEmpty() || bulletStep <= bullets.size()){
                                break;
                            }
                            //not destroyed just downsize
                        } else {
                            //Add to score
                            if (asteroidSet.get(asteroidStep).getAsteroidSize()== DemoAsteroid.Size.BIG){
                                score = score + 20;
                            } else {
                                score = score + 50;
                            }
                            asteroidSet.add(new LesserAsteroid(asteroidSet.get(asteroidStep).getPositionX(),asteroidSet.get(asteroidStep).getPositionY(),canvasWidth,canvasHeight,asteroidSet.get(asteroidStep)));
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
        asteroidSet.clear();
        bullets.clear();
        alien.die();
        alien.restart();
        level = 0;
        score = 0;
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

        bullets.add(new EnergyBullet(startX,startY,xRatio,yRatio,playerBulletSpeed,false));
    }

    public boolean distance(double bX, double bY, double aX, double aY, double aRadius){
        return pythagoras(bX, bY, aX, aY) < aRadius;
    }
    public double pythagoras(double bX, double bY, double aX, double aY){
        return Math.sqrt((bX-aX)*(bX-aX)+(bY-aY)*(bY-aY));
    }

    public String getScore() {
        return String.format("%07d",score);
    }

    public int getLevel() {
        return level;
    }

    /***
     *  Alien stuff
     */

    public boolean isAlienAlive(){
        return alien.isAlive();
    }

    private void callAlien(){
        if (gameState == GAMESTATE.GOING && alien.isAlive()){
            alien.move();
            shootAlien();
        } else if (gameState == GAMESTATE.GOING && alien.isDead()) {
            alien.respawn();
        }
    }
    public void shootAlien(){
        if (alien.shoot()){
            bullets.add(new EnergyBullet(alienX()*canvasWidth,alienY()*canvasHeight,
                    alien.getxRatio(),alien.getyRatio(),alienBulletSpeed, true));
        }
    }
    public double alienX(){
        return alien.getPositionX();
    }
    public double alienY(){
        return alien.getPositionY();
    }
    public double getAlienWidth() {
        return alien.getWidth();
    }

    public double getAlienHeight() {
        return alien.getHeight();
    }

}
