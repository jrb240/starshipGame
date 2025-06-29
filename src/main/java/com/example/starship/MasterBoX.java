package com.example.starship;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;


/***
 * Goals of this Class
 *
 * This class operates by breaking the canvas into boxes.
 * These boxes are then converted into a data structure.
 * The goal of this data structure is reduce the number of
 * checks for collisions from O(n^2) to a much smaller subset.
 * This data structure will look like this: 1-4-n
 *
 * The current collision system runs through all objects every
 * cycle. On a cycle where there are no things that can collide
 * we'll make O(n) checks.
 * If there are objects that can collide with each other we'll deal
 * with subsets that are smaller than O(n^2) even with the same function.
 */

//start this up on Model or IModel
public class MasterBoX implements PingMasterBox {
    private ArrayList<ColliderBox> collisionAreas;
    private ArrayList<ColliderBox> betweenLayer;
    private HashMap<String,ColliderBox> objectInserterMap;
    private double canvasWidth,canvasHeight;
    public MasterBoX(double canvasWidth,double canvasHeight){
        collisionAreas = new ArrayList<>();
        betweenLayer = new ArrayList<>();
        objectInserterMap = new HashMap<>();
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;
        //top left
        betweenLayer.add(new ColliderBox(0,canvasWidth/2,0,canvasHeight/2));
        //top right
        betweenLayer.add(new ColliderBox(canvasWidth/2,canvasWidth,0,canvasHeight/2));
        //bottom left
        betweenLayer.add(new ColliderBox(0,canvasWidth/2,canvasHeight/2,canvasHeight));
        //bottom right
        betweenLayer.add(new ColliderBox(canvasWidth/2,canvasWidth,canvasHeight/2,canvasHeight));
        //make our bottom layer
        createGrid(betweenLayer,canvasWidth,canvasHeight);
//        for (double xMark = 0; xMark < canvasWidth; xMark = xMark + 100){
//            for (double yMark = 0; yMark < canvasHeight; yMark = yMark + 100){
//                if (yMark < canvasHeight/2 && xMark < canvasWidth/2){ //top right
//                    betweenLayer.getFirst().addChild(new ColliderBox(xMark,xMark+100,yMark,yMark+100));
//                } else if (yMark >= canvasHeight/2 && xMark < canvasWidth/2) { //top left
//                    betweenLayer.get(1).addChild(new ColliderBox(xMark,xMark+100,yMark,yMark+100));
//                } else if (yMark < canvasHeight/2 && xMark >= canvasWidth/2) { //bottom right
//                    betweenLayer.get(2).addChild(new ColliderBox(xMark,xMark+100,yMark,yMark+100));
//                } else if (yMark >= canvasHeight/2 && xMark >= canvasWidth/2) { //bottom left
//                    betweenLayer.getLast().addChild(new ColliderBox(xMark,xMark+100,yMark,yMark+100));
//                } else {
//                    System.out.println("Collision System found out of bounds object");
//                }
//            }
//        }

    }
    private void createGrid(ArrayList<ColliderBox> grid,double canvasWidth, double canvasHeight){
        //fill by columns
        for (double xMark = 0; xMark < canvasWidth; xMark = xMark + 100){
            for (double yMark = 0; yMark < canvasHeight; yMark = yMark + 100){
                ColliderBox currentBox = new ColliderBox(xMark,xMark+100,yMark,yMark+100);
                //TODO: this needs a guard
                String boxLocationName = String.valueOf(Math.round(xMark/100)) +String.valueOf(Math.round(yMark/100));
//                System.out.println(boxLocationName);
                objectInserterMap.put(boxLocationName,currentBox);
                if (yMark < canvasHeight/2 && xMark < canvasWidth/2){ //top right
                    grid.getFirst().addChild(currentBox);
                } else if (yMark >= canvasHeight/2 && xMark < canvasWidth/2) { //top left
                    grid.get(1).addChild(currentBox);
                } else if (yMark < canvasHeight/2 && xMark >= canvasWidth/2) { //bottom right
                    grid.get(2).addChild(currentBox);
                } else if (yMark >= canvasHeight/2 && xMark >= canvasWidth/2) { //bottom left
                    grid.getLast().addChild(currentBox);
                } else {
                    System.out.println("Collision System found out of bounds object");
                }
            }
        }
    }
    private void interlockGridTiles(ArrayList<ColliderBox> grid){
        //first lay is for boxes, and then each has the full boxes
        //TODO: take every box, and start connecting all their children together
        //In 2 steps. Connect the 4, and then connect all grid squares
        ArrayList<ArrayList<ColliderBox>> allCollisionBoxes = new ArrayList<>(); //should replace with hashmap
        String rightColumn = String.valueOf(Math.round(canvasWidth/100));
        long farRightColNum = Math.round(canvasWidth/100);
        String bottomRow = String.valueOf(Math.round(canvasHeight/100));
        long veryBotRow = Math.round(canvasHeight/100);
        //TODO: complete this and remove old version
        for (int x = 0; x < farRightColNum; x++) {
            for (int y = 0; y < veryBotRow; y++) {
                //Found this is actually going to be more effective
                String boxLocationName = String.valueOf(x) +String.valueOf(y);
                if (Objects.equals(boxLocationName, "00")) {
                    ColliderBox curBox = objectInserterMap.get("00");
                    curBox.setTopBox(objectInserterMap.get("0"+bottomRow)); // top
                    curBox.setTopRightBox(objectInserterMap.get("1"+bottomRow)); //top right
                    curBox.setRightBox(objectInserterMap.get("01")); //right
                    curBox.setBotRightBox(objectInserterMap.get("11")); //bottom right
                    curBox.setBotBox(objectInserterMap.get("01")); //bottom
                    curBox.setBotLeftBox(objectInserterMap.get(rightColumn+"0")); //bottom left
                    curBox.setLeftBox(objectInserterMap.get(rightColumn+"0")); // left
                    curBox.setTopLeftBox(objectInserterMap.get(rightColumn+bottomRow)); //top left
                }
            }
        }
        objectInserterMap.forEach((s, colliderBox) -> {
            if (Objects.equals(s, "00")) {
                    colliderBox.setTopBox(objectInserterMap.get("0"+bottomRow));
                    colliderBox.setTopRightBox(objectInserterMap.get("1"+bottomRow));
                    colliderBox.setRightBox(objectInserterMap.get("01"));
                    colliderBox.setBotRightBox(objectInserterMap.get("11"));
                    colliderBox.setBotBox(objectInserterMap.get("01"));
                    colliderBox.setBotLeftBox(objectInserterMap.get(rightColumn+"0"));
                    colliderBox.setLeftBox(objectInserterMap.get(rightColumn+"0"));
                    colliderBox.setTopLeftBox(objectInserterMap.get(rightColumn+bottomRow));
                } else if (Objects.equals(s, rightColumn+bottomRow)) {
                    colliderBox.setRightBox(objectInserterMap.get("0"+bottomRow));
                    colliderBox.setBotBox(objectInserterMap.get(rightColumn+"0"));
                    colliderBox.setBotLeftBox(objectInserterMap.get(rightColumn+bottomRow));
            }
        });

    }
    public boolean isTheirConflict(){
        for (ColliderBox area: betweenLayer){
            if (area.isThereConflict()){
                return true;
            }
        }
        return false;
    }

    public void updateBoxes(){
        betweenLayer.forEach(child->{
            child.update();
        });
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
    public HashMap<String,ColliderBox> getMap(){
        return objectInserterMap;
    }

    public static void main(String[] args) {
        String cut = "**************************************************************************************";
        System.out.println("        **Constructor Testing**");
        MasterBoX testing = new MasterBoX(210,210);
        System.out.println("Constructor ran without issue");
        System.out.println(cut);
        System.out.println("        **Hashmap Testing**");
        System.out.println(testing.getMap().size());
        testing.getMap().forEach((s, colliderBox) -> {
            System.out.println(s);
        });
    }
}
