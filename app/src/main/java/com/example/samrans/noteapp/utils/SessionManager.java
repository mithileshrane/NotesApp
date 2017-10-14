package com.example.samrans.noteapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;


/**
 * Created by infiny on 28/6/17.
 */

public class SessionManager {

    private static final String KEY_STATUS = "status";
    private static final String REFRESH_TOKEN = "refreshtoken";


    private static final String KEY_LOGIN_DATA = "loginData";
    private String TAG = SessionManager.class.getSimpleName();
    private static final String IS_LOGIN = "IsLoggedIn";

    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "data";


    // Constructor
    public SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

//    public boolean isLoggedIn() {
//        return pref.getBoolean(IS_LOGIN, false);
//    }
//    public Login getUser() {
//        Login login;
//        Gson gson = new Gson();
//        String json = pref.getString(KEY_LOGIN_DATA, "");
////        MyObject obj = gson.fromJson(json, Duration.class);
//        Type collectionType = new TypeToken<Login>(){}.getType();
//        login = gson.fromJson(json, collectionType);
//        if (login!=null)
//            return login;
//        return null;
//    }

    public boolean getStatus(){
        return pref.getBoolean(KEY_STATUS,false);
    }
    public void clear() {
        editor.clear();
        editor.commit();
    }
    public void clearLogin() {
        editor.remove(IS_LOGIN);
        editor.apply();
    }
    public void clearLoginUser() {
        editor.remove(KEY_LOGIN_DATA);
        editor.apply();
    }
//    public void setLoginData(Login login) {
//        Gson gson = new Gson();
//        String json = gson.toJson(login); // myObject - instance of MyObject
//        editor.putString(KEY_LOGIN_DATA, json);
//        editor.putBoolean(IS_LOGIN, true);
//        editor.commit();
//    }

    public void setStatus(boolean message){
        editor.putBoolean(KEY_STATUS, message);
        editor.commit();
    }

    public void saveToken(String refreshedToken) {
        editor.putString(REFRESH_TOKEN,refreshedToken);
        editor.commit();
    }

    public String getRefreshToken() {
        return pref.getString(REFRESH_TOKEN,null);
    }
}
