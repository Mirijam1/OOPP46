package nl.tudelft.gogreen.gui.controllers.helpers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import nl.tudelft.gogreen.api.API;
import nl.tudelft.gogreen.api.ServerCallback;
import nl.tudelft.gogreen.shared.models.SubmitResponse;
import nl.tudelft.gogreen.shared.models.SubmittedActivity;
import nl.tudelft.gogreen.shared.models.SubmittedActivityOption;

import java.util.ArrayList;
import java.util.Collection;

public class ConfirmationController {

    @FXML
    private Button confirm;

    @FXML
    private Button cancel;

    @FXML
    private ImageView image;

    @FXML
    private Label confirmation;

    @FXML
    private Label content;

    @FXML
    protected void confirmButton(ActionEvent event) {
        Collection<SubmittedActivityOption> options =
                new ArrayList<>();

        options.add(new SubmittedActivityOption(0, "4"));
        SubmittedActivity activity =
                SubmittedActivity.builder().activityId(0).options(options).build();

        API.submitActivity(new ServerCallback<SubmittedActivity, SubmitResponse>() {
            @Override
            public void run() {
                System.out.println(getResult().getExternalId());
            }
        }, activity);
    }

    @FXML
    protected void exit(ActionEvent event) {
        System.out.println("exit");

        Stage stage = (Stage) cancel.getScene().getWindow();

        stage.close();

    }
}
