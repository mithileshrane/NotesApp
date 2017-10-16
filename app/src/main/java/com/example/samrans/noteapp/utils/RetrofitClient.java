package com.example.samrans.noteapp.utils;

import android.content.Context;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class RetrofitClient {
    private  static  boolean isOnline;
    private  Context mContext;
    private boolean isAuthorization;
    private Retrofit retrofit;

    private String email;
    private String password;
    private String token;
    String TAG=RetrofitClient.class.getSimpleName();

    public RetrofitClient(Context mContext) {
        this.mContext=mContext;
        isOnline= CommonUtils.isOnline(mContext);
    }


    OkHttpClient okHttpClient = new OkHttpClient().newBuilder().addInterceptor(new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();
            Request.Builder builder;
            if (isAuthorization) {
                builder = originalRequest.newBuilder().header("Authorization",
                        Credentials.basic(email, password));
            } else {
                builder = originalRequest.newBuilder().header("token",
                        token);
            }

            Request newRequest = builder.build();
            return chain.proceed(newRequest);
        }
    }).build();




    public Retrofit getBlankRetrofit(){

        File httpCacheDirectory = new File(mContext.getCacheDir(),  "responses");
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(httpCacheDirectory, cacheSize);

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(REWRITE_RESPONSE_INTERCEPTOR)
                .addInterceptor(OFFLINE_INTERCEPTOR)
                .cache(cache)
                .build();

        retrofit = new Retrofit.Builder()
//                .baseUrl("http://192.168.1.171:3000/api/")
                .baseUrl("http://dev2.infiny.in:3000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();

        return retrofit;

    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public RetrofitClient(RetrofitClientBuilder builder) {

        this.isAuthorization = builder.isAuthorization;
        this.email = builder.email;
        this.password = builder.password;
        this.token = builder.token;

        retrofit = new Retrofit.Builder()
                .baseUrl("http://dev2.infiny.in:3090")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }


    private static final Interceptor REWRITE_RESPONSE_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Response originalResponse = chain.proceed(chain.request());
            String cacheControl = originalResponse.header("Cache-Control");

            if (cacheControl == null || cacheControl.contains("no-store") || cacheControl.contains("no-cache") ||
                    cacheControl.contains("must-revalidate") || cacheControl.contains("max-age=0")) {
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, max-age=" + 10)
                        .build();
            } else {
                return originalResponse;
            }
        }
    };


    private static final Interceptor OFFLINE_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            if (!isOnline) {

                int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
                request = request.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }

            return chain.proceed(request);
        }
    };


    public static class RetrofitClientBuilder {

        // required parameters
        private String email;
        private String password;
        private boolean isAuthorization;
        private String token;

        public RetrofitClientBuilder(String email, String password) {
            this.email = email;
            this.password = password;
        }

        public RetrofitClientBuilder(String token) {
            this.token = token;
        }

        public RetrofitClientBuilder() {
        }

        public RetrofitClientBuilder setAuthorization(boolean isAuthorization) {
            this.isAuthorization = isAuthorization;
            return this;
        }

        public RetrofitClient build() {
            return new RetrofitClient(this);
        }

    }
}

