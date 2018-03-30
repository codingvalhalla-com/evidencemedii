package com.codingvalhalla.meredith.evidence;

import com.codingvalhalla.meredith.evidence.gui.GraphicUserInterface;
import javafx.application.Preloader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Meredith
 */
public class XPreloader extends Preloader {

    private static final String RESOURCES = GraphicUserInterface.getResources();
    private final String SPLASH = "images/splash.png";

    private ImageView imageView;
    private Pane pane;
    private Label progress;
    private Stage preloaderStage;
    private Scene scene;

    private double xOffset = 0;
    private double yOffset = 0;

    public XPreloader() {

    }

    @Override
    public void init() {
        initSplash();
        scene = new Scene(pane);

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
            progress.setText("Loading: " + String.format("%d", (long) ((ProgressNotification) info).getProgress()) + "%");
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

    private void initSplash() {
        pane = new Pane();
        progress = new Label();
        imageView = new ImageView(new Image(RESOURCES + SPLASH, 854, 480, true, true));
        pane.setOnMousePressed((MouseEvent event) -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        pane.setOnMouseDragged((MouseEvent event) -> {
            preloaderStage.setX(event.getScreenX() - xOffset);
            preloaderStage.setY(event.getScreenY() - yOffset);
        });
        progress.setTranslateX(50);
        progress.setTranslateY(imageView.getImage().getHeight() - 50);
        progress.setTextFill(Color.SILVER);
        pane.getChildren().addAll(imageView, progress);
    }

}
