package com.cnd.zhongkong.languageapp;

import android.app.Application;
import android.util.Log;

import org.litepal.LitePalApplication;
import org.litepal.tablemanager.Connector;

public class LanguageApp extends Application {
    private static final String TAG="LanguageApp:xwg";
    @Override
    public void onCreate() {
        super.onCreate();
//        创建数据库，默认语言随系统
        LitePalApplication.initialize(getApplicationContext());
//        try {
//            Connector.getDatabase();
//            Language language=new Language();
//            language.setLanguage("auto");
//            language.save();//新建
//            Log.i(TAG,"application success");
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.i(TAG,"application error:"+e.toString());
//        }
    }
}
