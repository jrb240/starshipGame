package com.example.starship;

import java.util.ArrayList;
import java.util.HashMap;


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
    private HashMap<String,ColliderBox> lowestLayerHashmap,betweenLayerHashmap;
    private String TL = "TOP LEFT";
    private String TR = "TOP RIGHT";
    private String BL = "BOTTOM LEFT";
    private String BR = "BOTTOM RIGHT";
    private double canvasWidth,canvasHeight;
    public MasterBoX(double canvasWidth,double canvasHeight){
        collisionAreas = new ArrayList<>();
        betweenLayer = new ArrayList<>();
        lowestLayerHashmap = new HashMap<>();
        betweenLayerHashmap = new HashMap<>();
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
        interlockGridTiles();
    }
    private void createGrid(ArrayList<ColliderBox> grid,double canvasWidth, double canvasHeight){
        //fill by columns
        for (double xMark = 0; xMark < canvasWidth; xMark = xMark + 100){
            for (double yMark = 0; yMark < canvasHeight; yMark = yMark + 100){
                ColliderBox currentBox = new ColliderBox(xMark,xMark+100,yMark,yMark+100);
                //TODO: this needs a guard
                String boxLocationName = String.valueOf(Math.round(xMark/100)) +String.valueOf(Math.round(yMark/100));
//                System.out.println(boxLocationName);
                lowestLayerHashmap.put(boxLocationName,currentBox);
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
    private void interlockGridTiles(){
        if (lowestLayerHashmap.isEmpty()){
            return;
        }
        //first lay is for boxes, and then each has the full boxes
        //In 2 steps. Connect the 4, and then connect all grid squares
        ArrayList<ArrayList<ColliderBox>> allCollisionBoxes = new ArrayList<>(); //should replace with hashmap
        String rightColumn = String.valueOf(Math.round(canvasWidth/100));
        long farRightColNum = Math.round(canvasWidth/100)-1;
        String bottomRow = String.valueOf(Math.round(canvasHeight/100));
        long veryBotRow = Math.round(canvasHeight/100)-1;

        String topLeft,topMid,topRight,midLeft,midRight,botLeft,botMid,botRight;

        //Well could be better, but this will work
        for (int x = 0; x < farRightColNum+1; x++) {
            for (int y = 0; y < veryBotRow+1; y++) {
                long xShiftRight = x + 1;
                long yShiftUp = y - 1;
                long xShiftLeft = x - 1;
                long yShiftDown = y + 1;

                //handle rollover
                if (x+1 > farRightColNum) { xShiftRight = 0;}
                if (x-1 < 0) { xShiftLeft = farRightColNum;}
                if (y - 1 < 0) { yShiftUp = veryBotRow;}
                if (y + 1 > veryBotRow) { yShiftDown = 0;}

                topLeft = String.valueOf(xShiftLeft) +String.valueOf(yShiftUp);
                topMid = String.valueOf(x) +String.valueOf(yShiftUp);
                topRight = String.valueOf(xShiftRight) +String.valueOf(yShiftUp);
                midLeft = String.valueOf(xShiftLeft) +String.valueOf(y);
                midRight = String.valueOf(xShiftRight) +String.valueOf(y);
                botLeft = String.valueOf(xShiftLeft) +String.valueOf(yShiftDown);
                botMid = String.valueOf(x) +String.valueOf(yShiftDown);
                botRight = String.valueOf(xShiftRight) +String.valueOf(yShiftDown);

                String boxLocationName = String.valueOf(x) +String.valueOf(y);
                ColliderBox curBox = lowestLayerHashmap.get(boxLocationName);

                curBox.setTopLeftBox(lowestLayerHashmap.get(topLeft));   //top left
                curBox.setTopBox(lowestLayerHashmap.get(topMid));        //top
                curBox.setTopRightBox(lowestLayerHashmap.get(topRight)); //top right
                curBox.setLeftBox(lowestLayerHashmap.get(midLeft));      //left
                curBox.setRightBox(lowestLayerHashmap.get(midRight));    //right
                curBox.setBotLeftBox(lowestLayerHashmap.get(botLeft));   //bottom left
                curBox.setBotBox(lowestLayerHashmap.get(botMid));        //bottom
                curBox.setBotRightBox(lowestLayerHashmap.get(botRight)); //bottom right
            }
        }
    }

    /**
     * Start up complete
     * Rest are functions calls to be done during operation
     */

    public boolean isTheirConflict(){
        for (ColliderBox area: betweenLayer){
            if (area.isThereConflict()){
                area.isThereConflict();
            }
        }
        return false;
    }

    public void updateBoxes(){
        betweenLayer.forEach(child->{
            child.update();
        });
    }

    public void addEnemyObject(EnemyObject nThing){
        String position = gridLocationToHashmapKey(nThing.getPositionX(),nThing.getPositionY());
//        lowestLayerHashmap.get(position).addControlledAsteroid(nThing);
    }
    public void addAsteroid(DemoAsteroid asteroid){
        String position = gridLocationToHashmapKey(asteroid.getPositionX(),asteroid.getPositionY());
        lowestLayerHashmap.get(position).addControlledAsteroid(asteroid);
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
        return lowestLayerHashmap;
    }

    private String gridLocationToHashmapKey(double x, double y){
        return String.valueOf(Math.round(x/100)+String.valueOf(Math.round(y/100)));
    }
    private String twoNumsToHashmapKey(double x, double y){
        return String.valueOf(x)+String.valueOf(y);
    }

    public static void main(String[] args) {
        String cut = "**************************************************************************************";
        System.out.println("            **Constructor Testing**");
        MasterBoX testing = new MasterBoX(1600,800);
        System.out.println("            **Constructor ran without issue**");
        System.out.println(cut);
        System.out.println("            **Hashmap Testing**");
        System.out.println(testing.getMap().size());
        testing.getMap().forEach((s, colliderBox) -> {
            if (colliderBox.getTopLeftBox() == null) {
                System.out.println(s + ": Failed to get TLB");
            }
            if (colliderBox.getTopBox() == null) {
                System.out.println(s + ": Failed to get TMB");
            }
            if (colliderBox.getTopRightBox() == null) {
                System.out.println(s + ": Failed to get TRB");
            }
            if (colliderBox.getLeftBox() == null) {
                System.out.println(s + ": Failed to get LB");
            }
            if (colliderBox.getRightBox() == null) {
                System.out.println(s + ": Failed to get RB");
            }
            if (colliderBox.getBotLeftBox() == null) {
                System.out.println(s + ": Failed to get BLB");
            }
            if (colliderBox.getBotBox() == null) {
                System.out.println(s + ": Failed to get BMB");
            }
            if (colliderBox.getBotRightBox() == null) {
                System.out.println(s + ": Failed to get BRB");
            }
        });
        System.out.println("            **Hashmap Testing Complete**");
        System.out.println(cut);
        System.out.println("            **Object Placement Testing**");

        System.out.println("            **Object Placement Testing Complete**");
        System.out.println(cut);
    }
}
