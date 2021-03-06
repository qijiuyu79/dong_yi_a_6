package com.zxdc.utils.library.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

import com.google.gson.Gson;

import java.util.List;

public class SPUtil {

    private SharedPreferences shar;
    private Editor editor;
    public final static String USERMESSAGE = "dong_yi_ri_sheng";
    //是否是第一次打开APP
    public final static String IS_FIRST_OPEN="IS_FIRST_OPEN";
    //当前的经纬度
    public final static String LAT="LAG";
    public final static String LONG="LONG";
    //用户所在的城市
    public final static String LOCATION_CITY="LOCATION_CITY";
    //当前的城市名称
    public final static String CITY="CITY";
    //分站id
    public final static String SITEID="SITEID";
    //登录的token
    public final static String TOKEN="TOKEN";
    //用户id
    public final static String USER_ID="USER_ID";
    //微信登录的openid
    public final static String OPEN_ID="OPEN_ID";
    //登录账号
    public final static String ACCOUNT="ACCOUNT";
    //登录密码
    public final static String PASSWORD="PASSWORD";
    //是否首次进入首页
    public final static String ISOPENMAIN="ISOPENMAIN";
    //是否首次进入案例详情页面
    public final static String IS_OPEN_CASE_DETAIILS="IS_OPEN_CASE_DETAIILS";
    //是否首次进入设计师详情页面
    public final static String IS_OPEN_DESIGNER_DETAILS="IS_OPEN_DESIGNER_DETAILS";
    //搜索的关键字
    public final static String SEARCH_KEY="SEARCH_KEY";
    //点击过的历史城市
    public final static String HISTORY_CITY="HISTORY_CITY";
    //存储后台最新版本信息
    public final static String VERSION_CODE="VERSION_CODE";
    //首页的活动id
    public final static String ACTIVITY_ID="ACTIVITY_ID";
    //客服电话
    public final static String TEL="TEL";
    //推送
    public final static String JPUSH="JPUSH";
    private static SPUtil sharUtil = null;
    public static Gson gson=new Gson();
    private SPUtil(Context context, String sharname) {
        shar = context.getSharedPreferences(sharname, Context.MODE_PRIVATE + Context.MODE_APPEND);
        editor = shar.edit();
    }

    public static SPUtil getInstance(Context context) {
        if (null == sharUtil) {
            sharUtil = new SPUtil(context, USERMESSAGE);
        }
        return sharUtil;
    }


    //添加String信息
    public void addString(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    //添加int信息
    public void addInt(String key, Integer value) {
        editor.putInt(key, value);
        editor.commit();
    }

    //添加boolean信息
    public void addBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    //添加float信息
    public void addFloat(String key, float value) {
        editor.putFloat(key, value);
        editor.commit();
    }

    //添加long信息
    public void addLong(String key, long value) {
        editor.putLong(key, value);
        editor.commit();
    }

    //添加list
    public void addList(String key, List<Object> list) {
        editor.putString(key, gson.toJson(list));
        LogUtils.e(gson.toJson(list));
        editor.commit();
    }


    public void addObject(String key,Object object){
        editor.putString(key,gson.toJson(object));
        editor.commit();
    }


    public Object getObject(String key,Class myClass){
        final String value=shar.getString(key,null);
        if(!TextUtils.isEmpty(value)){
            return gson.fromJson(value,myClass);
        }
        return null;
    }


    public void removeMessage(String delKey) {
        editor.remove(delKey);
        editor.commit();
    }

    public void removeAll() {
        editor.clear();
        editor.commit();
    }

    public String getString(String key) {
        return shar.getString(key, "");
    }

    public Integer getInteger(String key) {
        return shar.getInt(key, 0);
    }

    public boolean getBoolean(String key) {
        return shar.getBoolean(key, false);
    }

    public float getFloat(String key) {
        return shar.getFloat(key, 0);
    }

    public long getLong(String key) {
        return shar.getLong(key, 0);
    }

}
