package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import org.springframework.stereotype.Component;

@Component
public class AccountPageController {

    @FXML
    private AnchorPane CO2Counter;

    @FXML
    private Label userTitle;

    @FXML
    private Label information;

    @FXML
    private Label fullName;

    @FXML
    private Button modDataButton;

    @FXML
    private ScrollPane badgePane;

}
