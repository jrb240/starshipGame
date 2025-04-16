package com.example.starship;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

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
