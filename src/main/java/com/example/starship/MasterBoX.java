package com.example.starship;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

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
    public MasterBoX(double canvasWidth,double canvasHeight){
        collisionAreas = new ArrayList<>();
        betweenLayer = new ArrayList<>();
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
        for (double xMark = 0; xMark < canvasWidth; xMark = xMark + 100){
            for (double yMark = 0; yMark < canvasHeight; yMark = yMark + 100){
                if (yMark < canvasHeight/2 && xMark < canvasWidth/2){ //top right
                    grid.getFirst().addChild(new ColliderBox(xMark,xMark+100,yMark,yMark+100));
                } else if (yMark >= canvasHeight/2 && xMark < canvasWidth/2) { //top left
                    grid.get(1).addChild(new ColliderBox(xMark,xMark+100,yMark,yMark+100));
                } else if (yMark < canvasHeight/2 && xMark >= canvasWidth/2) { //bottom right
                    grid.get(2).addChild(new ColliderBox(xMark,xMark+100,yMark,yMark+100));
                } else if (yMark >= canvasHeight/2 && xMark >= canvasWidth/2) { //bottom left
                    grid.getLast().addChild(new ColliderBox(xMark,xMark+100,yMark,yMark+100));
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
        betweenLayer.forEach(box->{
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
}
