package com.codingvalhalla.meredith.evidence;

import com.codingvalhalla.meredith.evidence.gui.GraphicUserInterface;
import com.codingvalhalla.meredith.evidence.utils.StaticAlerts;
import com.sun.javafx.application.LauncherImpl;
import javafx.application.Application;
import javafx.application.Preloader;
import javafx.stage.Stage;

public class EvidenceMain extends Application {

    private final int COUNT_LIMIT = 100000;
    private Stage mainStage;
    GraphicUserInterface gui = null;

    public static void main(String[] args) {
        LauncherImpl.launchApplication(EvidenceMain.class, XPreloader.class, args);
    }

    @Override
    public void init() {
        for (int i = 0; i < COUNT_LIMIT; i++) {
            double progress = (100 * i) / COUNT_LIMIT;
            LauncherImpl.notifyPreloader(this, new Preloader.ProgressNotification(progress));
        }
    }

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
        gui.afterStarted();
    }

}
