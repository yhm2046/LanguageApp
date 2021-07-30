package com.cnd.zhongkong.languageapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.cnd.zhongkong.languageapp.databinding.ActivityLoginBinding;
import com.cnd.zhongkong.languageapp.databinding.ActivityMainBinding;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private  final static String TAG="MainActivity:xwg";
    private ActivityMainBinding activityMainBinding=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        activityMainBinding= ActivityMainBinding.inflate(LayoutInflater.from(this));
        setContentView(activityMainBinding.getRoot());
//        build database
        Connector.getDatabase();

        List<Language>list= DataSupport.select("language").find(Language.class);
        if(list.size()<=0){
            Language language=new Language();
            language.setLanguage("auto");
            language.save();//新建
            Log.i(TAG,"application success");
        }else
            Log.i(TAG,"list.size: "+list.size());

        activityMainBinding.btnJump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,MultiplyLanguage.class));
            }
        });
    }
}