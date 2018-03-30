package com.codingvalhalla.meredith.evidence.gui;

import com.codingvalhalla.meredith.evidence.gui.dialogs.DialogTVSeason;
import com.codingvalhalla.meredith.evidence.gui.dialogs.DialogMovie;
import com.codingvalhalla.meredith.evidence.gui.dialogs.DialogTVShow;
import com.codingvalhalla.meredith.evidence.gui.dialogs.DialogTVEpisode;
import com.codingvalhalla.meredith.evidence.EvidenceMain;
import com.codingvalhalla.meredith.evidence.model.Episode;
import com.codingvalhalla.meredith.evidence.model.Movie;
import com.codingvalhalla.meredith.evidence.model.RatingMPAA;
import com.codingvalhalla.meredith.evidence.model.Season;
import com.codingvalhalla.meredith.evidence.model.TV_Show;
import com.codingvalhalla.meredith.evidence.utils.StaticAlerts;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Optional;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author Meredith
 */
public class GraphicUserInterface {

    private final static String TITLE = "Media records - JavaFX";
    private final static String RESOURCES = EvidenceMain.class.getResource("resources/").toExternalForm();

    private BorderPane rootLayout;
    private Stage mainStage;
    private Scene scene;
    private static boolean alreadyExecuted = false;
    private boolean saved = false;
    private boolean started = false;

    private HBox movieBox;
    private HBox tvShowBox;
    private HBox tvSeasonBox;
    private HBox tvEpisodesBox;

    private Button movieAddButton;
    private Button movieRemoveButton;
    private Button movieEditButton;

    private Button tvShowAddButton;
    private Button tvShowRemoveButton;
    private Button tvShowEditButton;

    private Button tvSeasonAddButton;
    private Button tvSeasonRemoveButton;
    private Button tvSeasonEditButton;

    private Button tvEpisodeAddButton;
    private Button tvEpisodeRemoveButton;
    private Button tvEpisodeEditButton;

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
    private TableColumn<Movie, String> movieName;
    private TableColumn<Movie, RatingMPAA> movieRating;
    private TableColumn<Movie, Integer> movieStars;

    private TV_Show currentTV_Show;
    private Season currentSeason;

    private TabPane tvShowPane;
    private Tab tvShowTab;
    private Tab tvSeasonTab;
    private Tab tvEpisodeTab;

    private TableView<TV_Show> tvShowList;
    private TableView<Season> tvSeasonList;
    private TableView<Episode> tvEpisodeList;

    private TableColumn<TV_Show, String> tvShowName;
    private TableColumn<TV_Show, RatingMPAA> tvShowRating;
    private TableColumn<TV_Show, Integer> tvShowStars;
    private TableColumn<TV_Show, Boolean> tvShowWatching;

    private TableColumn<Season, String> tvSeasonName;
    private TableColumn<Season, RatingMPAA> tvSeasonRating;
    private TableColumn<Season, Integer> tvSeasonStars;

    private TableColumn<Episode, String> tvEpisodeName;
    private TableColumn<Episode, RatingMPAA> tvEpisodeRating;
    private TableColumn<Episode, Integer> tvEpisodeStars;

    public GraphicUserInterface(Stage stage) throws IllegalAccessException {
        if (!alreadyExecuted) {
            mainStage = stage;
            mainStage.setTitle(TITLE);
            initRootLayout();
            createListerers();
            alreadyExecuted = true;

        } else {
            throw new IllegalAccessException("Cannot make more than one GUI");
        }
    }

    public static String getTitle() {
        return TITLE;
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
        scene = new Scene(rootLayout, 904, 451);
        scene.getStylesheets().add(GraphicUserInterface.class.getResource("valhalla.css").toExternalForm());
        mainStage.setMinWidth(920);
        mainStage.setMinHeight(490);

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
        initTVSeasonList();
        initTVEpisodeList();
        initTVShowPane();
        pane.getChildren().addAll(movieList, tvShowPane);
        pane.setMinWidth(Double.MIN_VALUE);
        pane.setMinHeight(Double.MIN_VALUE);

        return pane;
    }

    private AnchorPane initRightStage() {
        AnchorPane pane = new AnchorPane();
        rightStageVBoxM = new VBox();
        Label labelM = new Label("Comment");
        labelM.setPrefSize(200, 25);
        labelM.setStyle("-fx-font-weight:bold;" + "-fx-alignment:center;" + "-fx-text-alignment:center");
        rightStageVBoxM.setPrefWidth(200);
        textAreaM = new TextArea();
        textAreaM.setEditable(false);
        textAreaM.prefWidth(200);
        textAreaM.prefHeightProperty().bind(movieList.heightProperty().add(-25));
        textAreaM.setWrapText(true);
        rightStageVBoxM.getChildren().addAll(labelM, textAreaM);

        rightStageVBoxT = new VBox();
        rightStageVBoxT.setPrefWidth(200);
        textAreaT = new TextArea();
        textAreaT.setEditable(false);
        textAreaT.setWrapText(true);
        Label labelT = new Label("More informations");
        labelT.setPrefSize(200, 25);
        labelT.setStyle("-fx-font-weight:bold;" + "-fx-alignment:center;" + "-fx-text-alignment:center");
        HBox seasons = new HBox();
        HBox episodes = new HBox();
        HBox comments = new HBox();
        seasons.setPrefHeight(25);
        seasons.setPadding(new Insets(0, 0, 0, 5));
        episodes.setPrefHeight(25);
        episodes.setPadding(new Insets(0, 0, 0, 5));
        comments.setAlignment(Pos.CENTER);
        Label seasonsLabel = new Label("№ Seasons");
        seasonsLabel.setPrefWidth(150);
        seasonsLabel.setStyle("-fx-font-weight:bold;" + "-fx-text-alignment:center");

        Label episodesLabel = new Label("№ All Episodes ");
        episodesLabel.setPrefWidth(150);
        episodesLabel.setStyle("-fx-font-weight:bold;" + "-fx-text-alignment:center");
        Label commnetsLabel = new Label("Comment");
        Group labelHolder = new Group(commnetsLabel);
        commnetsLabel.setPrefHeight(25);
        commnetsLabel.setRotate(-90);
        commnetsLabel.setStyle("-fx-font-weight:bold;" + "-fx-alignment:center;" + "-fx-text-alignment:center");
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

    private VBox initBottomStage() {
        double buttonWidth = 120;
        Pane botBox = new Pane();

        VBox result = new VBox();
        HBox topBox = new HBox();
        movieBox = new HBox();
        tvShowBox = new HBox();
        tvSeasonBox = new HBox();
        tvEpisodesBox = new HBox();
        topBox.setPadding(new Insets(15, 12, 0, 12));
        topBox.setSpacing(10);

        movieBox.setPadding(new Insets(15, 12, 15, 12));
        movieBox.setSpacing(10);
        movieBox.setPrefHeight(50);

        tvShowBox.setPadding(new Insets(15, 12, 15, 12));
        tvShowBox.setSpacing(10);
        tvShowBox.setPrefHeight(50);

        tvSeasonBox.setPadding(new Insets(15, 12, 15, 12));
        tvSeasonBox.setSpacing(10);
        tvSeasonBox.setPrefHeight(50);

        tvEpisodesBox.setPadding(new Insets(15, 12, 15, 12));
        tvEpisodesBox.setSpacing(10);
        tvEpisodesBox.setPrefHeight(50);

        movieAddButton = new Button("Add - Movie");
        movieRemoveButton = new Button("Remove - Movie");
        movieEditButton = new Button("Edit - Movie");

        tvShowAddButton = new Button("Add - TV Show");
        tvShowRemoveButton = new Button("Remove - TV Show");
        tvShowEditButton = new Button("Edit - TV Show");

        tvSeasonAddButton = new Button("Add - Season");
        tvSeasonRemoveButton = new Button("Remove - Season");
        tvSeasonEditButton = new Button("Edit - Season");

        tvEpisodeAddButton = new Button("Add - Episode");
        tvEpisodeRemoveButton = new Button("Remove - Episode");
        tvEpisodeEditButton = new Button("Edit - Episode");

        saveButton = new Button("Make backup");
        restoreButton = new Button("Restore backup");

        topBox.getChildren().addAll(saveButton, restoreButton);
        movieBox.getChildren().addAll(movieAddButton, movieEditButton, movieRemoveButton);
        tvShowBox.getChildren().addAll(tvShowAddButton, tvShowEditButton, tvShowRemoveButton);
        tvSeasonBox.getChildren().addAll(tvSeasonAddButton, tvSeasonEditButton, tvSeasonRemoveButton);
        tvEpisodesBox.getChildren().addAll(tvEpisodeAddButton, tvEpisodeEditButton, tvEpisodeRemoveButton);
        botBox.getChildren().addAll(movieBox, tvShowBox, tvSeasonBox, tvEpisodesBox);
        result.getChildren().addAll(topBox, botBox);

        for (int i = 0; i < botBox.getChildren().size(); i++) {
            HBox tempBox = (HBox) botBox.getChildren().get(i);
            for (int j = 0; j < tempBox.getChildren().size(); j++) {
                Button tempButton = (Button) tempBox.getChildren().get(j);
                tempButton.setPrefWidth(buttonWidth);
            }
        }

        for (int i = 0; i < topBox.getChildren().size(); i++) {
            Button temp = (Button) topBox.getChildren().get(i);
            temp.setPrefWidth(buttonWidth);
        }

        return result;
    }

    private void initMovieList() {
        movieList = new TableView<>();
        movieList.getStyleClass().add("movie-list");

        movieName = new TableColumn<>("Name");
        movieRating = new TableColumn<>("Rating MPAA");
        movieStars = new TableColumn<>("Rating");

        movieRating.setResizable(false);
        movieStars.setResizable(false);

        movieRating.setStyle("-fx-alignment: CENTER;");

        movieStars.setStyle("-fx-alignment: CENTER;");
        movieStars.setCellFactory((TableColumn<Movie, Integer> param) -> {
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

        movieRating.setPrefWidth(110);
        movieStars.setPrefWidth(120);
        movieName.prefWidthProperty().bind(movieList.widthProperty().add(-movieStars.getPrefWidth()).add(-movieRating.getPrefWidth()).add(-15));

        movieName.setCellValueFactory(new PropertyValueFactory<>("name"));
        movieRating.setCellValueFactory(new PropertyValueFactory<>("ratingMPAA"));
        movieStars.setCellValueFactory(new PropertyValueFactory<>("stars"));

        movieList.getColumns().addAll(movieName, movieRating, movieStars);
        AnchorPane.setTopAnchor(movieList, 0.0);
        AnchorPane.setRightAnchor(movieList, 0.0);
        AnchorPane.setLeftAnchor(movieList, 0.0);
        AnchorPane.setBottomAnchor(movieList, 0.0);
        movieList.setVisible(true);
    }

    private void initTVShowPane() {
        tvShowPane = new TabPane();
        tvShowTab.setContent(tvShowList);
        tvShowPane.getTabs().add(tvShowTab);
        tvShowTab.setClosable(false);
        AnchorPane.setTopAnchor(tvShowPane, 0.0);
        AnchorPane.setRightAnchor(tvShowPane, 0.0);
        AnchorPane.setLeftAnchor(tvShowPane, 0.0);
        AnchorPane.setBottomAnchor(tvShowPane, 0.0);
    }

    private void initTVShowList() {
        tvShowTab = new Tab("TV Shows");
        tvShowList = new TableView<>();
        tvShowName = new TableColumn<>("Name");
        tvShowRating = new TableColumn<>("Rating MPAA");
        tvShowStars = new TableColumn<>("Rating");
        tvShowWatching = new TableColumn<>();

        Label labelWatching = new Label("W");
        Tooltip watchingColumnTooltip = new Tooltip("Watching");
        labelWatching.setTooltip(watchingColumnTooltip);
        tvShowWatching.setGraphic(labelWatching);

        tvShowRating.setStyle("-fx-alignment: CENTER;");
        tvShowStars.setStyle("-fx-alignment: CENTER;");
        tvShowWatching.setStyle("-fx-alignment: CENTER;");

        tvShowStars.setCellFactory(
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
        tvShowWatching.setCellFactory(
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
        tvShowRating.setPrefWidth(110);
        tvShowStars.setPrefWidth(120);
        tvShowWatching.setPrefWidth(40);
        tvShowName.prefWidthProperty().bind(tvShowList.widthProperty().add(-tvShowStars.getPrefWidth()).add(-tvShowRating.getPrefWidth()).add(-tvShowWatching.getPrefWidth()).add(-15));

        tvShowName.setCellValueFactory(
                new PropertyValueFactory<>("name"));
        tvShowRating.setCellValueFactory(
                new PropertyValueFactory<>("ratingMPAA"));
        tvShowStars.setCellValueFactory(
                new PropertyValueFactory<>("stars"));
        tvShowWatching.setCellValueFactory(
                new PropertyValueFactory<>("watching"));

        tvShowList.getColumns().addAll(tvShowName, tvShowRating, tvShowStars, tvShowWatching);

    }

    private void initTVSeasonList() {
        tvSeasonTab = new Tab();
        tvSeasonList = new TableView<>();
        tvSeasonName = new TableColumn<>("Name");
        tvSeasonRating = new TableColumn<>("Rating MPAA");
        tvSeasonStars = new TableColumn<>("Rating");
        Label labelWatching = new Label("W");
        Tooltip watchingColumnTooltip = new Tooltip("Watching");
        labelWatching.setTooltip(watchingColumnTooltip);

        tvSeasonRating.setStyle("-fx-alignment: CENTER;");
        tvSeasonStars.setStyle("-fx-alignment: CENTER;");

        tvSeasonStars.setCellFactory((TableColumn<Season, Integer> param) -> {
            final ImageView imageview = new ImageView();

            TableCell<Season, Integer> cell = new TableCell<Season, Integer>() {
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

        tvSeasonRating.setPrefWidth(110);
        tvSeasonStars.setPrefWidth(120);
        tvSeasonName.prefWidthProperty().bind(tvSeasonList.widthProperty().add(-tvSeasonStars.getPrefWidth()).add(-tvSeasonRating.getPrefWidth()).add(-15));

        tvSeasonName.setCellValueFactory(
                new PropertyValueFactory<>("name"));
        tvSeasonRating.setCellValueFactory(
                new PropertyValueFactory<>("ratingMPAA"));
        tvSeasonStars.setCellValueFactory(
                new PropertyValueFactory<>("stars"));

        tvSeasonList.getColumns().addAll(tvSeasonName, tvSeasonRating, tvSeasonStars);

    }

    private void initTVEpisodeList() {
        tvEpisodeTab = new Tab();
        tvEpisodeList = new TableView<>();
        tvEpisodeName = new TableColumn<>("Name");
        tvEpisodeRating = new TableColumn<>("Rating MPAA");
        tvEpisodeStars = new TableColumn<>("Rating");
        Label labelWatching = new Label("W");
        Tooltip watchingColumnTooltip = new Tooltip("Watching");
        labelWatching.setTooltip(watchingColumnTooltip);

        tvEpisodeRating.setStyle("-fx-alignment: CENTER;");
        tvEpisodeStars.setStyle("-fx-alignment: CENTER;");

        tvEpisodeStars.setCellFactory((TableColumn<Episode, Integer> param) -> {
            final ImageView imageview = new ImageView();
            TableCell<Episode, Integer> cell = new TableCell<Episode, Integer>() {
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

        tvEpisodeRating.setPrefWidth(110);
        tvEpisodeStars.setPrefWidth(120);
        tvEpisodeName.prefWidthProperty().bind(tvEpisodeList.widthProperty().add(-tvEpisodeStars.getPrefWidth()).add(-tvEpisodeRating.getPrefWidth()).add(-15));

        tvEpisodeName.setCellValueFactory(
                new PropertyValueFactory<>("name"));
        tvEpisodeRating.setCellValueFactory(
                new PropertyValueFactory<>("ratingMPAA"));
        tvEpisodeStars.setCellValueFactory(
                new PropertyValueFactory<>("stars"));

        tvEpisodeList.getColumns().addAll(tvEpisodeName, tvEpisodeRating, tvEpisodeStars);

    }

    private void createListerers() {
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
            textAreaT.setText(newValue != null ? newValue.getComments() : "");
            seasonsField.setText(newValue != null ? Integer.toString(newValue.getSeasons().size()) : "");
            episodesField.setText(newValue != null ? "Not implemented..." : "");
        });

        tvShowList.setOnMouseClicked((event) -> {
            if (event.getClickCount() == 2) {
                if (tvShowPane.getTabs().contains(tvSeasonTab)) {
                    closeTabSeason();
                }
                currentTV_Show = tvShowList.getSelectionModel().getSelectedItem();
                tvSeasonList.getItems().clear();
                tvSeasonList.getItems().addAll(currentTV_Show.getSeasons());
                tvSeasonTab.setText("TV Show - " + currentTV_Show.getName());
                tvSeasonTab.setContent(tvSeasonList);
                tvShowPane.getTabs().add(1, tvSeasonTab);
                tvShowPane.getSelectionModel().select(tvSeasonTab);
            }
        });
        tvSeasonList.setOnMouseClicked((event) -> {
            if (event.getClickCount() == 2) {
                if (tvShowPane.getTabs().contains(tvEpisodeTab)) {
                    closeTabEpisode();
                }
                currentSeason = tvSeasonList.getSelectionModel().getSelectedItem();
                tvEpisodeTab.setText(currentSeason.getName() + " - " + currentTV_Show.getName());
                tvEpisodeTab.setContent(tvEpisodeList);
                tvEpisodeList.getItems().clear();
                tvEpisodeList.getItems().addAll(currentSeason.getEpisodes());
                tvShowPane.getTabs().add(2, tvEpisodeTab);
                tvShowPane.getSelectionModel().select(tvEpisodeTab);
            }
        });

        tvEpisodeList.setOnMouseClicked((event) -> {
            if (event.getClickCount() == 2) {
                tvEpisodeEditButton.fire();
            }
        });
        tvEpisodeTab.setOnCloseRequest((event) -> {
            closeTabEpisode();

        });
        tvSeasonTab.setOnCloseRequest((event) -> {
            closeTabSeason();

        });

        movieList.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Movie> observable, Movie oldValue, Movie newValue) -> {
            updateMovieComment(newValue);
        });
        movieList.setOnMouseClicked((event) -> {
            if (event.getClickCount() == 2) {
                movieEditButton.fire();
            }
        });

        saveButton.setOnAction(handlerSaveButton);
        restoreButton.setOnAction(handlerRestoreButton);

        movieAddButton.setOnAction(handlerMovieAddButton);
        movieEditButton.setOnAction(handlerMovieEditButton);
        movieEditButton.disableProperty().bind(movieList.getSelectionModel().selectedItemProperty().isNull());
        movieRemoveButton.setOnAction(handlerMovieRemoveButton);
        movieRemoveButton.disableProperty().bind(movieList.getSelectionModel().selectedItemProperty().isNull());

        tvShowAddButton.setOnAction(handlerTVShowAddButton);
        tvShowEditButton.setOnAction(handlerTVShowEditButton);
        tvShowEditButton.disableProperty().bind(tvShowList.getSelectionModel().selectedItemProperty().isNull());
        tvShowRemoveButton.setOnAction(handlerTVShowRemoveButton);
        tvShowRemoveButton.disableProperty().bind(tvShowList.getSelectionModel().selectedItemProperty().isNull());

        tvSeasonAddButton.setOnAction(handlerTVSeasonAddButton);
        tvSeasonEditButton.setOnAction(handlerTVSeasonEditButton);
        tvSeasonEditButton.disableProperty().bind(tvSeasonList.getSelectionModel().selectedItemProperty().isNull());
        tvSeasonRemoveButton.setOnAction(handlerTVSeasonRemoveButton);

        tvEpisodeAddButton.setOnAction(handlerTVEpisodeAddButton);
        tvEpisodeEditButton.setOnAction(handlerTVEpisodeEditButton);
        tvEpisodeEditButton.disableProperty().bind(tvEpisodeList.getSelectionModel().selectedItemProperty().isNull());
        tvEpisodeRemoveButton.setOnAction(handlerTVEpisodeRemoveButton);
        tvEpisodeRemoveButton.disableProperty().bind(tvEpisodeList.getSelectionModel().selectedItemProperty().isNull());

        movieBox.visibleProperty().bind(buttonMovie.selectedProperty());
        tvShowBox.visibleProperty().bind(buttonTVShow.selectedProperty().and(tvShowTab.selectedProperty()));
        tvSeasonBox.visibleProperty().bind(buttonTVShow.selectedProperty().and(tvSeasonTab.selectedProperty()));
        tvEpisodesBox.visibleProperty().bind(buttonTVShow.selectedProperty().and(tvEpisodeTab.selectedProperty()));

        movieList.visibleProperty().bind(buttonMovie.selectedProperty());
        tvShowPane.visibleProperty().bind(buttonTVShow.selectedProperty());

    }

    private void initRadioButtons() {
        buttonMovie.setToggleGroup(groupToggle);
        buttonTVShow.setToggleGroup(groupToggle);
        buttonTVShow.setDisable(false);
        buttonMovie.setSelected(true);
    }

    private void closeTabEpisode() {
        currentSeason.getEpisodes().clear();
        currentSeason.getEpisodes().addAll(tvEpisodeList.getItems());
        tvShowPane.getTabs().remove(tvEpisodeTab);
    }

    private void closeTabSeason() {
        if (tvShowPane.getTabs().contains(tvEpisodeTab)) {
            closeTabEpisode();
        }
        currentTV_Show.getSeasons().clear();
        currentTV_Show.getSeasons().addAll(tvSeasonList.getItems());
        tvShowPane.getTabs().remove(tvSeasonTab);
    }

    @SuppressWarnings("FieldMayBeFinal")
    private EventHandler<ActionEvent> handlerMovieAddButton = (ActionEvent event) -> {
        DialogMovie add = new DialogMovie();
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

    };

    @SuppressWarnings("FieldMayBeFinal")
    private EventHandler<ActionEvent> handlerMovieEditButton = (ActionEvent event) -> {
        Movie x = movieList.getSelectionModel().getSelectedItem();
        DialogMovie edit = new DialogMovie(x);
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
        updateMovieComment(movieList.getSelectionModel().getSelectedItem());
    };

    @SuppressWarnings("FieldMayBeFinal")
    private EventHandler<ActionEvent> handlerMovieRemoveButton = (ActionEvent event) -> {
        Movie x = movieList.getSelectionModel().getSelectedItem();
        movieList.getItems().remove(x);
        movieList.refresh();
        saved = false;
    };

    @SuppressWarnings("FieldMayBeFinal")
    private EventHandler<ActionEvent> handlerTVShowAddButton = (ActionEvent event) -> {
        DialogTVShow add = new DialogTVShow();
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
    };

    @SuppressWarnings("FieldMayBeFinal")
    private EventHandler<ActionEvent> handlerTVShowEditButton = (ActionEvent event) -> {
        TV_Show x = tvShowList.getSelectionModel().getSelectedItem();
        DialogTVShow edit = new DialogTVShow(x);
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
    };

    @SuppressWarnings("FieldMayBeFinal")
    private EventHandler<ActionEvent> handlerTVShowRemoveButton = (ActionEvent event) -> {
        TV_Show x = tvShowList.getSelectionModel().getSelectedItem();
        tvShowList.getItems().remove(x);
        tvShowList.refresh();
        saved = false;
    };

    @SuppressWarnings("FieldMayBeFinal")
    private EventHandler<ActionEvent> handlerTVSeasonAddButton = (ActionEvent event) -> {
        Season result = new Season("Season " + (currentTV_Show.getSeasons().size() + 1), 0, false);
        result.setRatingMPAA(currentTV_Show.getRatingMPAA());
        currentTV_Show.getSeasons().add(result);
        tvSeasonList.getItems().clear();
        tvSeasonList.getItems().addAll(currentTV_Show.getSeasons());
        saved = false;
        tvSeasonList.refresh();
    };

    @SuppressWarnings("FieldMayBeFinal")
    private EventHandler<ActionEvent> handlerTVSeasonEditButton = (ActionEvent event) -> {
        DialogTVSeason edit = new DialogTVSeason(tvSeasonList.getSelectionModel().getSelectedItem());
        edit.setTitle("Edit season");
        DialogPane dialogPane = edit.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("valhalla.css").toExternalForm());
        dialogPane.getStyleClass().add("edit-dialog");
        edit.show();
        edit.afterShow();
        edit.resultProperty().addListener((observable, oldValue, newValue) -> {
            saved = false;
            movieList.refresh();
        });
        updateMovieComment(movieList.getSelectionModel().getSelectedItem());
    };

    @SuppressWarnings("FieldMayBeFinal")
    private EventHandler<ActionEvent> handlerTVSeasonRemoveButton = (ActionEvent event) -> {
        if (!tvSeasonList.getItems().isEmpty()) {
            Season x = tvSeasonList.getItems().get(tvSeasonList.getItems().size() - 1);
            currentTV_Show.getSeasons().remove(x);
            tvSeasonList.getItems().clear();
            tvSeasonList.getItems().addAll(currentTV_Show.getSeasons());
            saved = false;
        }
        tvSeasonList.refresh();

    };

    @SuppressWarnings("FieldMayBeFinal")
    private EventHandler<ActionEvent> handlerTVEpisodeAddButton = (ActionEvent event) -> {
        DialogTVEpisode add = new DialogTVEpisode();
        add.setTitle("Add episode");
        DialogPane dialogPane = add.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("valhalla.css").toExternalForm());
        dialogPane.getStyleClass().add("add-dialog");
        add.show();
        add.afterShow();
        add.resultProperty().addListener((observable, oldValue, newValue) -> {
            try {
                newValue.setRatingMPAA(currentSeason.getRatingMPAA());
                currentSeason.getEpisodes().add(newValue);

                saved = false;
                tvEpisodeList.getItems().clear();
                tvEpisodeList.getItems().addAll(currentSeason.getEpisodes());
                tvEpisodeList.refresh();
            } catch (Exception e) {
                StaticAlerts.exceptionDialog(e, e.getLocalizedMessage(), mainStage);
            }
        });
    };

    @SuppressWarnings("FieldMayBeFinal")
    private EventHandler<ActionEvent> handlerTVEpisodeEditButton = (ActionEvent event) -> {
        Episode x = tvEpisodeList.getSelectionModel().getSelectedItem();
        DialogTVEpisode edit = new DialogTVEpisode();
        edit.setTitle("Edit episode");
        DialogPane dialogPane = edit.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("valhalla.css").toExternalForm());
        dialogPane.getStyleClass().add("add-dialog");
        edit.show();
        edit.afterShow();
        edit.resultProperty().addListener((observable, oldValue, newValue) -> {
            try {
                newValue.setRatingMPAA(currentSeason.getRatingMPAA());
                currentSeason.getEpisodes().add(newValue);

                saved = false;
                tvEpisodeList.getItems().clear();
                tvEpisodeList.getItems().addAll(currentSeason.getEpisodes());
                tvEpisodeList.refresh();
            } catch (Exception e) {
                StaticAlerts.exceptionDialog(e, e.getLocalizedMessage(), mainStage);
            }
        });
    };

    @SuppressWarnings("FieldMayBeFinal")
    private EventHandler<ActionEvent> handlerTVEpisodeRemoveButton = (ActionEvent event) -> {
        Episode x = tvEpisodeList.getSelectionModel().getSelectedItem();
        currentSeason.getEpisodes().remove(x);
        tvEpisodeList.getItems().clear();
        tvEpisodeList.getItems().addAll(currentSeason.getEpisodes());
        tvEpisodeList.refresh();
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
            if (tvShowPane.getTabs().size() != 1) {
                closeTabSeason();
            }
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
            if (tvShowPane.getTabs().size() != 1) {
                closeTabSeason();
            }
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

    public void afterStarted() {
//TODO JUST IN CASE
    }

    private void updateMovieComment(Movie movie) {
        if (movie != null) {
            textAreaM.setText(movie.getComments());
        } else {
            textAreaM.setText("");
        }
    }
}
