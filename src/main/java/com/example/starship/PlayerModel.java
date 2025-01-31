package com.example.starship;

import javafx.scene.image.Image;

import java.util.ArrayList;

public class PlayerModel {
    ArrayList<Star> stars;
    ArrayList<Subscriber> subscribers;
    Player player;
    private double canvasWidth,canvasHeight;
    enum PlayerStatus{ALIVE,DEAD};
    PlayerStatus playerState;

    PlayerModel(double width,double height){
        stars = new ArrayList<>();
        subscribers = new ArrayList<>();
        generateStars(100);

        player = new Player();
         playerState = PlayerStatus.ALIVE;
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

        //cases where the inverse tan is going to mess up
        if (scaledY-pY == 0) {
            if (scaledX-pX > 0){
                player.setAngle(180);
            } else {
                player.setAngle(0);
            }
        } else if (scaledX-pX == 0) {
            if (scaledY-pY > 0){
                player.setAngle(270);
            }
            else {
                player.setAngle(90);
            }
        }
        else if (scaledY-pY < 0 && scaledX-pX > 0 || scaledY-pY > 0 && scaledX-pX > 0){
            player.setAngle(Math.toDegrees(Math.atan((scaledY-pY)/(scaledX-pX)))+180);
        } else {
            player.setAngle(Math.toDegrees(Math.atan((scaledY-pY)/(scaledX-pX))));
        }
        subscribers.forEach(Subscriber::modelChanged);
    }

    public void update(ArrayList<DemoAsteroid> asteroids) {
        player.update();
        for (DemoAsteroid asteroid : asteroids) {
            for (ShipCollisionPoints hitBox : player.getHitBox()) {
                  if (pythagoras(asteroid.getPositionX()*canvasWidth,asteroid.getPositionY()*canvasHeight,
                          hitBox.getXPos()+playerXPos()*canvasWidth,hitBox.getYPos()+playerYPos()*canvasHeight
                  )<=asteroid.getRadius()){
                      playerState = PlayerStatus.DEAD;
                  }
            }
        }
    }
    public void increaseSpeed(double mouseX, double mouseY, double playerX, double playerY) {
        double hypotenuse = Math.sqrt((mouseX-playerX)*(mouseX-playerX)
                +(mouseY-playerY)*(mouseY-playerY));
        double xRatio = (mouseX-playerX)/hypotenuse;
        double yRatio = (mouseY-playerY)/hypotenuse;
        player.increaseSpeed(xRatio,yRatio);
    }
    public double getShipScaler(){
        return player.getScaler();
    }
    public double pythagoras(double bX, double bY, double aX, double aY){
        return Math.sqrt((bX-aX)*(bX-aX)+(bY-aY)*(bY-aY));
    }

    public void restart() {
        if (playerState==PlayerStatus.DEAD){
            playerState = PlayerStatus.ALIVE;
            player.restart();
        }
    }

    public boolean isPlayerAlive() {
        if (playerState==PlayerStatus.ALIVE){
            return true;
        } else {
            return false;
        }
    }

    public double getCanvasHeight() {
        return canvasHeight;
    }

    public double getCanvasWidth() {
        return canvasWidth;
    }

}
