package com.example.englishapp;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class APIWorker {
    private static APIWorker mInstance;
    private static final String BASE_URL = "http://df87cc15348d.ngrok.io/api/";
    private Retrofit mRetrofit;

    private APIWorker() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static APIWorker getInstance() {
        if (mInstance == null) {
            mInstance = new APIWorker();
        }
        return mInstance;
    }

    public JSONApi getJSONApi() {
        return mRetrofit.create(JSONApi.class);
    }
}
