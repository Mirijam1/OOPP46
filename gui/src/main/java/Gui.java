import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import nl.tudelft.gogreen.api.API;
import nl.tudelft.gogreen.api.ServerCallback;
import nl.tudelft.gogreen.api.servermodels.BasicResponse;
import nl.tudelft.gogreen.api.servermodels.CompletedActivity;
import nl.tudelft.gogreen.api.servermodels.User;
import nl.tudelft.gogreen.shared.models.SubmitResponse;
import nl.tudelft.gogreen.shared.models.SubmittedActivity;

public class Gui extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Setup API
        API.prepareAPI(false);
        /*
        Please note that this call makes the client program have some threads always working,
        which means the application will keep running even after you close the GUI (you can only force
        close it in your IDE). To stop the threads just call API.closeAPI() when the program should exit.
         */

//        API.attemptAuthentication(new ServerCallback<Object, BasicResponse>() {
//            @Override
//            public void run() {
//                API.retrieveCompletedActivities(new ServerCallback<Object, CompletedActivity[]>() {
//                    @Override
//                    public void run() {
//                        for (CompletedActivity activity : getResult()) {
//                            System.out.print(activity.getExternalId() + " -> ");
//                            System.out.println(activity.getDateTimeCompleted().getDayOfWeek().name());
//                        }
//                    }
//                });
//            }
//        }, new User("admin", "password", 0F));


        // Continue start
        Parent root = FXMLLoader.load(getClass().getResource("fxml/login.fxml"));
      //  primaryStage.getIcons().add(new Image("img/logo.png"));
        primaryStage.setTitle("GoGreen");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
        primaryStage.setOnCloseRequest(t -> {
            Platform.exit();
            System.exit(0);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
