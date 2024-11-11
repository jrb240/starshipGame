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
    private double scaler;
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
        //182x121
//        testShip = new Image("ship_cut.png");
//        getShipImage();
        testShip = shipSelection("ship_cut.png");
        scaler = 1.5;
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
            box.update(newAngle);
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
        this.hitBox.add(new ShipCollisionPoints(91,0));
        this.hitBox.add(new ShipCollisionPoints(84,42));
        this.hitBox.add(new ShipCollisionPoints(84,-42));
        //Guns
        this.hitBox.add(new ShipCollisionPoints(54,54));
        this.hitBox.add(new ShipCollisionPoints(54,-54));
        this.hitBox.add(new ShipCollisionPoints(18,58));
        this.hitBox.add(new ShipCollisionPoints(18,-58));
        //midShips
        this.hitBox.add(new ShipCollisionPoints(2,48));
        this.hitBox.add(new ShipCollisionPoints(2,-48));
        this.hitBox.add(new ShipCollisionPoints(-22,41));
        this.hitBox.add(new ShipCollisionPoints(-22,-41));
        //tail
        this.hitBox.add(new ShipCollisionPoints(-67,20));
        this.hitBox.add(new ShipCollisionPoints(-67,-20));
        this.hitBox.add(new ShipCollisionPoints(-87,15));
        this.hitBox.add(new ShipCollisionPoints(-87,0));
        this.hitBox.add(new ShipCollisionPoints(-87,-15));
        return new Image("ship_cut.png");
    }

    public ArrayList<ShipCollisionPoints> getHitBox() {
        return hitBox;
    }
}
