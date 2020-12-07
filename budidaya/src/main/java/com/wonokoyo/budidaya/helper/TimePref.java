package com.wonokoyo.budidaya.helper;

import android.content.Context;
import android.content.SharedPreferences;

public class TimePref {
    public static final String TM_PANEN = "tmPanenApp";
    public static final String TM_MULAI = "tmMulai";
    public static final String TM_SELESAI = "tmSelesai";

    SharedPreferences sp;
    SharedPreferences.Editor spEditor;

    public TimePref(Context context) {
        sp = context.getSharedPreferences(TM_PANEN, Context.MODE_PRIVATE);
        spEditor = sp.edit();
    }

    public void saveSPString(String key, String value) {
        spEditor.putString(key, value);
        spEditor.commit();
    }

    public String getMulai() {
        return sp.getString(TM_MULAI, "0000-00-00 00:00:00");
    }

    public String getSelesai() {
        return sp.getString(TM_SELESAI, "0000-00-00 00:00:00");
    }
}
