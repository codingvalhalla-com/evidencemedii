package com.codingvalhalla.meredith.evidence.gui.dialogs;

import com.codingvalhalla.meredith.evidence.model.RatingMPAA;
import com.codingvalhalla.meredith.evidence.model.Season;
import com.codingvalhalla.meredith.evidence.utils.StaticAlerts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

/**
 *
 * @author Meredith
 */
public class DialogTVSeason extends Dialog<Season> {

    private final double buttonWidth = 75;
    @SuppressWarnings("FieldMayBeFinal")
    private DialogPane dialogPane;
    private BorderPane rootLayout;
    private GridPane rootGrid;
    private Window window;
    private Stage dialogStage;
    private Scene dialogScene;

    private Season selectedItem;

    private TextField name;

    private Button buttonOK;
    private Button buttonCancel;

    @SuppressWarnings("FieldMayBeFinal")
    private ObservableList<RatingMPAA> ratingList = FXCollections.observableArrayList(RatingMPAA.values());

    public DialogTVSeason(Season selectedItem) {
        this.selectedItem = selectedItem;
        window = getDialogPane().getScene().getWindow();
        window.setOnCloseRequest((WindowEvent event) -> {
            if (StaticAlerts.confirmMessage("close this window without saving")) {
                window.hide();
            } else {
                event.consume();
            }
        });
        dialogPane = getDialogPane();
        init();
        initGrid();
        initProperties();
        addToLayout();
        initListers();
        addValues();
    }

    private void init() {
        this.rootLayout = new BorderPane();
        this.rootGrid = new GridPane();
        this.dialogScene = new Scene(rootLayout, 200, 400);
        this.buttonOK = new Button("OK");
        this.buttonCancel = new Button("Cancel");
        this.name = new TextField();
    }

    private void addToLayout() {
        rootLayout.setTop(initTop());
        rootLayout.setBottom(initBottom());
        rootLayout.setCenter(rootGrid);
        dialogPane.setContent(rootLayout);
    }

    private VBox initTop() {
        VBox vbox = new VBox();

        vbox.setPadding(new Insets(10));
        vbox.setSpacing(8);

        vbox.getChildren().addAll(new Label("Fill following informations:"));
        return vbox;
    }

    private HBox initBottom() {
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setSpacing(10);
        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().addAll(buttonOK, buttonCancel);
        return hbox;
    }

    private void initProperties() {
        buttonOK.setPrefWidth(buttonWidth);
        buttonCancel.setPrefWidth(buttonWidth);
        name.setPromptText("Enter name of season.");
        name.setPrefWidth(200);

        rootGrid.setHgap(10.0);
        rootGrid.setVgap(5.0);
        rootGrid.setPadding(new Insets(5, 15, 5, 15));

        for (int i = 0; i < rootGrid.getChildren().size(); i++) {
            if (i % 2 == 0) {
                GridPane.setHalignment(rootGrid.getChildren().get(i), HPos.RIGHT);
            } else {
                GridPane.setHalignment(rootGrid.getChildren().get(i), HPos.LEFT);
                GridPane.setFillWidth(rootGrid.getChildren().get(i), true);
                GridPane.setHgrow(rootGrid.getChildren().get(i), Priority.ALWAYS);
            }

        }

    }

    private void initGrid() {
        rootGrid.addRow(0, new Label("Name:"), name);
    }

    private boolean isValid() {
        String errorMessage = "";

        if (name.getText() == null || name.getText().length() == 0) {
            errorMessage += "Not valid name.\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            StaticAlerts.errorWithMessege(errorMessage, "Correct the entered data", dialogStage);
            return false;
        }

    }

    @SuppressWarnings("FieldMayBeFinal")
    private EventHandler<ActionEvent> dialogOKEvent = (ActionEvent event) -> {
        if (isValid()) {
            selectedItem.setName(name.getText());
            setResult(selectedItem);
        }

    };

    @SuppressWarnings("FieldMayBeFinal")
    private EventHandler<ActionEvent> dialogCancelEvent = (ActionEvent event) -> {
        window.fireEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSE_REQUEST));
    };

    private void initListers() {
        buttonOK.setOnAction(dialogOKEvent);
        buttonCancel.setOnAction(dialogCancelEvent);
    }

    private void addValues() {
        name.setText(selectedItem.getName());

    }

}
