package com.example.starship;

import javafx.scene.image.Image;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player {
    private double posX,posY,angle;
    private double xSpeed;
    private double ySpeed;
    private double increment = 0.001;
    private double timer;
    private BufferedImage ship;
    private Image testShip;
    private double scaler;

    public Player(){
        posX = 0.5;
        posY = 0.5;
        angle = 0;

        xSpeed = 0;
        ySpeed = 0;
        timer = 0;

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
    public Image shipSelection(String shipName){
        return new Image("ship_cut.png");
    }
    private void collisionPoints(){
        double front,frontTop,frontBot,frontGunTop,frontGunBot,backGunTop, backGunBot;
    }
}
