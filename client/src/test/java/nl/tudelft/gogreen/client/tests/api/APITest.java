package nl.tudelft.gogreen.client.tests.api;

import nl.tudelft.gogreen.api.API;
import nl.tudelft.gogreen.api.EndPoints;
import nl.tudelft.gogreen.api.ServerCallback;
import nl.tudelft.gogreen.api.servermodels.AchievedBadge;
import nl.tudelft.gogreen.api.servermodels.BasicResponse;
import nl.tudelft.gogreen.api.servermodels.CompletedActivityServer;
import nl.tudelft.gogreen.api.servermodels.User;
import nl.tudelft.gogreen.shared.StatusCodes;
import nl.tudelft.gogreen.shared.models.*;
import nl.tudelft.gogreen.shared.models.social.Friendship;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class APITest {

    @Before
    public void init() {
        API.prepareAPI(false);
        API.attemptAuthentication(new ServerCallback<Object, BasicResponse>() {
            @Override
            public void run() {
                testCheckRequestStatus();
                testAttemptAdminAuthentication();
                testSubmitActivity();
                testRetrieveCompletedActivities();
                testRetrieveAchievedBadges();
                testRetrieveFriendsLeaderboard();
            }
        }, new User("admin", "password", 0F));
    }

    @Test
    public void testcheckUrl() {
        assertEquals("https://oopp.timanema.net/login", API.buildUrl(EndPoints.LOGIN));
    }


    @Test
    public void testCheckRequestStatus() {
        API.requestStatus(new ServerCallback<Object, BasicResponse>() {
            @Override
            public void run() {
                if (getResponse().getStatus() == StatusCodes.UNAUTHORIZED) {
                    assertEquals(getResponse().getStatus(), 401);
                } else {
                    assertEquals(getResponse().getStatus(), 200);
                }
            }
        }, EndPoints.STATUS_USER);

    }

    @Test
    public void testAttemptAdminAuthentication() {
        API.attemptAuthentication(new ServerCallback<Object, BasicResponse>() {
            @Override
            public void run() {
                if (getResponse().getStatus() == StatusCodes.AUTHENTICATED) {
                    assertEquals(getResponse().getStatus(), 200);
                }

            }
        }, new User("admin", "password", (float) 0));

    }

    @Test
    public void testFakeStatus() {
        API.requestFakeStatus(new ServerCallback<Object, BasicResponse>() {
            @Override
            public void run() {
                assertTrue(getStatusCode() == 200);
            }
        });
    }


    @Test
    public void testFakeCO2() {
        API.retrieveFakeCo2(new ServerCallback<Object, BasicResponse>() {
            @Override
            public void run() {
                assertTrue(getResult() != null);
            }
        });
    }

    @Test
    public void testFakeUser() {
        API.retrieveFakeUser(new ServerCallback<Object, User>() {
            @Override
            public void run() {
                assertTrue(getResult() != null);
            }
        });
    }

    @Test
    public void testSubmitActivity() {
        API.submitActivity(new ServerCallback<SubmittedActivity, SubmitResponse>() {
            @Override
            public void run() {
                System.out.println("Submitted activity!");
                assertTrue(getResult() != null);
            }
        }, SubmittedActivity.builder().activityId(1).build());
    }

    @Test
    public void testRetrieveCategoryList() {
        API.retrieveCategoryList(new ServerCallback<Object, Category[]>() {
            @Override
            public void run() {
                List<Category> categoriesReal = Arrays.asList(new Category("1", "Food", "Food items"), new Category("2", "Transport", "Transportation"), new Category("3", "Energy", "Energy items"), new Category("4", "Misc", "Other items"));
                List<Category> categories = Arrays.stream(getResult()).collect(Collectors.toList());
                assertEquals(categoriesReal, categories);
            }
        });
    }

    @Test
    public void testRetrieveActivityList() {
        API.retrieveActivityList(new ServerCallback<Object, Activity[]>() {
            @Override
            public void run() {
                List<Activity> activitiesReal = Arrays.asList(new Activity(0, "Vegetarian Meal", "Eating a veg meal", null));
                List<Activity> activities = Arrays.stream(getResult()).collect(Collectors.toList());
                assertEquals(activitiesReal, activities);
            }
        }, "Food");
    }

    @Test
    public void testRetrieveCompletedActivities() {
        API.attemptAuthentication(new ServerCallback<Object, BasicResponse>() {
            @Override
            public void run() {
                API.retrieveCompletedActivities(new ServerCallback<Object, CompletedActivityServer[]>() {
                    @Override
                    public void run() {
                        assertTrue(getResult() != null);
                    }
                });
            }
        }, new User("admin", "password", (float) 0));

    }

    @Test
    public void testCreateUser() {
        API.createUser(new ServerCallback<User, User>() {
            @Override
            public void run() {
                API.attemptAuthentication(new ServerCallback<Object, BasicResponse>() {
                    @Override
                    public void run() {
                        System.out.println("Logged in");
                        API.retrieveUser(new ServerCallback<Object, UserServer>() {
                            @Override
                            public void run() {
                                assertEquals("testuser", getResult().getUser());
                            }
                        });
                    }
                }, new User("testuser", "password", 0F));
            }
        }, new User("testuser", "password", 0F));
    }

    @Test
    public void testUpdateUser() {
        API.updateUser(new ServerCallback<User, User>() {
            @Override
            public void run() {
                API.attemptAuthentication(new ServerCallback<Object, BasicResponse>() {
                    @Override
                    public void run() {
                        System.out.println("Logged in");
                        API.retrieveUser(new ServerCallback<Object, UserServer>() {
                            @Override
                            public void run() {
                                assertEquals("testuser", getResult().getUser());
                            }
                        });
                    }
                }, new User("testuser", "pass", 0F));
            }
        }, new User("testuser", "pass", 0F));
    }

    @Test
    public void testRetrieveAchievedBadges() {


        API.attemptAuthentication(new ServerCallback<Object, BasicResponse>() {
            @Override
            public void run() {
                System.out.println("Logged in");
                API.retrieveAchievedBadges(new ServerCallback<Object, AchievedBadge[]>() {
                    @Override
                    public void run() {
                        assertTrue(getResult() != null);
                    }
                });
            }
        }, new User("admin", "password", 0F));
    }


    @Test
    public void testRetrieveGlobalLeaderboard() {
        API.retrieveGlobalLeaderboard(new ServerCallback<Object, UserServer[]>() {
            @Override
            public void run() {
                assertTrue(getResult() != null);
            }
        });
    }

    @Test
    public void testRetrieveFriendsLeaderboard() {
        API.retrieveFriendsLeaderboard(new ServerCallback<Object, UserServer[]>() {
            @Override
            public void run() {
                assertTrue(getResult() != null);
            }
        });
    }

    @Test
    public void retrieveFriends() {
        API.retrieveFriends(new ServerCallback<Object, Friendship[]>() {
            @Override
            public void run() {
                assertTrue(getResult() != null);
            }
        });
    }

    @Test
    public void addFriend() {
        API.addFriend(new ServerCallback<Object, Friendship[]>() {
            @Override
            public void run() {
                assertTrue(getResult() != null);
            }
        }, "gogreenuser");
    }

    @Test
    public void retrievePendingReceivedFriendRequests() {
        API.retrievePendingReceivedFriendRequests(new ServerCallback<Object, Friendship[]>() {
            @Override
            public void run() {
                assertTrue(getResult() != null);
            }
        });
    }

    @Test
    public void searchUserProfiles() {
        API.searchUserProfiles(new ServerCallback<Object, UserServer>() {
            @Override
            public void run() {
                assertTrue(getResult() != null);
            }
        }, "gogreenuser");
    }

    @Test
    public void retrievePendingSentFriendRequests() {
        API.retrievePendingSentFriendRequests(new ServerCallback<Object, Friendship[]>() {
            @Override
            public void run() {
                assertTrue(getResult() != null);
            }
        });
    }

    @Test
    public void retrieveFriendActivities() {
        API.retrieveFriendActivities(new ServerCallback<Object, CompletedActivity[]>() {
            @Override
            public void run() {
                assertTrue(getResult() != null);
            }
        });
    }

}