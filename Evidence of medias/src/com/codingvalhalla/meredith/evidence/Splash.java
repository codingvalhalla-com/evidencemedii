package com.codingvalhalla.meredith.evidence;

import com.codingvalhalla.meredith.evidence.gui.GraphicUserInterface;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 *
 * @author Meredith
 */
public class Splash {

    private static String resources = GraphicUserInterface.getResources();
    private ImageView imageView;
    private Pane pane;
    private Label progress;
    public static Label label;

    public Splash() {
        progress = new Label();
        label = progress;
        imageView = new ImageView(new Image(resources + "images/splash.png", 854, 480, true, true));
        pane = initPane();
    }

    public Pane getPane() {
        return pane;
    }

    private Pane initPane() {
        Pane temp = new Pane();
        progress.setTranslateX(50);
        progress.setTranslateY(imageView.getImage().getHeight() - 50);
        progress.setTextFill(Color.SILVER);
        temp.getChildren().addAll(imageView, progress);
        return temp;
    }
}
