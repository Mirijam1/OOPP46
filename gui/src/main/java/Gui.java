import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import nl.tudelft.gogreen.api.API;

public class Gui extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Setup API
        API.prepareAPI(true);
        /*
        Please note that this call makes the client program have some threads always working,
        which means the application will keep running even after you close the GUI (you can only force
        close it in your IDE). To stop the threads just call API.closeAPI() when the program should exit.
         */

        // Continue start
        Parent root = FXMLLoader.load(getClass().getResource("fxml/login.fxml"));
        primaryStage.getIcons().add(new Image("img/leaficon.png"));
        primaryStage.setTitle("GoGreen");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
        primaryStage.setOnCloseRequest(t -> {
            API.closeAPI();
            Platform.exit();
            System.exit(0);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
