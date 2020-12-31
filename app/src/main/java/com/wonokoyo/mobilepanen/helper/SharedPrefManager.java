package com.wonokoyo.mobilepanen.helper;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    public static final String SP_PANEN = "spPanenApp";
    public static final String SP_LOGIN = "spLogin";
    public static final String SP_USER = "spUser";
    public static final String SP_NAME = "spName";

    SharedPreferences sp;
    SharedPreferences.Editor spEditor;

    public SharedPrefManager(Context context) {
        sp = context.getSharedPreferences(SP_PANEN, Context.MODE_PRIVATE);
        spEditor = sp.edit();
    }

    public void saveSPString(String key, String value) {
        spEditor.putString(key, value);
        spEditor.commit();
    }

    public void saveSPBoolean(String key, Boolean value) {
        spEditor.putBoolean(key, value);
        spEditor.commit();
    }

    public Boolean getLogin() {
        return sp.getBoolean(SP_LOGIN, false);
    }

    public String getUser() {
        return sp.getString(SP_USER, "");
    }

    public String getName() {
        return sp.getString(SP_NAME, "");
    }
}
