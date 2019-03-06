package nl.tudelft.gogreen.client;

import nl.tudelft.gogreen.api.API;
import nl.tudelft.gogreen.api.ServerCallback;
import nl.tudelft.gogreen.api.servermodels.BasicResponse;
import nl.tudelft.gogreen.api.servermodels.Category;
import nl.tudelft.gogreen.shared.Shared;
import nl.tudelft.gogreen.shared.StatusCodes;

import java.util.ArrayList;
import java.util.Arrays;

public class Client {
    /* Just ignore this for now */
    /*
    IMPORTANT:
    Don't use this class for anything, it's just an example class and is something that is just
    here from before we changed our program planning. This class is scheduled to be completely deleted
    in the soon future, as soon as its purpose (giving an example) has been fulfilled.
     */

    public static void main(String... args)  {
        System.out.println("Hello, world!");
        System.out.println(Shared.getTestString());

        API.prepareAPI(true);

        API.requestFakeStatus(new ServerCallback<BasicResponse>() {
            @Override
            public void run() {
                System.out.println("Found (fake) response: " + getResult().getResponse());
            }
        });

        /*
        Please ignore the ugly looking code for now, it's mainly for testing the cache
         */
        // Test status
//        API.requestStatus(new ServerCallback<BasicResponse>() {
//            @Override
//            public void run() {
//                System.out.println(Thread.currentThread().getName() + " => " + "Found" + (isCached() ? " cached " : " ") + "response: " + getResult().getResponse());
//
//                API.requestStatus(new ServerCallback<BasicResponse>() {
//                    @Override
//                    public void run() {
//                        System.out.println(Thread.currentThread().getName() + " => " + "Found" + (isCached() ? " cached " : " ") + "response: " + getResult().getResponse());
//
//                        API.requestStatus(new ServerCallback<BasicResponse>() {
//                            @Override
//                            public void run() {
//                                System.out.println(Thread.currentThread().getName() + " => " + "Found" + (isCached() ? " cached " : " ") + "response: " + getResult().getResponse());
//
//                                clearCache();
//
//                                API.requestStatus(new ServerCallback<BasicResponse>() {
//                                    @Override
//                                    public void run() {
//                                        System.out.println(Thread.currentThread().getName() + " => " + "Found" + (isCached() ? " cached " : " ") + "response: " + getResult().getResponse());
//
//                                        API.requestStatus(new ServerCallback<BasicResponse>() {
//                                            @Override
//                                            public void run() {
//                                                System.out.println(Thread.currentThread().getName() + " => " + "Found" + (isCached() ? " cached " : " ") + "response: " + getResult().getResponse());
//
//                                                API.attemptAuthentication(new ServerCallback<BasicResponse>() {
//                                                    @Override
//                                                    public void run() {
//                                                        if (getResponse().getStatus() == StatusCodes.AUTHENTICATED) {
//                                                            System.out.println("Logged in!");
//                                                        } else {
//                                                            System.out.println("Login failed!");
//                                                        }
//
//                                                        API.closeAPI();
//                                                    }
//                                                }, new User("admin", "password"));
//                                            }
//                                        }, EndPoints.STATUS);
//                                    }
//                                }, EndPoints.STATUS);
//                            }
//                        }, EndPoints.STATUS);
//                    }
//                }, EndPoints.STATUS);
//            }
//        }, EndPoints.STATUS);


        //getCatList();

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
    }
}

