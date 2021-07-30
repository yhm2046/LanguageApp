package com.cnd.zhongkong.languageapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.DisplayMetrics;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.litepal.crud.DataSupport;

import java.util.List;
import java.util.Locale;

public class ActivitySetLanguage extends AppCompatActivity {
    private static final String TAG="ActivitySetLanguage:xwg";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

    }

    @Override
    protected void onResume() {
        super.onResume();
        //        从数据库读取设置的语言并重绘语言显示
        try {
            List<Language> list= DataSupport.select("language").find(Language.class);
            String strLanguage=list.get(0).getLanguage();
            Log.i(TAG,"strLanguage: "+strLanguage);
            Locale myLocale = new Locale(strLanguage);
            Resources res = getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            conf.locale = myLocale;
            res.updateConfiguration(conf, dm);
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG,"set language error: "+e.toString());
        }
    }
}
