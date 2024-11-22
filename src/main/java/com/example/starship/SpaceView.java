package com.example.starship;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class SpaceView extends StackPane implements Subscriber {

    Canvas myCanvas,starCanvas,collider;
    WritableImage buffer;
    PixelReader reader;
    GraphicsContext gc;
    GraphicsContext printer, playerPrinter;
    PlayerModel model;
    InteractionModel iModel;
    Controller controller;
    double shipScaler = 0.25;
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
    }

    public void setIModel(InteractionModel iModel) {
        this.iModel = iModel;
    }

    @Override
    public void modelChanged() {
        draw();
    }

    private void draw() {
        //clean view
        playerPrinter.clearRect(0,0,myCanvas.getWidth(), myCanvas.getHeight());

        //draw asteroids
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

        //draw bullets
        playerPrinter.setFill(Color.color(1,1,0));
        playerPrinter.setLineWidth(0.5);
        playerPrinter.setStroke(Color.ORANGE);
        iModel.getBullets().forEach(bullet->{
            playerPrinter.fillOval(bullet.positionX-2,bullet.positionY-2,4,4);
            playerPrinter.strokeOval(bullet.positionX-2,bullet.positionY-2,4,4);
        });

        //draw ship
        if (model.isPlayerAlive()) {
            playerPrinter.save();
            playerPrinter.translate(model.getPlayer().getPosX() * myCanvas.getWidth(),
                    model.getPlayer().getPosY() * myCanvas.getHeight());
            playerPrinter.scale(model.getShipScaler(), model.getShipScaler());  //shrinking
            playerPrinter.rotate(model.getPlayer().getAngle());
            playerPrinter.drawImage(model.getShip(), -91, -60.5);
            playerPrinter.restore();
        }

        //draw collision points
        playerPrinter.setStroke(Color.AQUA);
        playerPrinter.setFill(Color.RED);
        model.getPlayer().getHitBox().forEach(hitBox->{
            playerPrinter.fillOval(hitBox.XPos+model.playerXPos()*myCanvas.getWidth()-2,
                    hitBox.YPos+ model.playerYPos()*myCanvas.getHeight()-2,4,4);
            playerPrinter.strokeOval(hitBox.XPos+model.playerXPos()*myCanvas.getWidth()-2,
                    hitBox.YPos+ model.playerYPos()*myCanvas.getHeight()-2,4,4);
        });

    }

    public void setFocus() {
        myCanvas.requestFocus();
    }
}
