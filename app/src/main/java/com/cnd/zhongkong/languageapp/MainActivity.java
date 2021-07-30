package com.cnd.zhongkong.languageapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.cnd.zhongkong.languageapp.databinding.ActivityLoginBinding;
import com.cnd.zhongkong.languageapp.databinding.ActivityMainBinding;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private  final static String TAG="MainActivity:xwg";
    private ActivityMainBinding activityMainBinding=null;
    public Bundle s;

    @Override
    protected void onResume() {
        super.onResume();
//        onCreate(null);
        //        从数据库读取设置的语言并重绘语言显示
//        try {
//            List<Language> list= DataSupport.select("language").find(Language.class);
//            String strLanguage=list.get(0).getLanguage();
//            Log.i(TAG,"strLanguage: "+strLanguage);
//            Locale myLocale = new Locale(strLanguage);
//            Resources res = getResources();
//            DisplayMetrics dm = res.getDisplayMetrics();
//            Configuration conf = res.getConfiguration();
//            conf.locale = myLocale;
//            res.updateConfiguration(conf, dm);
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.i(TAG,"set language error: "+e.toString());
//        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onRestart() {
//        this.recreate();
        super.onRestart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        this.s=savedInstanceState;
        activityMainBinding= ActivityMainBinding.inflate(LayoutInflater.from(this));
        setContentView(activityMainBinding.getRoot());
//        build database
        buildDatabase();
//        get system language
        String defaultLan= Locale.getDefault().getLanguage();
        Log.i(TAG,"now defaultLan:"+defaultLan);
        activityMainBinding.btnJump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,MultiplyLanguage.class));
            }
        });
    }


    private void buildDatabase() {
        try {
            Connector.getDatabase();
            List<Language> list= DataSupport.select("language").find(Language.class);
            if(list.size()<=0){
                Language language=new Language();
                language.setLanguage("auto");
                language.save();//新建
                Log.i(TAG,"application success");
            }else {
                Log.i(TAG, "list.size: " + list.size());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG,"buildDatabase error: "+e.toString());
        }
    }
}