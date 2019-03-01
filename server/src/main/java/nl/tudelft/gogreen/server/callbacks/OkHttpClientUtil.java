package nl.tudelft.gogreen.server.callbacks;

import okhttp3.OkHttpClient;

public class OkHttpClientUtil {

    static  OkHttpClient client = null;

    static  OkHttpClient getClient(){
        if(client == null){
            client = new OkHttpClient();
        }
        return client;
    }
}
