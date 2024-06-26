package com.example.app_lotteria.Helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.app_lotteria.Domain.ProductDomain;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ManagerCart {

    private static final String PREFS_NAME = "MyPrefs";
    private static final String KEY_OBJECT_LIST = "listProductInCart";
    private static final String KEY_OBJECT = "productInCart";



    public static void saveObject(Context context, ProductDomain product) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        String json = gson.toJson(product);

        editor.putString(KEY_OBJECT, json);
        editor.apply();
    }

    public static ProductDomain getObject(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String json = sharedPreferences.getString(KEY_OBJECT, "");

        if (json.isEmpty()) {
            return null;
        }

        Gson gson = new Gson();
        return gson.fromJson(json, ProductDomain.class);
    }

    public static void saveObjectList(Context context, ArrayList<ProductDomain> objectList){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // convert object to String
        Gson gson = new Gson();
        String json = gson.toJson(objectList);
        editor.putString(KEY_OBJECT_LIST,json);
        editor.apply();
    }

    public static ArrayList<ProductDomain> getObjectList(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String json = sharedPreferences.getString(KEY_OBJECT_LIST,"");

        if (json.isEmpty()) {
            return  new ArrayList<>();
        }

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<ProductDomain>>() {}.getType();

        return gson.fromJson(json, type);

    }


}
