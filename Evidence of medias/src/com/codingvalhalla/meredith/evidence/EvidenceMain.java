package com.codingvalhalla.meredith.evidence;

import com.codingvalhalla.meredith.evidence.gui.GraphicUserInterface;
import com.codingvalhalla.meredith.evidence.utils.StaticAlerts;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author st52506
 */
public class EvidenceMain extends Application {

    private Stage mainStage;
    GraphicUserInterface gui = null;

    @Override
    public void start(Stage stage) {
        mainStage = stage;

        try {
            gui = new GraphicUserInterface(mainStage);
        } catch (IllegalAccessException e) {
            StaticAlerts.exceptionDialog(e, e.getLocalizedMessage(), mainStage);
        }
        gui.restore();
        gui.setStarted(true);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
