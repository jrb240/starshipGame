package com.example.starship;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class Controller {

    InteractionModel iModel;
    PlayerModel model;
    double mouseX,mouseY;
    public void handleTimerTick() {
        model.update(iModel.getAsteroids());
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
                case S -> {
                    if (!(iModel.getLevel()==0)){
                        return;
                    }
                    iModel.start();}
                case R -> {
                    iModel.restart();
                    model.restart();}
            }
        } else {
            switch (keyEvent.getCode()){
                case W ->{
                    if (!model.isPlayerAlive()){
                        return;
                    }
                    model.increaseSpeed(mouseX,mouseY,
                            model.playerXPos()*model.getCanvasWidth()
                            ,model.playerYPos()*model.getCanvasHeight());
                }
                case SPACE -> {
                    if (!model.isPlayerAlive()){
                        return;
                    }
                    iModel.shoot(mouseX,mouseY,
                            model.playerXPos()*model.getCanvasWidth()
                            ,model.playerYPos()*model.getCanvasHeight());}
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
