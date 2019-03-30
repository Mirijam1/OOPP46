package nl.tudelft.gogreen.api;

import nl.tudelft.gogreen.api.servermodels.Activity;
import nl.tudelft.gogreen.api.servermodels.BasicResponse;
import nl.tudelft.gogreen.api.servermodels.Category;
import nl.tudelft.gogreen.api.servermodels.User;
import nl.tudelft.gogreen.shared.StatusCodes;
import nl.tudelft.gogreen.shared.models.SubmitResponse;
import nl.tudelft.gogreen.shared.models.SubmittedActivity;
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
    }

    @Test
    public void checkUrl() {
        assertEquals("http://localhost:8088/login", API.buildUrl(EndPoints.LOGIN));
    }

    @Test
    public void CheckRequestStatus() {
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
    public void requestFakeStatus() {
        API.requestFakeStatus(new ServerCallback<Object, BasicResponse>() {
            @Override
            public void run() {
                assertEquals(200,getStatusCode());
            }
        });
    }

    @Test
    public void attemptAdminAuthentication() {
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
    public void submitActivity() {
        API.attemptAuthentication(new ServerCallback<Object, BasicResponse>() {
            @Override
            public void run() {
                System.out.println("Logged in");

                API.submitActivity(new ServerCallback<SubmittedActivity, SubmitResponse>() {
                    @Override
                    public void run() {
                        System.out.println("Submitted activity!");
                        assertTrue(getResult().getExternalId()!=null);
                        assertTrue(getResult().getPoints()!=null);
                        assertTrue(getResult().getUpdatedPoints()!=null);
                        assertTrue(getResult().getResponse()!=null);
                    }
                }, SubmittedActivity.builder().activityId(1).build());
            }
        }, new User("admin", "password", 0F));
    }

    @Test
    public void retrieveCategoryList() {
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
    public void retrieveActivityList() {
        API.retrieveActivityList(new ServerCallback<Object, Activity[]>() {
            @Override
            public void run() {
                List<Activity> activitiesReal = Arrays.asList(new Activity(1, "Eating a veg meal", "description", new Category("1", "Food", "Food items")));
                List<Activity> activities = Arrays.stream(getResult()).collect(Collectors.toList());
                assertEquals(activitiesReal, activities);
            }
        }, "Food");
    }

    @Test
    public void retrieveFakeCo2() {
        API.retrieveFakeCo2(new ServerCallback<Object, BasicResponse>() {
            @Override
            public void run() {
                assertEquals(200, getStatusCode());
            }
        });
    }
}