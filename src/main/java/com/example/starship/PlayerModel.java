package com.example.starship;

import javafx.scene.image.Image;

import java.util.ArrayList;

public class PlayerModel {
    ArrayList<Star> stars;
    ArrayList<Subscriber> subscribers;
    Player player;
    double canvasWidth,canvasHeight,playerMoveSpeed;

    PlayerModel(double width,double height){
        stars = new ArrayList<>();
        subscribers = new ArrayList<>();
        playerMoveSpeed = 20;
        generateStars(100);

        player = new Player();
        setCanvasDimensions(width,height);
    }

    private void generateStars(int num) {
        for (int i=0;i<num;i++){
            stars.add(new Star());
        }
    }

    public ArrayList<Star> getStars() {
        return stars;
    }

    public Player getPlayer() {
        return player;
    }
    public double playerXPos(){
        return player.getPosX();
    }
    public double playerYPos(){
        return player.getPosY();
    }
//    public void moveShip(double dx, double dy){
//        player.setPosX(player.getPosX()+dx);
//        player.setPosY(player.getPosY()+dy);
//        subscribers.forEach(Subscriber::modelChanged);
//    }
    public void moveRight(){
        player.setPosX(xMove(-playerMoveSpeed));
        subscribers.forEach(Subscriber::modelChanged);
    }
    public void moveLeft(){
        player.setPosX(xMove(playerMoveSpeed));
        subscribers.forEach(Subscriber::modelChanged);
    }
    public void moveUp(){
        player.setPosY(yMove(-playerMoveSpeed));
        subscribers.forEach(Subscriber::modelChanged);
    }
    public void moveDown(){
        player.setPosY(yMove(playerMoveSpeed));
        subscribers.forEach(Subscriber::modelChanged);
    }
    public double[] placingShip(double dx,double dy){
        player.setPosX(player.getPosX()+dx);
        player.setPosY(player.getPosY()+dy);
        return new double[]{player.getPosX(), player.getPosY()};
    }
    public void setCanvasDimensions(double width, double height){
        canvasWidth = width;
        canvasHeight = height;
        player.setPosX(player.getPosX());
        player.setPosY(player.getPosY());
    }

    public void addSubscriber(SpaceView view) {
        subscribers.add(view);
    }
    public Image getShip(){
        return player.getShip();
    }
    public double xMove(double move){
        return (playerXPos()*canvasWidth+move)/canvasWidth;
    }
    public double yMove(double move){
        return (playerYPos()*canvasHeight+move)/canvasHeight;
    }

    public void changePlayerAngle(double mX, double mY) {
        double scaledX = mX/canvasWidth;
        double scaledY = mY/canvasHeight;
        double pX = player.getPosX();
        double pY = player.getPosY();


        if (scaledY-pY < 0 && scaledX-pX > 0 || scaledY-pY > 0 && scaledX-pX > 0){
            player.setAngle(Math.toDegrees(Math.atan((scaledY-pY)/(scaledX-pX)))+181);
        } else {
            player.setAngle(Math.toDegrees(Math.atan((scaledY-pY)/(scaledX-pX))));
        }



        subscribers.forEach(Subscriber::modelChanged);
    }

    public void update() {
    }
}
