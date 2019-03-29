package nl.tudelft.gogreen.client;

import javafx.application.Platform;
import nl.tudelft.gogreen.api.API;
import nl.tudelft.gogreen.api.ServerCallback;
import nl.tudelft.gogreen.api.servermodels.BasicResponse;
import nl.tudelft.gogreen.api.servermodels.CompletedActivityServer;
import nl.tudelft.gogreen.api.servermodels.GUICompletedActivities;
import nl.tudelft.gogreen.api.servermodels.User;
import nl.tudelft.gogreen.shared.Shared;
import nl.tudelft.gogreen.shared.models.Badge;
import nl.tudelft.gogreen.shared.models.SubmitResponse;
import nl.tudelft.gogreen.shared.models.SubmittedActivity;

import java.util.ArrayList;
import java.util.List;

public class Client {
    //   static List<UUID> CompletedActivitiesID = new ArrayList<UUID>();
    static List<GUICompletedActivities> a = new ArrayList<>();
    static List<Badge> Badges = new ArrayList<>();
//    static List<Badge> Badges = new ArrayList<>();
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

        API.prepareAPI(false);

//        API.requestFakeStatus(new ServerCallback<Object, BasicResponse>() {
//            @Override
//            public void run() {
//                System.out.println("Found (fake) response: " + getResult().getResponse());
//            }
//        });

        //Get user-profile
//        API.attemptAuthentication(new ServerCallback<Object, BasicResponse>() {
//            @Override
//            public void run() {
//                System.out.println("Logged in");
//
//                API.retrieveUserProfile(new ServerCallback<Object, UserServer>() {
//                    @Override
//                    public void run() {
//                        System.out.println(getResult().getUser().getUsername());
//                        System.out.println(getResult().getPoints());
//                    }
//                });
//                //retrieve badges
//
//                API.retrieveAchievedBadges(new ServerCallback<Object, AchievedBadge[]>() {
//                    @Override
//                    public void run() {
//                        for (AchievedBadge badge : getResult()) {
//                            Badges.add(badge.getBadge());
//                        }
//
//                    }
//                });
//            }
//        }, new User("admin", "password", 0F));

//
//        // Example
//        API.retrieveFakeCo2(new ServerCallback<Object, BasicResponse>() {
//            @Override
//            public void run() {
//                if (getStatusCode() != 200) {
//                    System.out.println("error");
//                } else {
//                    doSomething(getResult());
//                }
//            }
//        });


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
                API.retrieveCompletedActivities(new ServerCallback<Object, CompletedActivityServer[]>() {
                    @Override
                    public void run() {
                        for (CompletedActivityServer activity : getResult()) {
                            a.add(new GUICompletedActivities(activity.getActivity().getActivityName(), activity.getPoints().toString()));
                        }
                        finalActivities(a);
                    }
                });
            }
        }, new User("admin", "password", 0F));


//                API.submitActivity(new ServerCallback<SubmittedActivity, SubmitResponse>() {
//                    @Override
//                    public void run() {
//                        System.out.println("Submitted activity!");
//                        System.out.println(getResult().getExternalId());
//                        System.out.println(getResult().getPoints());
//                        System.out.println(getResult().getUpdatedPoints());
//                        System.out.println(getResult().getResponse());
//                    }
//                }, SubmittedActivity.builder().activityId(1).build());


    }
//
//    private static void retrieveCompletedActivities(List<UUID> completedActivitiesID) {
//        API.attemptAuthentication(new ServerCallback<Object, BasicResponse>() {
//            @Override
//            public void run() {
//                System.out.println("Logged in");
//                API.retrieveCompletedActivities(new ServerCallback<Object,UserCompletedActivity[]>() {
//                    @Override
//                    public void run() {
//                        for (UserCompletedActivity activity : getResult()) {
//                            a.add(new GUICompletedActivities(activity.getActivity().getActivityName(), activity.getPoints() )));
//                        }
//                        finalActivities(a);
//                    }
//                }, completedActivitiesID);
//            }
//        }, new User("admin","password",0F));
//    }

    private static void finalActivities(List<GUICompletedActivities> array) {
        Platform.runLater(() -> {
            for (GUICompletedActivities a : array) {
                System.out.println(a.getActivityname());
                System.out.println(a.getPoints());
            }
        });

    }
}


//    public static void doSomething(BasicResponse basicResponse) {
//        Platform.runLater(() -> {
//            System.out.println("Do something with this value: " + basicResponse.getResponse());
//        });
//    }

