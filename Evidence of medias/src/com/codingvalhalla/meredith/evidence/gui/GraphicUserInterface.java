package com.codingvalhalla.meredith.evidence.gui;

import com.codingvalhalla.meredith.evidence.EvidenceMain;
import com.codingvalhalla.meredith.evidence.model.Movie;
import com.codingvalhalla.meredith.evidence.model.RatingMPAA;
import com.codingvalhalla.meredith.evidence.model.TV_Show;
import com.codingvalhalla.meredith.evidence.utils.StaticAlerts;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Optional;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author Meredith
 */
public class GraphicUserInterface {

    private final String title = "Evidence medii - JavaFX";
    private final static String RESOURCES = EvidenceMain.class.getResource("resources/").toExternalForm();

    private BorderPane rootLayout;
    private Stage mainStage;
    private Scene scene;
    private static boolean alreadyExecuted = false;
    private boolean saved = false;
    private boolean started = false;

    private Button addButton;
    private Button removeButton;
    private Button editButton;
    private Button saveButton;
    private Button restoreButton;

    private ToggleGroup groupToggle;
    private RadioButton buttonMovie;
    private RadioButton buttonTVShow;

    private VBox rightStageVBoxM;
    private VBox rightStageVBoxT;

    private TextArea textAreaM;
    private TextArea textAreaT;
    private TextField seasonsField;
    private TextField episodesField;

    private TableView<Movie> movieList;
    private TableColumn<Movie, String> nameColumnM;
    private TableColumn<Movie, RatingMPAA> ratingColumnM;
    private TableColumn<Movie, Integer> starsColumnM;

    private TableView<TV_Show> tvShowList;
    private TableColumn<TV_Show, String> nameColumnT;
    private TableColumn<TV_Show, RatingMPAA> ratingColumnT;
    private TableColumn<TV_Show, Integer> starsColumnT;
    private TableColumn<TV_Show, Boolean> watchingColumnT;

    @SuppressWarnings("FieldMayBeFinal")
    private ObservableList<Movie> movieData = FXCollections.observableArrayList();
    @SuppressWarnings("FieldMayBeFinal")
    private ObservableList<TV_Show> tvShowData = FXCollections.observableArrayList();

    public GraphicUserInterface(Stage stage) throws IllegalAccessException {
        if (!alreadyExecuted) {
            mainStage = stage;
            mainStage.setTitle(title);
            initRootLayout();
            createListerers();
            alreadyExecuted = true;

        } else {
            throw new IllegalAccessException("Cannot make more than one GUI");
        }
    }

    public static String getResources() {
        return RESOURCES;
    }

    public void save() {
        saveButton.fire();
    }

    public void restore() {
        restoreButton.fire();
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public boolean isSaved() {
        return saved;
    }

    private void initRootLayout() {
        rootLayout = new BorderPane();
        scene = new Scene(rootLayout, 904, 401);
        scene.getStylesheets().add(GraphicUserInterface.class.getResource("valhalla.css").toExternalForm());
        mainStage.setMinWidth(920);
        mainStage.setMinHeight(440);

        rootLayout.setTop(initMenu());
        rootLayout.setLeft(initLeftStage());
        rootLayout.setCenter(initCenterStage());
        rootLayout.setRight(initRightStage());
        rootLayout.setBottom(initBottomStage());
        mainStage.setScene(scene);
        mainStage.show();

    }

    private MenuBar initMenu() {
        MenuBar menuBar = new MenuBar();
        menuBar.prefWidthProperty().bind(mainStage.widthProperty());
        Menu fileMenu = new Menu("_File");
        fileMenu.setMnemonicParsing(true);
        MenuItem closeMenuItem = new MenuItem("Close");
        closeMenuItem.setOnAction((ActionEvent event) -> {
            mainStage.fireEvent(new WindowEvent(mainStage, WindowEvent.WINDOW_CLOSE_REQUEST));
        });
        Menu helpMenu = new Menu("_Help");
        helpMenu.setMnemonicParsing(true);
        MenuItem aboutMenuItem = new MenuItem("About");
        aboutMenuItem.setOnAction((ActionEvent event) -> {
            StaticAlerts.about();
        });
        fileMenu.getItems().addAll(closeMenuItem);
        helpMenu.getItems().addAll(aboutMenuItem);
        menuBar.getMenus().addAll(fileMenu, helpMenu);
        return menuBar;

    }

    private VBox initLeftStage() {
        this.groupToggle = new ToggleGroup();
        this.buttonMovie = new RadioButton("Movies");
        this.buttonTVShow = new RadioButton("TV Shows");
        initRadioButtons();
        VBox result = new VBox();
        result.setMinWidth(100);
        result.setMaxWidth(100);
        result.setAlignment(Pos.BASELINE_LEFT);
        result.setSpacing(5);
        result.setPadding(new Insets(10, 0, 0, 10));
        result.getChildren().addAll(buttonMovie, buttonTVShow);
        return result;
    }

    private AnchorPane initCenterStage() {
        AnchorPane pane = new AnchorPane();
        initMovieList();
        initTVShowList();
        pane.getChildren().addAll(movieList, tvShowList);
        pane.setMinWidth(Double.MIN_VALUE);
        pane.setMinHeight(Double.MIN_VALUE);

        return pane;
    }

    private AnchorPane initRightStage() {
        String style = "-fx-font-weight:bold;" + "-fx-alignment:center;" + "-fx-text-alignment:center";
        AnchorPane pane = new AnchorPane();
        rightStageVBoxM = new VBox();
        Label labelM = new Label("Comment");
        labelM.setPrefSize(200, 25);
        labelM.setStyle(style);
        rightStageVBoxM.setPrefWidth(200);
        textAreaM = new TextArea();
        textAreaM.setEditable(false);
        textAreaM.prefWidth(200);
        textAreaM.prefHeightProperty().bind(movieList.heightProperty().add(-25));
        rightStageVBoxM.getChildren().addAll(labelM, textAreaM);

        rightStageVBoxT = new VBox();
        rightStageVBoxT.setPrefWidth(200);
        textAreaT = new TextArea();
        textAreaT.setEditable(false);
        Label labelT = new Label("More informations");
        labelT.setPrefSize(200, 25);
        labelT.setStyle(style);
        HBox seasons = new HBox();
        HBox episodes = new HBox();
        HBox comments = new HBox();
        seasons.setPrefHeight(25);
        episodes.setPrefHeight(25);
        comments.setAlignment(Pos.CENTER);
        Label seasonsLabel = new Label("Seasons");
        seasonsLabel.setPrefWidth(55);
        seasonsLabel.setStyle(style);
        Label episodesLabel = new Label("Episodes");
        episodesLabel.setPrefWidth(55);
        episodesLabel.setStyle(style);
        Label commnetsLabel = new Label("Comment");
        Group labelHolder = new Group(commnetsLabel);
        commnetsLabel.setPrefHeight(25);
        commnetsLabel.setRotate(-90);
        commnetsLabel.setStyle(style);
        seasonsField = new TextField();
        episodesField = new TextField();
        seasonsField.setEditable(false);
        episodesField.setEditable(false);
        seasons.getChildren().addAll(seasonsLabel, seasonsField);
        episodes.getChildren().addAll(episodesLabel, episodesField);
        comments.getChildren().addAll(labelHolder, textAreaT);
        textAreaT.prefWidth(160);
        textAreaT.prefHeightProperty().bind(movieList.heightProperty().add(-25 - 25 - 25));
        rightStageVBoxT.getChildren().addAll(labelT, seasons, episodes, comments);
        rightStageVBoxT.setVisible(false);

        pane.getChildren().addAll(rightStageVBoxM, rightStageVBoxT);
        return pane;
    }

    private HBox initBottomStage() {
        double buttonWidth = 120;
        HBox result = new HBox();
        result.setPadding(new Insets(15, 12, 15, 12));
        result.setSpacing(10);
        result.setPrefHeight(50);
        addButton = new Button("Add - Movie");
        removeButton = new Button("Remove - Movie");
        editButton = new Button("Edit - Movie");
        saveButton = new Button("Make backup");
        restoreButton = new Button("Restore backup");
        result.getChildren().addAll(addButton, editButton, removeButton, saveButton, restoreButton);
        for (int i = 0; i < result.getChildren().size(); i++) {
            Button temp = new Button();
            temp = (Button) result.getChildren().get(i);
            temp.setPrefWidth(buttonWidth);
        }
        return result;
    }

    private void initMovieList() {
        movieList = new TableView<>();

        nameColumnM = new TableColumn<>("Name");
        ratingColumnM = new TableColumn<>("Rating MPAA");
        starsColumnM = new TableColumn<>("Rating");

        ratingColumnM.setResizable(false);
        starsColumnM.setResizable(false);

        ratingColumnM.setStyle("-fx-alignment: CENTER;");

        starsColumnM.setStyle("-fx-alignment: CENTER;");
        starsColumnM.setCellFactory((TableColumn<Movie, Integer> param) -> {
            final ImageView imageview = new ImageView();

            TableCell<Movie, Integer> cell = new TableCell<Movie, Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    if (item == null || empty) {
                        setGraphic(null);
                    } else {
                        switch (item) {
                            case 0:
                                imageview.setImage(new Image(RESOURCES + "images/stars0.png"));
                                break;
                            case 1:
                                imageview.setImage(new Image(RESOURCES + "images/stars1.png"));
                                break;
                            case 2:
                                imageview.setImage(new Image(RESOURCES + "images/stars2.png"));
                                break;
                            case 3:
                                imageview.setImage(new Image(RESOURCES + "images/stars3.png"));
                                break;
                            case 4:
                                imageview.setImage(new Image(RESOURCES + "images/stars4.png"));
                                break;
                            case 5:
                                imageview.setImage(new Image(RESOURCES + "images/stars5.png"));
                                break;
                            case 6:
                                imageview.setImage(new Image(RESOURCES + "images/stars6.png"));
                                break;
                            case 7:
                                imageview.setImage(new Image(RESOURCES + "images/stars7.png"));
                                break;
                            case 8:
                                imageview.setImage(new Image(RESOURCES + "images/stars8.png"));
                                break;
                            case 9:
                                imageview.setImage(new Image(RESOURCES + "images/stars9.png"));
                                break;
                            case 10:
                                imageview.setImage(new Image(RESOURCES + "images/stars10.png"));
                                break;

                        }
                        setGraphic(imageview);

                    }
                }

            };

            return cell;
        });

        ratingColumnM.setPrefWidth(110);
        starsColumnM.setPrefWidth(120);
        nameColumnM.prefWidthProperty().bind(movieList.widthProperty().add(-starsColumnM.getPrefWidth()).add(-ratingColumnM.getPrefWidth()).add(-15));

        nameColumnM.setCellValueFactory(new PropertyValueFactory<>("name"));
        ratingColumnM.setCellValueFactory(new PropertyValueFactory<>("ratingMPAA"));
        starsColumnM.setCellValueFactory(new PropertyValueFactory<>("stars"));

        movieList.getColumns().addAll(nameColumnM, ratingColumnM, starsColumnM);
        AnchorPane.setTopAnchor(movieList, 0.0);
        AnchorPane.setRightAnchor(movieList, 0.0);
        AnchorPane.setLeftAnchor(movieList, 0.0);
        AnchorPane.setBottomAnchor(movieList, 0.0);
        movieList.setVisible(true);
    }

    private void initTVShowList() {
        tvShowList = new TableView<>();
        nameColumnT = new TableColumn<>("Name");
        ratingColumnT = new TableColumn<>("Rating MPAA");
        starsColumnT = new TableColumn<>("Rating");
        watchingColumnT = new TableColumn<>();

        Label labelWatching = new Label("W");
        Tooltip watchingColumnTooltip = new Tooltip("Watching");
        labelWatching.setTooltip(watchingColumnTooltip);
        watchingColumnT.setGraphic(labelWatching);

        ratingColumnT.setStyle("-fx-alignment: CENTER;");
        starsColumnT.setStyle("-fx-alignment: CENTER;");
        watchingColumnT.setStyle("-fx-alignment: CENTER;");

        starsColumnT.setCellFactory(
                (TableColumn<TV_Show, Integer> param) -> {
                    final ImageView imageview = new ImageView();

                    TableCell<TV_Show, Integer> cell = new TableCell<TV_Show, Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    if (item == null || empty) {
                        setGraphic(null);
                    } else {
                        switch (item) {
                            case 0:
                                imageview.setImage(new Image(RESOURCES + "images/stars0.png"));
                                break;
                            case 1:
                                imageview.setImage(new Image(RESOURCES + "images/stars1.png"));
                                break;
                            case 2:
                                imageview.setImage(new Image(RESOURCES + "images/stars2.png"));
                                break;
                            case 3:
                                imageview.setImage(new Image(RESOURCES + "images/stars3.png"));
                                break;
                            case 4:
                                imageview.setImage(new Image(RESOURCES + "images/stars4.png"));
                                break;
                            case 5:
                                imageview.setImage(new Image(RESOURCES + "images/stars5.png"));
                                break;
                            case 6:
                                imageview.setImage(new Image(RESOURCES + "images/stars6.png"));
                                break;
                            case 7:
                                imageview.setImage(new Image(RESOURCES + "images/stars7.png"));
                                break;
                            case 8:
                                imageview.setImage(new Image(RESOURCES + "images/stars8.png"));
                                break;
                            case 9:
                                imageview.setImage(new Image(RESOURCES + "images/stars9.png"));
                                break;
                            case 10:
                                imageview.setImage(new Image(RESOURCES + "images/stars10.png"));
                                break;

                        }
                        setGraphic(imageview);

                    }
                }

            };

                    return cell;
                }
        );
        watchingColumnT.setCellFactory(
                (TableColumn<TV_Show, Boolean> param) -> {
                    CheckBox checkBox = new CheckBox();
                    TableCell<TV_Show, Boolean> cell = new TableCell<TV_Show, Boolean>() {
                @Override
                protected void updateItem(Boolean item, boolean empty) {
                    if (empty) {
                        setGraphic(null);
                    } else {
                        checkBox.setSelected(item);
                        setGraphic(checkBox);
                    }
                }

            };
                    checkBox.setDisable(true);
                    checkBox.setStyle("-fx-opacity: 1;");

                    return cell;
                }
        );
        ratingColumnT.setPrefWidth(110);
        starsColumnT.setPrefWidth(120);
        watchingColumnT.setPrefWidth(40);
        nameColumnT.prefWidthProperty().bind(movieList.widthProperty().add(-starsColumnT.getPrefWidth()).add(-ratingColumnT.getPrefWidth()).add(-watchingColumnT.getPrefWidth()).add(-15));

        nameColumnT.setCellValueFactory(
                new PropertyValueFactory<>("name"));
        ratingColumnT.setCellValueFactory(
                new PropertyValueFactory<>("ratingMPAA"));
        starsColumnT.setCellValueFactory(
                new PropertyValueFactory<>("stars"));
        watchingColumnT.setCellValueFactory(
                new PropertyValueFactory<>("watching"));

        tvShowList.getColumns().addAll(watchingColumnT, nameColumnT, ratingColumnT, starsColumnT);
        AnchorPane.setTopAnchor(tvShowList, 0.0);
        AnchorPane.setRightAnchor(tvShowList, 0.0);
        AnchorPane.setLeftAnchor(tvShowList, 0.0);
        AnchorPane.setBottomAnchor(tvShowList, 0.0);
        tvShowList.setVisible(false);
    }

    private void createListerers() {
        removeButton.setDisable(true);
        editButton.setDisable(true);

        mainStage.setOnCloseRequest((WindowEvent event) -> {
            if (!saved) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("Quit");
                alert.setHeaderText(null);
                alert.setContentText("Do you want to save changes?");
                Stage hiddenStage = (Stage) alert.getDialogPane().getScene().getWindow();
                hiddenStage.getIcons().add(new Image(RESOURCES + "images/tux.png"));
                ButtonType buttonTypeYes = new ButtonType("Save", ButtonData.YES);
                ButtonType buttonTypeNo = new ButtonType("Don't save", ButtonData.NO);
                ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
                alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo, buttonTypeCancel);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == buttonTypeYes) {
                    save();
                    System.exit(0);
                } else if (result.get() == buttonTypeNo) {
                    System.exit(0);
                } else if (result.get() == buttonTypeCancel) {
                    event.consume();
                }
            }
        });
        tvShowList.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends TV_Show> observable, TV_Show oldValue, TV_Show newValue) -> {
            if (newValue != null) {
                removeButton.setDisable(false);
                editButton.setDisable(false);
                textAreaT.setText(newValue.getComments());
                seasonsField.setText(Integer.toString(newValue.getSeasonsNumber()));

            } else {
                removeButton.setDisable(true);
                editButton.setDisable(true);
                textAreaM.setText("");
                seasonsField.setText("");
                episodesField.setText("");
            }

        });
        tvShowList.setOnMouseClicked((event) -> {
            if (event.getClickCount() == 2) {
                editButton.fire();
            }
        });
        movieList.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Movie> observable, Movie oldValue, Movie newValue) -> {
            if (newValue != null) {
                removeButton.setDisable(false);
                editButton.setDisable(false);
                textAreaM.setText(newValue.getComments());
            } else {
                removeButton.setDisable(true);
                editButton.setDisable(true);
                textAreaM.setText("");
            }

        });
        movieList.setOnMouseClicked((event) -> {
            if (event.getClickCount() == 2) {
                editButton.fire();
            }
        });

        addButton.setOnAction(handlerAddButton);
        editButton.setOnAction(handlerEditButton);
        removeButton.setOnAction(handlerRemoveButton);
        saveButton.setOnAction(handlerSaveButton);
        restoreButton.setOnAction(handlerRestoreButton);

    }

    private void initRadioButtons() {
        buttonMovie.setToggleGroup(groupToggle);
        buttonTVShow.setToggleGroup(groupToggle);
        buttonTVShow.setDisable(false);
        buttonMovie.setSelected(true);

        buttonTVShow.selectedProperty().addListener((observable, oldValue, newValue) -> {
            addButton.setText("Add - Tv Show");
            removeButton.setText("Remove - Tv Show");
            editButton.setText("Edit - Tv Show");

            tvShowList.setVisible(true);
            movieList.setVisible(false);
            rightStageVBoxM.setVisible(false);
            rightStageVBoxT.setVisible(true);

        });
        buttonMovie.selectedProperty().addListener((observable, oldValue, newValue) -> {
            addButton.setText("Add - Movie");
            removeButton.setText("Remove - Movie");
            editButton.setText("Edit - Movie");

            rightStageVBoxM.setVisible(true);
            rightStageVBoxT.setVisible(false);
            tvShowList.setVisible(false);
            movieList.setVisible(true);
        });

    }

    @SuppressWarnings("FieldMayBeFinal")
    private EventHandler<ActionEvent> handlerAddButton = (ActionEvent event) -> {
        if (buttonMovie.isSelected()) {
            AddDialogMovie add = new AddDialogMovie();
            add.setTitle("Add movie");
            DialogPane dialogPane = add.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("valhalla.css").toExternalForm());
            dialogPane.getStyleClass().add("add-dialog");

            add.show();
            add.afterShow();
            add.resultProperty().addListener((observable, oldValue, newValue) -> {
                try {
                    movieList.getItems().add(newValue);
                    saved = false;
                    movieList.refresh();
                } catch (Exception e) {
                    StaticAlerts.exceptionDialog(e, e.getLocalizedMessage(), mainStage);
                }
            });
        }
        if (buttonTVShow.isSelected()) {
            AddDialogTVShow add = new AddDialogTVShow();
            add.setTitle("Add TV show");
            DialogPane dialogPane = add.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("valhalla.css").toExternalForm());
            dialogPane.getStyleClass().add("add-dialog");

            add.show();
            add.afterShow();

            add.resultProperty().addListener((observable, oldValue, newValue) -> {
                try {
                    tvShowList.getItems().add(newValue);
                    saved = false;
                    tvShowList.refresh();
                } catch (Exception e) {
                    StaticAlerts.exceptionDialog(e, e.getLocalizedMessage(), mainStage);
                }
            });

        }

    };
    @SuppressWarnings("FieldMayBeFinal")
    private EventHandler<ActionEvent> handlerEditButton = (ActionEvent event) -> {
        if (buttonMovie.isSelected()) {
            Movie x = movieList.getSelectionModel().getSelectedItem();
            EditDialogMovie edit = new EditDialogMovie(x);
            edit.setTitle("Edit movie");
            DialogPane dialogPane = edit.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("valhalla.css").toExternalForm());
            dialogPane.getStyleClass().add("edit-dialog");

            edit.show();
            edit.afterShow();
            edit.resultProperty().addListener((observable, oldValue, newValue) -> {
                saved = false;
                movieList.refresh();

            });

        }
        if (buttonTVShow.isSelected()) {
            TV_Show x = tvShowList.getSelectionModel().getSelectedItem();
            EditDialogTVShow edit = new EditDialogTVShow(x);
            edit.setTitle("Edit TV show");
            DialogPane dialogPane = edit.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("valhalla.css").toExternalForm());
            dialogPane.getStyleClass().add("edit-dialog");

            edit.show();
            edit.afterShow();
            edit.resultProperty().addListener((observable, oldValue, newValue) -> {
                saved = false;
                tvShowList.refresh();

            });
        }

    };
    @SuppressWarnings("FieldMayBeFinal")
    private EventHandler<ActionEvent> handlerRemoveButton = (ActionEvent event) -> {
        if (buttonMovie.isSelected()) {
            Movie x = movieList.getSelectionModel().getSelectedItem();
            movieList.getItems().remove(x);
            movieList.refresh();
        }
        if (buttonTVShow.isSelected()) {
            TV_Show x = tvShowList.getSelectionModel().getSelectedItem();
            tvShowList.getItems().remove(x);
            tvShowList.refresh();
        }
        saved = false;
    };
    @SuppressWarnings("FieldMayBeFinal")
    private EventHandler<ActionEvent> handlerSaveButton = (ActionEvent event) -> {
        FileOutputStream fileOut = null;
        ObjectOutputStream out = null;

        try {
            fileOut = new FileOutputStream("backup.bin");
            out = new ObjectOutputStream(fileOut);
            Movie[] arr = new Movie[movieList.getItems().size()];
            arr = movieList.getItems().toArray(arr);
            out.writeInt(arr.length);
            for (Movie arr1 : arr) {
                out.writeObject(arr1);
            }

            TV_Show[] array2 = new TV_Show[tvShowList.getItems().size()];
            array2 = tvShowList.getItems().toArray(array2);
            out.writeInt(array2.length);
            for (TV_Show array21 : array2) {
                out.writeObject(array21);
            }

            saved = true;
            out.writeBoolean(saved);

        } catch (IOException ex) {
            StaticAlerts.exceptionDialog(ex, ex.getLocalizedMessage(), mainStage);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (fileOut != null) {
                    fileOut.close();
                }

            } catch (IOException ex) {
                StaticAlerts.exceptionDialog(ex, ex.getLocalizedMessage(), mainStage);
            }
        }

    };
    @SuppressWarnings("FieldMayBeFinal")
    private EventHandler<ActionEvent> handlerRestoreButton = (ActionEvent event) -> {
        FileInputStream fileIn = null;
        ObjectInputStream in = null;
        try {
            fileIn = new FileInputStream("backup.bin");
            in = new ObjectInputStream(fileIn);
            int iMax = in.readInt();
            movieList.getItems().clear();
            for (int i = 0; i < iMax; i++) {
                Movie temp = (Movie) in.readObject();
                movieList.getItems().add(temp);
            }
            movieList.refresh();
            iMax = in.readInt();
            tvShowList.getItems().clear();
            for (int i = 0; i < iMax; i++) {
                TV_Show temp = (TV_Show) in.readObject();
                tvShowList.getItems().add(temp);
            }
            tvShowList.refresh();
            saved = in.readBoolean();

        } catch (IOException | ClassNotFoundException ex) {
            if (started) {
                StaticAlerts.exceptionDialog(ex, ex.getLocalizedMessage(), mainStage);
            }
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (fileIn != null) {
                    fileIn.close();
                }

            } catch (IOException ex) {
                StaticAlerts.exceptionDialog(ex, ex.getLocalizedMessage(), mainStage);
            }
        }

    };

}
