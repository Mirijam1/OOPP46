package controllers;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class SidebarController {

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

//    @FXML
//    public Label userLabel;

    @FXML
    private Pane subscene;

    private Background BACKGROUND = new Background(new BackgroundImage(new Image("img/gogreenbg.jpg", 1280, 720, true, true), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT));

//    private User user;

    private boolean isGamified = false;
    private boolean isPaused = false;
    private MediaPlayer mediaPlayer;

    @FXML
    public void initialize() throws IOException {
//        API.retrieveUserProfile(new ServerCallback<Object, UserServer>() {
//            @Override
//            public void run() {
//                if (getStatusCode() != 200) {
//                    System.out.println("Error");
//                } else {
//                    user = getResult().getUser();
//                    Platform.runLater(() -> userLabel.setText(user.getUsername()));
//                }
//            }
//        });
        AnchorPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/trackerScreen.fxml"));

        subscene.getChildren().setAll(pane);
        subscene.setBackground(BACKGROUND);
    }

    @FXML
    void overviewpage(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/trackerScreen.fxml"));
        subscene.getChildren().setAll(pane);
    }

    @FXML
    void newactivitypage(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/dashboard.fxml"));
        subscene.getChildren().setAll(pane);
    }

    @FXML
    void leaderboardpage(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/leaderboard.fxml"));
        subscene.getChildren().setAll(pane);
    }

    @FXML
    void friendspage(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/friends.fxml"));
        subscene.getChildren().setAll(pane);
    }

    @FXML
    void accountpage(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/accountPage.fxml"));
        subscene.getChildren().setAll(pane);
    }

    @FXML
    void addfriendpage(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/addfriend.fxml"));
        subscene.getChildren().setAll(pane);
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
