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
        playerPrinter.save();
        playerPrinter.translate(model.getPlayer().getPosX()*myCanvas.getWidth(),
                model.getPlayer().getPosY()*myCanvas.getHeight());
        playerPrinter.rotate(model.getPlayer().getAngle());
        playerPrinter.drawImage(model.getShip(),-91,-60.5);
        playerPrinter.restore();
//        playerPrinter.drawImage(model.getShip(),
//                model.playerXPos()*myCanvas.getWidth()-91,
//                model.playerYPos()*myCanvas.getHeight()-60.5);
        iModel.getAsteroids();
    }

    public void setFocus() {
        myCanvas.requestFocus();
    }
}
