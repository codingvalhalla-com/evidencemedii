package com.codingvalhalla.meredith.evidence;

import javafx.application.Preloader;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Meredith
 */
public class XPreloader extends Preloader {

    private Stage preloaderStage;
    private Scene scene;

    private double xOffset = 0;
    private double yOffset = 0;

    public XPreloader() {

    }

    @Override
    public void init() {
        Splash root = new Splash();
        root.getPane().setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        root.getPane().setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                preloaderStage.setX(event.getScreenX() - xOffset);
                preloaderStage.setY(event.getScreenY() - yOffset);
            }
        });
        scene = new Scene(root.getPane());

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.preloaderStage = primaryStage;
        preloaderStage.setScene(scene);
        preloaderStage.initStyle(StageStyle.UNDECORATED);
        preloaderStage.show();

    }

    @Override
    public void handleApplicationNotification(PreloaderNotification info) {
        if (info instanceof ProgressNotification) {
            Splash.label.setText("Loading: " + String.format("%d", (long) ((ProgressNotification) info).getProgress()) + "%");
        }

    }

    @Override
    public void handleStateChangeNotification(StateChangeNotification info) {
        StateChangeNotification.Type type = info.getType();
        switch (type) {
            case BEFORE_START:
                preloaderStage.hide();
                break;
        }
    }
}
