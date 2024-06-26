package com.example.app_lotteria.Helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.app_lotteria.Domain.ProductDomain;
import com.example.app_lotteria.Domain.User;
import com.google.gson.Gson;

public class ManagerUser {

    private static final String PREFS_NAME = "MyPrefs";
    private static final String KEY_OBJECT = "User";

    public static void saveObject(Context context, User user) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        String json = gson.toJson(user);

        editor.putString(KEY_OBJECT, json);
        editor.apply();
    }

    public static User getObject(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String json = sharedPreferences.getString(KEY_OBJECT, "");

        if (json.isEmpty()) {
            return null;
        }

        Gson gson = new Gson();
        return gson.fromJson(json, User.class);
    }
}
