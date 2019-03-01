package nl.tudelft.gogreen.server.callbacks;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import nl.tudelft.gogreen.server.models.Activity;
import nl.tudelft.gogreen.server.models.Category;
import okhttp3.Request;
import okhttp3.Response;

import java.lang.reflect.Type;
import java.util.List;

public class CategoryAPI {


    public static List<Category> categorycall() throws Exception {
        //Create Request
        Request request = new Request.Builder()
            .url("http://localhost:8088/api/category/categories")
            .build();
        Response response = OkHttpClientUtil.getClient().newCall(request).execute();
        //System.out.println(" Data " + response.body().string());
        Type CategoryType = new TypeToken<List<Category>>() {
        }.getType(); // Create Type Object for parse
        List<Category> categoriesList = new Gson().fromJson(response.body().string(), CategoryType); // Gson case list response json with type for case
        return categoriesList;
//            for (Category category : categoriesList) {
//                System.out.println("Name :" + category.getCategoryName());
//            }
//
//        } catch (Exception e) {
//            System.out.println("Error " + e.getLocalizedMessage());
//        }
    }
    public static List<Activity> activitycall() throws Exception {
        //Create Request
        Request request = new Request.Builder()
            .url("http://localhost:8088/api/activity/activities")
            .build();
        Response response = OkHttpClientUtil.getClient().newCall(request).execute();
        //System.out.println(" Data " + response.body().string());
        Type ActivityType = new TypeToken<List<Activity>>() {
        }.getType(); // Create Type Object for parse
        List<Activity> activitiesList = new Gson().fromJson(response.body().string(), ActivityType); // Gson case list response json with type for case
        return activitiesList;
    }


//HttpResponse<JsonNode> jsonResponse = Unirest.post("http://httpbin.org/post")
//  .header("accept", "application/json")
//  .queryString("apiKey", "123")
//  .field("parameter", "value")
//  .field("foo", "bar")
//  .asJson();
}

