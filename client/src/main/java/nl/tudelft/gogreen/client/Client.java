package nl.tudelft.gogreen.client;

<<<<<<< HEAD
import nl.tudelft.gogreen.api.API;
import nl.tudelft.gogreen.api.EndPoints;
import nl.tudelft.gogreen.api.ServerCallback;
import nl.tudelft.gogreen.api.servermodels.BasicResponse;
import nl.tudelft.gogreen.api.servermodels.Category;
import nl.tudelft.gogreen.api.servermodels.User;
import nl.tudelft.gogreen.shared.Shared;
import nl.tudelft.gogreen.shared.StatusCodes;

import java.util.ArrayList;
import java.util.Arrays;
=======
import javafx.application.Platform;
import nl.tudelft.gogreen.api.API;
import nl.tudelft.gogreen.api.ServerCallback;
import nl.tudelft.gogreen.api.servermodels.BasicResponse;
import nl.tudelft.gogreen.api.servermodels.User;
import nl.tudelft.gogreen.shared.Shared;
import nl.tudelft.gogreen.shared.models.SubmitResponse;
import nl.tudelft.gogreen.shared.models.SubmittedActivity;
>>>>>>> dev

public class Client {
    /* Just ignore this for now */
    /*
    IMPORTANT:
    Don't use this class for anything, it's just an example class and is something that is just
    here from before we changed our program planning. This class is scheduled to be completely deleted
    in the soon future, as soon as its purpose (giving an example) has been fulfilled.
     */

<<<<<<< HEAD
    public static void main(String... args)  {
=======
    public static void main(String... args) {
>>>>>>> dev
        System.out.println("Hello, world!");
        System.out.println(Shared.getTestString());

        API.prepareAPI(true);

<<<<<<< HEAD
        // Test status
//        API.requestStatus(new ServerCallback<BasicResponse>() {
//            @Override
//            public void run() {
//                System.out.println("Found response: " + getResult().getResponse());
//                asyncChain1();
//            }
//        }, EndPoints.STATUS);

        getCatList();

    }


    //TODO: Remove these async chains, this is just to give an example

//    private static void asyncChain1() {
//        // Test authentication
//        API.requestStatus(new ServerCallback<BasicResponse>() {
//            @Override
//            public void run() {
//                if (getResponse().getStatus() == StatusCodes.UNAUTHORIZED) {
//                    System.out.println("Client not authenticated, as it should be!");
//                } else {
//                    System.out.println("Oof I guess?");
//                }
//
//                asyncChain2();
//            }
//        }, EndPoints.STATUS_USER);
//    }
//
//    private static void asyncChain2() {
//        // Test authentication
//        API.attemptAuthentication(new ServerCallback<BasicResponse>() {
//            @Override
//            public void run() {
//                if (getResponse().getStatus() == StatusCodes.AUTHENTICATED) {
//                    System.out.println("Logged in!");
//                } else {
//                    System.out.println("Login failed!");
//                }
//
//                asyncChain3();
//            }
//        }, new User("admin", "password"));
//    }
//
//    private static void asyncChain3() {
//        // Test if admin rights
//        API.requestStatus(new ServerCallback<BasicResponse>() {
//            @Override
//            public void run() {
//                if (getResponse().getStatus() == StatusCodes.AUTHENTICATED) {
//                    System.out.println("Client is authenticated as an admin");
//                } else if (getResponse().getStatus() == StatusCodes.FORBIDDEN) {
//                    System.out.println("Client is authenticated, but not as an admin");
//                } else {
//                    System.out.println("Client is still not authenticated");
//                }
//
//                API.closeAPI();
//            }
//        }, EndPoints.STATUS_ADMIN);
//    }

    public static ArrayList<Category> getCatList()  {
        API.retrieveCategoryList(
            new ServerCallback<Category[]>() {
                @Override
                public void run() {
                    ArrayList<Category> catList;
                    if (getResponse().getStatus() == StatusCodes.AUTHENTICATED) {
                        System.out.println("API is working");
                        Category[] catArray = (Category[]) getResponse().getBody();
                        catList = new ArrayList<>(Arrays.asList(catArray));
                        getCategoryList(catList);
                    }

                }
            });
        System.out.println("null list");
        return null;
    }

    public static ArrayList<Category> getCategoryList(ArrayList<Category> list) {
        System.out.println(list.toString());
        return list;
=======
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
>>>>>>> dev
    }
}

