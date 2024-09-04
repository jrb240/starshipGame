package com.example.starship;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class Controller {

    InteractionModel iModel;
    PlayerModel model;
    double mouseX,mouseY;
    public void handleTimerTick() {
        model.update();
        iModel.update();
    }
    Controller(){};

    public void setiModel(InteractionModel iModel) {
        this.iModel = iModel;
    }

    public void setModel(PlayerModel model) {
        this.model = model;
    }

    public void handleKeyPressed(KeyEvent keyEvent) {
        if(keyEvent.isControlDown()){
            switch (keyEvent.getCode()){
                case S -> {iModel.start();}
                case R -> {iModel.restart();}
            }
        } else {
            switch (keyEvent.getCode()){
                case W ->{
                    model.increaseSpeed(mouseX,mouseY,
                            model.playerXPos()*model.canvasWidth
                            ,model.playerYPos()*model.canvasHeight);
                }
//                case A -> {model.moveRight();}
//                case W -> {model.moveUp();}
//                case D -> {model.moveLeft();}
//                case S -> {model.moveDown();}
                case SPACE -> {iModel.shoot(mouseX,mouseY,
                        model.playerXPos()*model.canvasWidth
                        ,model.playerYPos()*model.canvasHeight);}
            }
        }

    }

    public void handleMouseMoved(MouseEvent mouseEvent) {
        double mY = mouseEvent.getY();
        double mX = mouseEvent.getX();
        mouseX = mX;
        mouseY = mY;

        model.changePlayerAngle(mX,mY);
    }

    public void handleMouseReleased(MouseEvent mouseEvent) {
//        case SPACE -> {iModel.shoot(mouseX,mouseY,
//                model.playerXPos()*model.canvasWidth
//                ,model.playerYPos()*model.canvasHeight);}
    }
}
