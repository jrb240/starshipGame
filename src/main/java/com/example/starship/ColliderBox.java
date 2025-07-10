package com.example.starship;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;


public class ColliderBox {
    private LinkedList<DemoAsteroid> asteroidSet;
    private LinkedList<DemoAsteroid> controlledAsteroidset;
    private LinkedList<EnergyBullet> bullets;
    private LinkedList<EnergyBullet> controlledBullets;
    private double leftX,rightX,topY,bottomY;
    private ColliderBox rightBox,leftBox,topBox,botBox,topRightBox,topLeftBox,botRightBox,botLeftBox,parent;
    private ArrayList<ColliderBox> children;
    private HashMap<String,ColliderBox> childrenMap;

    public ColliderBox(double leftX,double rightX,double topY, double bottomY) {
        asteroidSet = new LinkedList<>();
        controlledAsteroidset = new LinkedList<>();
        bullets = new LinkedList<>();
        controlledBullets = new LinkedList<>();
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
            return !(asteroidSet.isEmpty() && bullets.isEmpty());
        }
    }

    public void addControlledAsteroid(DemoAsteroid asteroid) {
        controlledAsteroidset.add(asteroid);
    }

    public void addEnergyBullet(EnergyBullet bullet) {
        bullets.add(bullet);
    }

    public void addChild(ColliderBox addChild){
        if (childrenMap == null) {
            childrenMap = new HashMap<>();
        }
        children.add(addChild);
        childrenMap.put(addChild.getName(),addChild);
        addChild.setParent(this);
    }

    public void update() {
        //collider boxes should probably handle moving as there are a
        //few things that need to happen after things have moved
        if (!asteroidSet.isEmpty()) {
            asteroidSet.forEach(DemoAsteroid::move);
        }
        if (!bullets.isEmpty()) {
            bullets.forEach(EnergyBullet::move);
        }
    }
    public void insertAsteroidIntoCollider(DemoAsteroid newAsteroid){
        if (children.isEmpty()) {
            addControlledAsteroid(newAsteroid);
        } else {
            //TODO: complete this. This is placing the asteroids into the smaller boxes
            double placeX = newAsteroid.getPositionX();
            double placeY = newAsteroid.getPositionY();
            double size = newAsteroid.getRadius();
        }
    }

    public ArrayList<ColliderBox> getChildren() {
        return children;
    }

    public void setBotBox(ColliderBox botBox) {
        this.botBox = botBox;
    }

    public ColliderBox getBotBox() {
        return botBox;
    }

    public void setBotLeftBox(ColliderBox botLeftBox) {
        this.botLeftBox = botLeftBox;
    }

    public ColliderBox getBotLeftBox() {
        return botLeftBox;
    }

    public void setBotRightBox(ColliderBox botRightBox) {
        this.botRightBox = botRightBox;
    }

    public ColliderBox getBotRightBox() {
        return botRightBox;
    }

    public void setLeftBox(ColliderBox leftBox) {
        this.leftBox = leftBox;
    }

    public ColliderBox getLeftBox() {
        return leftBox;
    }

    public void setRightBox(ColliderBox rightBox) {
        this.rightBox = rightBox;
    }

    public ColliderBox getRightBox() {
        return rightBox;
    }

    public void setTopRightBox(ColliderBox topRightBox) {
        this.topRightBox = topRightBox;
    }

    public ColliderBox getTopRightBox() {
        return topRightBox;
    }

    public void setTopBox(ColliderBox topBox) {
        this.topBox = topBox;
    }

    public ColliderBox getTopBox() {
        return topBox;
    }

    public void setTopLeftBox(ColliderBox topLeftBox) {
        this.topLeftBox = topLeftBox;
    }

    public ColliderBox getTopLeftBox() {
        return topLeftBox;
    }

    public String getName(){
        return String.valueOf(Math.round(leftX/100))+String.valueOf(Math.round(topY/100));
    }

    public void setParent(ColliderBox parent){
        this.parent = parent;
    }
}
