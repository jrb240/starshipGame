package com.example.starship;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class SpaceView extends StackPane implements Subscriber {

    Canvas myCanvas,starCanvas;
    GraphicsContext gc;
    GraphicsContext printer, playerPrinter;
    PlayerModel model;
    InteractionModel iModel;
    Controller controller;
    public SpaceView(double CanvasWidth,double CanvasHeight){
        starCanvas = new Canvas(CanvasWidth,CanvasHeight);
        myCanvas = new Canvas(CanvasWidth,CanvasHeight);
        printer = starCanvas.getGraphicsContext2D();
        playerPrinter= myCanvas.getGraphicsContext2D();


        this.setMaxSize(CanvasWidth,CanvasHeight);
        this.getChildren().addAll(starCanvas,myCanvas);
    }
    public void setControlller(Controller controller) {
        myCanvas.setOnKeyPressed(controller::handleKeyPressed);
        myCanvas.setOnMouseMoved(controller::handleMouseMoved);
        myCanvas.setOnMouseReleased(controller::handleMouseReleased);
    }

    public void setModel(PlayerModel model) {
        this.model = model;
        model.getStars().forEach(star -> {
            int x = (int) (star.locationX*myCanvas.getWidth());
            int y = (int) (star.locationY* myCanvas.getHeight());
            printer.getPixelWriter().setColor(
                    x,
                    y,
                    Color.WHITE);
        });
        playerPrinter.save();
        playerPrinter.translate(model.getPlayer().getPosX()*myCanvas.getWidth(),
                model.getPlayer().getPosY()*myCanvas.getHeight());
        playerPrinter.rotate(model.getPlayer().getAngle());
        playerPrinter.drawImage(model.getShip(),-91,-60.5);
        playerPrinter.restore();

//        playerPrinter.drawImage(model.getShip(),
//                model.playerXPos()*myCanvas.getWidth()-91,
//                model.playerYPos()*myCanvas.getHeight()-60.5);
    }

    public void setIModel(InteractionModel iModel) {
        this.iModel = iModel;
    }

    @Override
    public void modelChanged() {
        draw();
    }

    private void draw() {
        playerPrinter.clearRect(0,0,myCanvas.getWidth(), myCanvas.getHeight());
//        model.getPlayer().getAngle();
        playerPrinter.setFill(Color.color(.2,.2,.2));
        playerPrinter.setLineWidth(1);
        playerPrinter.setStroke(Color.GREY);
        iModel.getAsteroids().forEach(asteroid->{
            playerPrinter.fillOval(myCanvas.getWidth()* asteroid.getPositionX()-asteroid.getRadius(),
                    myCanvas.getHeight()*asteroid.getPositionY()-asteroid.getRadius(),
                    asteroid.getRadius()*2,
                    asteroid.getRadius()*2);
            playerPrinter.strokeOval(myCanvas.getWidth()* asteroid.getPositionX()-asteroid.getRadius(),
                    myCanvas.getHeight()*asteroid.getPositionY()-asteroid.getRadius(),
                    asteroid.getRadius()*2,
                    asteroid.getRadius()*2);
        });
        playerPrinter.setFill(Color.color(1,1,0));
        playerPrinter.setLineWidth(0.5);
        playerPrinter.setStroke(Color.ORANGE);
        iModel.getBullets().forEach(bullet->{
            playerPrinter.fillOval(bullet.positionX-2,bullet.positionY-2,4,4);
            playerPrinter.strokeOval(bullet.positionX-2,bullet.positionY-2,4,4);
        });
        playerPrinter.save();
        playerPrinter.translate(model.getPlayer().getPosX()*myCanvas.getWidth(),
                model.getPlayer().getPosY()*myCanvas.getHeight());
        playerPrinter.scale(.5,.5);  //shrinking
        playerPrinter.rotate(model.getPlayer().getAngle());
        playerPrinter.drawImage(model.getShip(),-91,-60.5);

        playerPrinter.restore();
    }

    public void setFocus() {
        myCanvas.requestFocus();
    }
}
