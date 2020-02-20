package com.ylean.dyspd.utils;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.umeng.analytics.MobclickAgent;
import com.ylean.dyspd.activity.init.LoginActivity;
import com.zxdc.utils.library.http.HttpMethod;
import com.zxdc.utils.library.util.LogUtils;
import com.zxdc.utils.library.util.SPUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 埋点
 * Created by Administrator on 2020/2/18.
 */

public class PointUtil {

    private static PointUtil pointUtil;

    public static PointUtil getInstent(){
        if(pointUtil==null){
            pointUtil=new PointUtil();
        }
        return pointUtil;
    }

    /**
     * 收藏埋点
     * @param type
     */
    public static void collPoint(Context context,int type){
        switch (type){
            //收藏图库
            case 1:
                MobclickAgent.onEvent(context, "coll_pic");
                break;
            //收藏VR
            case 2:
                MobclickAgent.onEvent(context, "coll_vr");
                break;
            //软装范本
            case 7:
                MobclickAgent.onEvent(context, "coll_models");
                break;
            //风格案例
            case 12:
                MobclickAgent.onEvent(context, "coll_case");
                break;
            //收藏装修攻略
            case 3:
                MobclickAgent.onEvent(context, "coll_news");
                break;
            //收藏体验店
            case 4:
                MobclickAgent.onEvent(context, "coll_shop");
                break;
            //收藏在施工地
            case 5:
                MobclickAgent.onEvent(context, "coll_site");
                break;
            //收藏楼盘
            case 6:
                MobclickAgent.onEvent(context, "coll_house");
                break;
            //收藏设计师
            case 8:
                MobclickAgent.onEvent(context, "coll_designer");
                break;
            default:
                break;
        }
    }


    /**
     * 微信或手机注册埋点
     * @param context
     */
    public static void registerPoint(Context context,int type){
        //手机号注册
        if(type==1){
            MobclickAgent.onEvent(context, "mobile_register");
        }else{
            MobclickAgent.onEvent(context, "wx_register");
        }
    }


    /**
     * 报名埋点
     * @param context
     */
    public static void respokePoint(Context context){
        final String token = SPUtil.getInstance(context).getString(SPUtil.TOKEN);
        //未注册报名
        if (TextUtils.isEmpty(token)) {
            MobclickAgent.onEvent(context, "not_register_apply");
        } else {
            MobclickAgent.onEvent(context, "yes_register_apply");
        }
    }


    /**
     * 页面埋点
     * @param context
     * @param type
     */
    public static void pagePoint(Context context,int type){
        switch (type){
            //设计师
            case 1:
                MobclickAgent.onEvent(context, "browse_designer");
                break;
            //体验店
            case 2:
                MobclickAgent.onEvent(context, "browse_shop");
                break;
            //风格案例
            case 3:
                MobclickAgent.onEvent(context, "browse_case");
                break;
            //案例图库
            case 4:
                MobclickAgent.onEvent(context, "browse_pic");
                break;
            //VR样板房
            case 5:
                MobclickAgent.onEvent(context, "browse_vr");
                break;
            //软装案例
            case 6:
                MobclickAgent.onEvent(context, "browse_models");
                break;
            //热装楼盘
            case 7:
                MobclickAgent.onEvent(context, "browse_house");
                break;
            //实景工地
            case 8:
                MobclickAgent.onEvent(context, "browse_site");
                break;
            //攻略
            case 9:
                MobclickAgent.onEvent(context, "browse_news");
                break;
            default:
                break;
        }
    }
}
