package cs.frame.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Server on 2017/3/14.
 */

public class SharedPreferencesUtils {
    //token
    public final static String TOKEN = "TOKEN";
    //电话
    public final static String TEL = "TEL";
    //是否是子账号登录
    public final static String SON="SON";
    //子账号电话
    public final static String SON_TEL="SON_TEL";
    //订单号
    public final static String ORDERNUM = "ORDERNUM";

    //获取token
    public static String getToken(Context context) {
        Context ctx = context;
        SharedPreferences sp = ctx.getSharedPreferences(TOKEN, Context.MODE_PRIVATE);
        String locationArea = sp.getString(TOKEN, "");
        return locationArea;
    }
    //退出时清空token
    public static  void clearToken(Context context) {
        Context ctx = context;
        SharedPreferences sp = ctx.getSharedPreferences(TOKEN, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear().commit();
    }
    //保存token
    public static void saveToken(Context context, String token) {
        SharedPreferences sp = context.getSharedPreferences(TOKEN, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.putString(TOKEN, token);
        editor.commit();
    }


    //获取电话
    public static String getTel(Context context) {
        Context ctx = context;
        SharedPreferences sp = ctx.getSharedPreferences(TEL, Context.MODE_PRIVATE);
        String tel = sp.getString(TEL, "");
        return tel;
    }
    //退出时清空电话
    public static  void clearTel(Context context) {
        Context ctx = context;
        SharedPreferences sp = ctx.getSharedPreferences(TEL, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear().commit();
    }
    //保存电话
    public static void saveTel(Context context, String tel) {
        SharedPreferences sp = context.getSharedPreferences(TEL, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.putString(TEL, tel);
        editor.commit();
    }

    //获取是否是子账号登录
    public static boolean getSon(Context context) {
        Context ctx = context;
        SharedPreferences sp = ctx.getSharedPreferences(SON, Context.MODE_PRIVATE);
        boolean isSonLogin = sp.getBoolean(SON, false);
        return isSonLogin;
    }
    //退出时清空是否是子账号登录
    public static  void clearSon(Context context) {
        Context ctx = context;
        SharedPreferences sp = ctx.getSharedPreferences(SON, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear().commit();
    }
    //保存是否是子账号登录
    public static void saveSon(Context context, boolean isSon) {
        SharedPreferences sp = context.getSharedPreferences(SON, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.putBoolean(SON, isSon);
        editor.commit();
    }

    //获取子账号电话
    public static String getSonTel(Context context) {
        Context ctx = context;
        SharedPreferences sp = ctx.getSharedPreferences(SON_TEL, Context.MODE_PRIVATE);
        String sonTel = sp.getString(SON_TEL, "");
        return sonTel;
    }
    //退出时清空子账号电话
    public static  void clearSonTel(Context context) {
        Context ctx = context;
        SharedPreferences sp = ctx.getSharedPreferences(SON_TEL, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear().commit();
    }
    //保存子账号电话
    public static void saveSonTel(Context context, String sonTel) {
        SharedPreferences sp = context.getSharedPreferences(SON_TEL, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.putString(SON_TEL, sonTel);
        editor.commit();
    }

    //获取订单号
    public static String getOrderNum(Context context) {
        Context ctx = context;
        SharedPreferences sp = ctx.getSharedPreferences(ORDERNUM, Context.MODE_PRIVATE);
        String tel = sp.getString(ORDERNUM, "");
        return tel;
    }
    //退出时清空订单号
    public static  void clearOrderNum(Context context) {
        Context ctx = context;
        SharedPreferences sp = ctx.getSharedPreferences(ORDERNUM, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear().commit();
    }
    //保存订单号
    public static void saveOrderNum(Context context, String orderNum) {
        SharedPreferences sp = context.getSharedPreferences(ORDERNUM, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.putString(ORDERNUM, orderNum);
        editor.commit();
    }

}
