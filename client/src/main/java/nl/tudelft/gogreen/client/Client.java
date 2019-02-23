package nl.tudelft.gogreen.client;

import nl.tudelft.gogreen.api.API;
import nl.tudelft.gogreen.api.EndPoints;
import nl.tudelft.gogreen.api.ServerCallback;
import nl.tudelft.gogreen.api.servermodels.BasicResponse;
import nl.tudelft.gogreen.api.servermodels.User;
import nl.tudelft.gogreen.shared.Shared;
import nl.tudelft.gogreen.shared.StatusCodes;

public class Client {
    /* Just ignore this for now */
    public static void main(String... args) {
        System.out.println("Hello, world!");
        System.out.println(Shared.getTestString());

        API.prepareAPI(true);

        // Test status
        API.requestStatus(new ServerCallback<BasicResponse>() {
            @Override
            public void run() {
                System.out.println("Found response: " + getResult().getResponse());
                asyncChain1();
            }
        }, EndPoints.STATUS);
    }

    //TODO: Remove these async chains, this is just to give an example

    private static void asyncChain1() {
        // Test authentication
        API.requestStatus(new ServerCallback<BasicResponse>() {
            @Override
            public void run() {
                if (getResponse().getStatus() == StatusCodes.UNAUTHORIZED) {
                    System.out.println("Client not authenticated, as it should be!");
                } else {
                    System.out.println("Oof I guess?");
                }

                asyncChain2();
            }
        }, EndPoints.STATUS_USER);
    }

    private static void asyncChain2() {
        // Test authentication
        API.attemptAuthentication(new ServerCallback<BasicResponse>() {
            @Override
            public void run() {
                if (getResponse().getStatus() == StatusCodes.AUTHENTICATED) {
                    System.out.println("Logged in!");
                } else {
                    System.out.println("Login failed!");
                }

                asyncChain3();
            }
        }, new User("admin", "password"));
    }

    private static void asyncChain3() {
        // Test if admin rights
        API.requestStatus(new ServerCallback<BasicResponse>() {
            @Override
            public void run() {
                if (getResponse().getStatus() == StatusCodes.AUTHENTICATED) {
                    System.out.println("Client is authenticated as an admin");
                } else if (getResponse().getStatus() == StatusCodes.FORBIDDEN) {
                    System.out.println("Client is authenticated, but not as an admin");
                } else {
                    System.out.println("Client is still not authenticated");
                }

                API.closeAPI();
            }
        }, EndPoints.STATUS_ADMIN);
    }
}
