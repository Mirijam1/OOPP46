package nl.tudelft.gogreen.gui.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import nl.tudelft.gogreen.api.API;
import nl.tudelft.gogreen.api.ServerCallback;
import nl.tudelft.gogreen.shared.models.UserServer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SidebarController implements Initializable {

    @FXML
    private AnchorPane anchor;

    @FXML
    private VBox sidebar;

    @FXML
    private JFXButton overview;

    @FXML
    private JFXButton add;

    @FXML
    private JFXButton leaderboard;

    @FXML
    private JFXButton friends;

    @FXML
    private JFXButton account;

    @FXML
    private JFXButton logout;

    @FXML
    private Pane addFriendPane;

    @FXML
    public Label userLabel;

    protected static SidebarController controller;

    @FXML
    private Pane subscene;

    private Background BACKGROUND =
            new Background(new BackgroundImage(new Image("img/gogreenbg.jpg",
                    1280, 720, true, true),
                    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                    BackgroundSize.DEFAULT));

    private boolean isGamified = false;
    private boolean isPaused = false;
    private boolean isExtended = false;
    private MediaPlayer mediaPlayer;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        API.retrieveUserProfile(new ServerCallback<Object, UserServer>() {
            @Override
            public void run() {
                if (getStatusCode() != 200) {
                    System.out.println("Error");
                } else {
                    Platform.runLater(() -> userLabel.setText(getResult().getUser().getUsername()));
                }
            }
        });

        AnchorPane pane = null;
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/trackerScreen.fxml"));
        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        subscene.getChildren().setAll(pane);
        subscene.setBackground(BACKGROUND);
    }

    @FXML
    void overviewpage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/trackerScreen.fxml"));
        AnchorPane pane = loader.load();
        subscene.getChildren().setAll(pane);
    }

    @FXML
    void newactivitypage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/dashboard.fxml"));
        AnchorPane pane = loader.load();
        subscene.getChildren().setAll(pane);
    }

    @FXML
    void leaderboardpage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/leaderboard.fxml"));
        AnchorPane pane = loader.load();
        subscene.getChildren().setAll(pane);
    }

    @FXML
    void friendspage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/friends.fxml"));
        AnchorPane pane = loader.load();
        subscene.getChildren().setAll(pane);
    }

    @FXML
    void accountpage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/accountPage.fxml"));
        AnchorPane pane = loader.load();
        subscene.getChildren().setAll(pane);
    }

    @FXML
    void addfriendpage(ActionEvent event) {
        if (!isExtended) {
            addFriendPane.translateYProperty().set(0);

            Timeline timeline = new Timeline();
            KeyValue kv = new KeyValue(addFriendPane.translateYProperty(), -addFriendPane.getPrefHeight(), Interpolator.EASE_IN);
            KeyFrame kf = new KeyFrame(Duration.millis(200), kv);
            timeline.getKeyFrames().add(kf);
            timeline.play();

            isExtended = true;
        } else {
            addFriendPane.translateYProperty().set(-addFriendPane.getPrefHeight());

            Timeline timeline = new Timeline();
            KeyValue kv = new KeyValue(addFriendPane.translateYProperty(), 0, Interpolator.EASE_OUT);
            KeyFrame kf = new KeyFrame(Duration.millis(200), kv);
            timeline.getKeyFrames().add(kf);
            timeline.play();
            isExtended = false;
        }
    }

    @FXML
    void logout(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void buttonhover(MouseEvent event) {
        JFXButton button = (JFXButton) event.getSource();
        String id = button.getId();
        switch (id) {
            case "overview":
                button.setText("Home");
                break;
            case "add":
                button.setText("Add Activity");
                break;
            case "leaderboard":
                button.setText("Leaderboard");
                break;
            case "friends":
                button.setText("Friends");
                break;
            case "account":
                button.setText("Account");
                break;
            case "logout":
                button.setText("Logout");
                break;
            default:
                break;
        }
    }

    @FXML
    void buttonunhover(MouseEvent event) {
        JFXButton button = (JFXButton) event.getSource();
        button.setText("");
    }

    public void gamify(ActionEvent actionEvent) {
        if (!isGamified) {
            isGamified = true;
            BACKGROUND = new Background(new BackgroundImage(new Image("img/gamifybg.jpg", 1280, 720, true, true), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT));
            subscene.setBackground(BACKGROUND);
            URL url = getClass().getClassLoader().getResource("media/C418 - Minecraft.wav");

            if (url == null) {
                return;
            }

            Media sound = new Media(url.toExternalForm());
            mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.play();
        } else {
            if (isPaused) {
                mediaPlayer.play();
                isPaused = false;
            } else {
                mediaPlayer.pause();
                isPaused = true;
            }
        }
    }
}
