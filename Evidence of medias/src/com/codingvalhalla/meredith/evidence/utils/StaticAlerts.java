package com.codingvalhalla.meredith.evidence.utils;

import com.codingvalhalla.meredith.evidence.gui.GraphicUserInterface;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

/**
 *
 * @author Meredith
 */
public class StaticAlerts {

    private static String resources = GraphicUserInterface.getResources();

    private StaticAlerts() {
    }

    public static void exceptionDialog(Exception e, String message, Stage stage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(stage);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.setTitle("Exception Dialog");
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String exceptionText = sw.toString();
        Label label = new Label("The exception stacktrace was:");
        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);
        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);
        alert.getDialogPane().setExpandableContent(expContent);
        Stage hiddenStage = (Stage) alert.getDialogPane().getScene().getWindow();
        hiddenStage.getIcons().add(new Image(resources + "images/alert.png"));
        alert.showAndWait();
    }

    public static void errorWithMessege(String errorMessage, String message, Stage stage) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.initOwner(stage);
        alert.setTitle("Error");
        alert.setHeaderText(message);
        alert.setContentText(errorMessage);
        Stage hiddenStage = (Stage) alert.getDialogPane().getScene().getWindow();
        hiddenStage.getIcons().add(new Image(resources + "images/alert.png"));
        alert.showAndWait();
    }

    public static void error(String message, Stage stage) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.initOwner(stage);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        Stage hiddenStage = (Stage) alert.getDialogPane().getScene().getWindow();
        hiddenStage.getIcons().add(new Image(resources + "images/alert.png"));
        alert.showAndWait();
    }

    public static boolean confirmMessage(String operation) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setHeaderText("Confirm");
        alert.setHeaderText(null);
        alert.setContentText("Do you really want " + operation + "?");
        Stage hiddenStage = (Stage) alert.getDialogPane().getScene().getWindow();
        hiddenStage.getIcons().add(new Image(resources + "images/tux.png"));
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            return true;
        }
        return false;

    }
    

    public static void infoMessage(String title, String infoMessage) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(infoMessage);
        Stage hiddenStage = (Stage) alert.getDialogPane().getScene().getWindow();
        hiddenStage.getIcons().add(new Image(resources + "images/tux.png"));

        alert.showAndWait();

    }

    public static void about() {
        Alert alert = new Alert(AlertType.INFORMATION);
        String icon = "images/coding_valhalla_small.png";
        alert.setGraphic(new ImageView(resources + icon));
        alert.setTitle("About");
        alert.setHeaderText(null);
        alert.setContentText(About.getAbout());
        Stage hiddenStage = (Stage) alert.getDialogPane().getScene().getWindow();
        hiddenStage.getIcons().add(new Image(resources + icon));

        alert.showAndWait();

    }
}
