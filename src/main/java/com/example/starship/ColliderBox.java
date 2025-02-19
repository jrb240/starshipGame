package com.example.starship;

import java.util.ArrayList;

public class ColliderBox {
    private ArrayList<DemoAsteroid> asteroidSet;
    private ArrayList<EnergyBullet> bullets;
    private double leftX,rightX,topY,bottomY;
    private ColliderBox rightBox,leftBox,topBox,botBox,topRightBox,topLeftBox,botRightBox,botLeftBox;
    private ArrayList<ColliderBox> children;

    public ColliderBox(double leftX,double rightX,double topY, double bottomY) {
        asteroidSet = new ArrayList<>();
        bullets = new ArrayList<>();
        children = new ArrayList<>();

        this.leftX = leftX;
        this.rightX = rightX;
        this.topY = topY;
        this.bottomY = bottomY;
    }

    public boolean isThereConflict() {
        //if this box has children then it shouldn't have bullets
        //or asteroids
        if (!children.isEmpty()) {
            for (ColliderBox child : children){
                if (child.isThereConflict()){
                    return true;
                }
            }
            return false;
        } else {
            return (!asteroidSet.isEmpty() && !bullets.isEmpty());
        }
    }

    public void addAsteroid(DemoAsteroid asteroid) {
        asteroidSet.add(asteroid);
    }

    public void addEnergyBullet(EnergyBullet bullet) {
        bullets.add(bullet);
    }

    public void addChild(ColliderBox addChild){
        children.add(addChild);
    }

    public void setBotBox(ColliderBox botBox) {
        this.botBox = botBox;
    }

    public void setBotLeftBox(ColliderBox botLeftBox) {
        this.botLeftBox = botLeftBox;
    }

    public void setBotRightBox(ColliderBox botRightBox) {
        this.botRightBox = botRightBox;
    }

    public void setLeftBox(ColliderBox leftBox) {
        this.leftBox = leftBox;
    }

    public void setRightBox(ColliderBox rightBox) {
        this.rightBox = rightBox;
    }

    public void setTopRightBox(ColliderBox topRightBox) {
        this.topRightBox = topRightBox;
    }

    public void setTopBox(ColliderBox topBox) {
        this.topBox = topBox;
    }

    public void setTopLeftBox(ColliderBox topLeftBox) {
        this.topLeftBox = topLeftBox;
    }

    public void update() {
        if (!asteroidSet.isEmpty()) {
            asteroidSet.forEach(demoAsteroid -> {
                demoAsteroid.move();
            });
        }
        if (!bullets.isEmpty()) {
            bullets.forEach(EnergyBullet::move);
        }
    }
}
