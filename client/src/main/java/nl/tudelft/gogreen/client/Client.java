package nl.tudelft.gogreen.client;

import javafx.application.Platform;
import nl.tudelft.gogreen.api.API;
import nl.tudelft.gogreen.api.ServerCallback;
import nl.tudelft.gogreen.api.servermodels.BasicResponse;
import nl.tudelft.gogreen.api.servermodels.User;
import nl.tudelft.gogreen.shared.Shared;
import nl.tudelft.gogreen.shared.models.SubmitResponse;
import nl.tudelft.gogreen.shared.models.SubmittedActivity;

public class Client {
    /* Just ignore this for now */
    /*
    IMPORTANT:
    Don't use this class for anything, it's just an example class and is something that is just
    here from before we changed our program planning. This class is scheduled to be completely deleted
    in the soon future, as soon as its purpose (giving an example) has been fulfilled.
     */

    public static void main(String... args) {
        System.out.println("Hello, world!");
        System.out.println(Shared.getTestString());

        API.prepareAPI(true);

        API.requestFakeStatus(new ServerCallback<Object, BasicResponse>() {
            @Override
            public void run() {
                System.out.println("Found (fake) response: " + getResult().getResponse());
            }
        });

        // Example
        API.retrieveFakeCo2(new ServerCallback<Object, BasicResponse>() {
            @Override
            public void run() {
                if (getStatusCode() != 200) {
                    System.out.println("error");
                } else {
                    doSomething(getResult());
                }
            }
        });


        API.attemptAuthentication(new ServerCallback<Object, BasicResponse>() {
            @Override
            public void run() {
                System.out.println("Logged in");

                API.submitActivity(new ServerCallback<SubmittedActivity, SubmitResponse>() {
                    @Override
                    public void run() {
                        System.out.println("Submitted activity!");
                        System.out.println(getResult().getExternalId());
                        System.out.println(getResult().getPoints());
                        System.out.println(getResult().getUpdatedPoints());
                        System.out.println(getResult().getResponse());
                    }
                }, SubmittedActivity.builder().activityId(1).build());
            }
        }, new User("admin", "password", 0F));
    }

    public static void doSomething(BasicResponse basicResponse) {
        Platform.runLater(() -> {
            System.out.println("Do something with this value: " + basicResponse.getResponse());
        });
    }
}

