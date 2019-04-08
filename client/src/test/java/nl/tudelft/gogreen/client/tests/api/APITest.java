package nl.tudelft.gogreen.client.tests.api;

import nl.tudelft.gogreen.api.API;
import nl.tudelft.gogreen.api.EndPoints;
import nl.tudelft.gogreen.api.ServerCallback;
import nl.tudelft.gogreen.api.servermodels.AchievedBadge;
import nl.tudelft.gogreen.api.servermodels.BasicResponse;
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
        }, new User("admin", "password", null, 0F, null));
    }

    @Test
    public void testcheckUrl() {
        assertEquals("https://oopp.timanema.net/login", API.buildUrl(EndPoints.LOGIN));
    }


    @Test
    public void testcheckUrlTrue() {
        API.prepareAPI(true);
        assertEquals("http://localhost:8088/login", API.buildUrl(EndPoints.LOGIN));
        API.closeAPI();
    }

    @Test
    public void testcheckVarUrl() {
        assertEquals("https://oopp.timanema.net/api/profile/activities/vegmeal", API.buildUrl(EndPoints.GET_SPECIFIC_ACTIVITY, "vegmeal"));
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
        }, new User("admin", "password", null, (float) 0, null));

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
        testAttemptAdminAuthentication();
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
        testAttemptAdminAuthentication();
        API.retrieveCategoryList(new ServerCallback<Object, Category[]>() {
            @Override
            public void run() {
                List<Category> categoriesReal = Arrays.asList(new Category("1", "food", "food items"), new Category("2", "Transport", "transportation"), new Category("3", "Energy", "Energy items"), new Category("4", "misc", "Other items"));
                List<Category> categories = Arrays.stream(getResult()).collect(Collectors.toList());
                assertEquals(categoriesReal, categories);
            }
        });
    }

    @Test
    public void testRetrieveActivityList() {
        testAttemptAdminAuthentication();
        API.retrieveActivityList(new ServerCallback<Object, Activity[]>() {
            @Override
            public void run() {
                List<Activity> activitiesReal = Arrays.asList(new Activity(0, "Vegetarian Meal", "Eating a veg meal", null, null));
                List<Activity> activities = Arrays.stream(getResult()).collect(Collectors.toList());
                assertEquals(activitiesReal, activities);
            }
        }, "food");
    }

    @Test
    public void testRetrieveCompletedActivities() {
        testAttemptAdminAuthentication();
        API.retrieveCompletedActivities(new ServerCallback<Object, CompletedActivity[]>() {
            @Override
            public void run() {
                assertTrue(getRequest() != null);
                assertTrue(getResult() != null);
            }
        });
    }

    @Test
    public void testRetrieveActivityById() {
        testAttemptAdminAuthentication();
        API.retrieveActivityById(new ServerCallback<Object, Activity>() {
            @Override
            public void run() {
                assertTrue(getResult() != null);
            }
        }, 1);
    }

    @Test
    public void testRetrieveUserProfile() {
        testAttemptAdminAuthentication();
        API.retrieveUserProfile(new ServerCallback<Object, UserServer>() {
            @Override
            public void run() {
                assertTrue(getResult() != null);
            }
        });
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
                }, new User("testuser", "password", null, 0F, null));
            }
        }, new User("testuser", "password", null, 0F, null));
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
                }, new User("testuser", "pass", null, 0F, null));
            }
        }, new User("testuser", "pass", null, 0F, null));
    }

    @Test
    public void testRetrieveUser() {
        API.attemptAuthentication(new ServerCallback<Object, BasicResponse>() {
            @Override
            public void run() {
                System.out.println("Logged in");
                API.retrieveUser(new ServerCallback<Object, UserServer>() {
                    @Override
                    public void run() {
                        assertEquals("testuser", getResult().getUser());
                        assertEquals("0F",getResult().getPoints());
                    }
                });
            }
        }, new User("testuser", "pass", null,0F, null));
    }

    @Test
    public void testRetrieveAchievedBadges() {
        testAttemptAdminAuthentication();
        API.retrieveAchievedBadges(new ServerCallback<Object, AchievedBadge[]>() {
            @Override
            public void run() {
                assertTrue(getResponse() != null);
                assertTrue(getResult() != null);
            }
        });
    }


    @Test
    public void testRetrieveGlobalLeaderboard() {
        testAttemptAdminAuthentication();
        API.retrieveGlobalLeaderboard(new ServerCallback<Object, UserServer[]>() {
            @Override
            public void run() {
                assertTrue(getResult() != null);
            }
        });
    }

    @Test
    public void testRetrieveFriendsLeaderboard() {
        testAttemptAdminAuthentication();
        API.retrieveFriendsLeaderboard(new ServerCallback<Object, UserServer[]>() {
            @Override
            public void run() {
                assertTrue(getResult() != null);
            }
        });
    }

    @Test
    public void retrieveFriends() {
        testAttemptAdminAuthentication();
        API.retrieveFriends(new ServerCallback<Object, Friendship[]>() {
            @Override
            public void run() {
                assertTrue(getResult() != null);
            }
        });
    }

    @Test
    public void addFriend() {
        testAttemptAdminAuthentication();
        API.addFriend(new ServerCallback<Object, Friendship>() {
            @Override
            public void run() {
                assertTrue(getResult() != null);
            }
        }, "gogreenuser");
    }

    @Test
    public void retrievePendingReceivedFriendRequests() {
        testAttemptAdminAuthentication();
        API.retrievePendingReceivedFriendRequests(new ServerCallback<Object, Friendship[]>() {
            @Override
            public void run() {
                assertTrue(getResult() != null);
            }
        });
    }

    @Test
    public void searchUserProfiles() {
        testAttemptAdminAuthentication();
        API.searchUserProfiles(new ServerCallback<Object, UserServer>() {
            @Override
            public void run() {
                assertTrue(getResult() != null);
            }
        }, "gogreenuser");
    }

    @Test
    public void retrievePendingSentFriendRequests() {
        testAttemptAdminAuthentication();
        API.retrievePendingSentFriendRequests(new ServerCallback<Object, Friendship[]>() {
            @Override
            public void run() {
                assertTrue(getResult() != null);
            }
        });
    }

    @Test
    public void retrieveFriendActivities() {
        testAttemptAdminAuthentication();
        API.retrieveFriendActivities(new ServerCallback<Object, CompletedActivity[]>() {
            @Override
            public void run() {
                assertTrue(getResult() != null);
            }
        });
    }

}