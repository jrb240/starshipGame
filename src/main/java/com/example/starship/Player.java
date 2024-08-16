package com.example.starship;

import javafx.scene.image.Image;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player {
    private double posX,posY,angle;

    private BufferedImage ship;
    private Image testShip;

    public Player(){
        posX = 0.5;
        posY = 0.5;
        angle = 0;

        ship = null;
        testShip = new Image("ship_cut.png");
//        getShipImage();
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
}
