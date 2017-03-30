package com.example.xdcao.diandiapp.UI.songwenqiang.utils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by wewarrios on 2017/2/17.
 */

public class MyOkhttp {
    public static OkHttpClient client = new OkHttpClient();
    public static String get(String url)  {
        client.newBuilder().connectTimeout(10000, TimeUnit.MILLISECONDS);
        Request request = new Request.Builder().url(url).build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
            if(response.isSuccessful()){
                return response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
        return null;

    }
}
