package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import nl.tudelft.gogreen.api.API;
import nl.tudelft.gogreen.api.ServerCallback;
import nl.tudelft.gogreen.shared.models.UserServer;
import nl.tudelft.gogreen.shared.models.social.Friendship;

public class AddFriendController {

    @FXML
    private Pane pane;

    @FXML
    private JFXButton searchButton;

    @FXML
    private JFXTextField searchField;

    @FXML
    private Button searchResult;

    @FXML
    private VBox addFriendVBox;

    @FXML
    private JFXButton exit;

    @FXML
    void exit(ActionEvent event) {
    }

    @FXML
    void searchUser(ActionEvent event) {
        if (searchField.getText().equals("")) {
            return;
        }

        String searchUserName = searchField.getText();
        System.out.println(searchUserName);

        API.searchUserProfiles(new ServerCallback<Object, UserServer>() {
            @Override
            public void run() {
                if (getStatusCode() != 200) {
                    System.out.println(getStatusCode());
                } else {
                    System.out.println(getResult());
                    Platform.runLater(() -> createButton(getResult()));
                }
            }
        }, searchUserName);

//        searchField.setText("");
    }

    private void createButton(UserServer user) {
        searchResult.setText(user.getUser().getUsername());
        searchResult.setOnAction(addFriend(user));
    }

    private EventHandler<ActionEvent> addFriend(UserServer user) {
        return event -> API.addFriend(new ServerCallback<Object, Friendship>() {

            @Override
            public void run() {
                if (getStatusCode() != 200) {
                    System.out.println(getStatusCode());
                } else {
                    System.out.println(getResult());
                }
            }
        }, user.getUser().getUsername());
    }
}
