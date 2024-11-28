package com.example.starship;

import javafx.scene.image.Image;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class Player {
    private double posX,posY,angle;
    private double xSpeed;
    private double ySpeed;
    private double increment = 0.001;
    private double timer;
    private BufferedImage ship;
    private Image testShip;
    private double scaler = 0.25;
    private ArrayList<ShipCollisionPoints> hitBox;

    public Player(){
        posX = 0.5;
        posY = 0.5;
        angle = 0;

        xSpeed = 0;
        ySpeed = 0;
        timer = 0;

        hitBox = new ArrayList<>();

        ship = null;
//        scaler = 0.25;
        //182x121
//        testShip = new Image("ship_cut.png");
//        getShipImage();
        testShip = shipSelection("ship_cut.png");

    }

    public void getShipImage(){
        try {
            ship = ImageIO.read(getClass().getResourceAsStream("ship_cut.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Image getShip(){
        return testShip;
    }

    public double getPosX() {
        return posX;
    }
    public double getPosY() {
        return posY;
    }
    public void setPosX(double posX) {
        this.posX = posX;
    }
    public void setPosY(double posY) {
        this.posY = posY;
    }

    public double getAngle() {
        return this.angle;
    }

    public void setAngle(double newAngle) {
        this.angle = newAngle;
        for (ShipCollisionPoints box : this.hitBox) {
            box.update(newAngle,this.posX,this.posY);
        }
    }

    public void increaseSpeed(double xRatio,double yRatio) {
        xSpeed =xSpeed+ increment*xRatio;
        ySpeed =ySpeed+ increment*yRatio;
        timer = 100;
    }
    public void decreaseSpeed(){
        xSpeed = xSpeed * 0.7;
        ySpeed = ySpeed * 0.7;
    }

    public void update() {
        posX =posX+xSpeed;
        posY =posY+ySpeed;
//        for (ShipCollisionPoints box : this.hitBox) {
//            box.update(getAngle(),getPosX(),getPosY());
//        }
        if (posX < -0.002){
            posX = 1.001;
        }
        //moving right
        if (posX > 1.002){
            posX = -0.001;
        }
        //moving up
        if (posY < -0.002){
            posY = 1.001;
        }
        //moving down
        if (posY > 1.002){
            posY = -0.001;
        }
        timer = timer - 1;
        if (timer < 0){
            decreaseSpeed();
            timer = 20;
        }
    }

    public double getScaler() {
        return scaler;
    }
    //TODO:Remake this with a ship object as there is too much to keep track of
    public Image shipSelection(String shipName){
        //front
        this.hitBox.add(new ShipCollisionPoints(-91,0,scaler));
        this.hitBox.add(new ShipCollisionPoints(-84,42,scaler));
        this.hitBox.add(new ShipCollisionPoints(-84,-42,scaler));
        //Guns
        this.hitBox.add(new ShipCollisionPoints(-54,54,scaler));
        this.hitBox.add(new ShipCollisionPoints(-54,-54,scaler));
        this.hitBox.add(new ShipCollisionPoints(-18,58,scaler));
        this.hitBox.add(new ShipCollisionPoints(-18,-58,scaler));
        //midShips
        this.hitBox.add(new ShipCollisionPoints(-2,48,scaler));
        this.hitBox.add(new ShipCollisionPoints(-2,-48,scaler));
        this.hitBox.add(new ShipCollisionPoints(22,41,scaler));
        this.hitBox.add(new ShipCollisionPoints(22,-41,scaler));
        //tail
        this.hitBox.add(new ShipCollisionPoints(59,20,scaler));
        this.hitBox.add(new ShipCollisionPoints(59,-20,scaler));
        this.hitBox.add(new ShipCollisionPoints(87,15,scaler));
        this.hitBox.add(new ShipCollisionPoints(87,0,scaler));
        this.hitBox.add(new ShipCollisionPoints(87,-15,scaler));
        return new Image(shipName);
    }

    public ArrayList<ShipCollisionPoints> getHitBox() {
        return hitBox;
    }
    public void restart(){
        posX = 0.5;
        posY = 0.5;
        xSpeed = 0;
        ySpeed = 0;
    }
}
