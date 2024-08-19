package com.example.starship;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class MainUI extends StackPane {
    VBox sideBar;
    HBox mainScreen,itemBox,workingCanvas;

    Controller controller;
    SpaceView view;
    MainUI(){
        double width = 1200;
        double height = 800;
        double mmWidth = 200;
        double mmHeight = 200;
        this.setStyle("-fx-base: #191919; -fx-background-color: #191919");

        sideBar = new VBox();
        mainScreen = new HBox();
        itemBox = new HBox();
        workingCanvas = new HBox();

        PlayerModel model = new PlayerModel(width,height);
        InteractionModel iModel = new InteractionModel(width,height);
        controller = new Controller();
        view = new SpaceView(width,height);
        SpaceView miniMap = new SpaceView(mmWidth,mmHeight);

        view.setControlller(controller);
        view.setModel(model);
        view.setIModel(iModel);
        model.setCanvasDimensions(width,height);
        controller.setModel(model);
        controller.setiModel(iModel);
        model.addSubscriber(view);
        iModel.addSubscriber(view);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                controller.handleTimerTick();
            }
        };
        timer.start();

        workingCanvas.getChildren().add(view);
        this.getChildren().addAll(workingCanvas);
    }
    public void setFocus(){
        view.setFocus();
    }
}
