package com.cnd.zhongkong.languageapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cnd.zhongkong.languageapp.databinding.ActivityLoginBinding;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Locale;

public class TestLanguage extends AppCompatActivity {
    private  final static String TAG="TestLanguage:xwg";
    private ActivityLoginBinding activityLoginBinding;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoginBinding=ActivityLoginBinding.inflate(LayoutInflater.from(this));
        setContentView(activityLoginBinding.getRoot());
        initData();
        initView();
    }

    /**
     * init view
     */
    private void initView() {
        activityLoginBinding.rbAuto.setChecked(true);
        activityLoginBinding.rgLanguage.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                List<Language>list=null;
                if (activityLoginBinding.rbAuto.isChecked()){
                    Log.i(TAG,"now language is auto");
                    Locale primaryLocale = getResources().getConfiguration().getLocales().get(0);
                    String locale = primaryLocale.getDisplayName();
                    String lan=primaryLocale.getLanguage();
                    setLanguage(lan);
                }
                if (activityLoginBinding.rbEn.isChecked()){
//                    Log.i(TAG,"now language is en");
                    //        select:查找
                    list= DataSupport.select("language").find(Language.class);
                    if(list.size()!=0) {
                        Log.i(TAG,"get lan:" + list.get(0).getLanguage().toString());

                    }else
                        insertLanguage("en");
                    setLanguage("en");
                }
                if (activityLoginBinding.rbCn.isChecked()){
                    Language language=new Language();
                    language.setLanguage("jp");
                    language.updateAll();
                    Log.i(TAG,"now language is zh");
                    setLanguage("zh");
                }
            }
        });
    }

    private void insertLanguage(String lan) {
        Language language=new Language();
        language.setLanguage(lan);
        language.save();
    }

    /**
     * refresh:刷新当前页面
     */
    public void refresh() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
    private void setLanguage(String language){
        Locale myLocale = new Locale(language);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        refresh();
    }

    /**
     * init data
     */
    private void initData() {
//        build database：创建数据库
        try {
            Connector.getDatabase();
            Log.i(TAG,"build database success");
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG,"build database fail:"+e.toString());
        }

    }
    //遍历类中所有字段信息 https://blog.csdn.net/weixin_34346099/article/details/94036015
    public static void reflect(Object o){
        //获取参数类
        Class cls = o.getClass();
        //将参数类转换为对应属性数量的Field类型数组（即该类有多少个属性字段 N 转换后的数组长度即为 N）
        Field[] fields = cls.getDeclaredFields();
        for(int i = 0;i < fields.length; i ++){
            Field f = fields[i];
            f.setAccessible(true);
            try {
                //f.getName()得到对应字段的属性名，f.get(o)得到对应字段属性值,f.getGenericType()得到对应字段的类型
//                System.out.println("属性名："+f.getName()+"；属性值："+f.get(o)+";字段类型：" + f.getGenericType());
                Log.i("xwg","属性名："+f.getName()+"；属性值："+f.get(o)+";字段类型：" + f.getGenericType());
            } catch (IllegalArgumentException | IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                Log.i("xwg","ReflectUtil error:"+e.toString());
            }
        }
    }

    /**
     * save at SharePreferences:data/data/com.cnd.zhongkong.languageapp/shared_prefs/data.xml,reboot haven't exist! 重启不存在
     */
    private void SharedPreferencessave() {
        SharedPreferences.Editor editor=getSharedPreferences("data",MODE_PRIVATE).edit();
        editor.putString("name","jack");
        editor.putInt("age",31);
        editor.apply();
    }

    /**
     * read from data:从SharedPreference中读取字段信息
     */
    private void readSharedPreference(){
        SharedPreferences preferences=getSharedPreferences("data",MODE_PRIVATE);
        String name=preferences.getString("name","");
        int age=preferences.getInt("age",0);
        Log.i(TAG,"name:"+name+",age:"+age);
    }

    /**
     * read from file:从文件中读取文件
     */
    private void read() {
        FileInputStream in=null;
        BufferedReader reader=null;
        StringBuilder content=new StringBuilder();
        try{
            in=openFileInput("data");
            reader=new BufferedReader(new InputStreamReader(in));
            String line="";
            while ((line=reader.readLine())!=null)
                content.append(line);
            Log.i(TAG,"read from data:"+content.toString());
        }catch (IOException e){
            e.printStackTrace();
            Log.i(TAG,"read IOException:"+e.toString());
        }finally {
            if(reader!=null){
                try {
                    reader.close();
                }catch (IOException e){
                    e.printStackTrace();
                    Log.i(TAG,"read finally IOException:"+e.toString());
                }
            }
        }
    }

    /**
     * sava string:保存String字符串
     * path:data/data/com.cnd.zhongkong.languageapp/files/data, reboot haven't exist: 重启后不存在
     * @param lan_is_en
     */
    private void save(String lan_is_en) {
        FileOutputStream out=null;
        BufferedWriter writer=null;
        try {
            out=openFileOutput("data", Context.MODE_PRIVATE);
            writer=new BufferedWriter(new OutputStreamWriter(out));
            writer.write(lan_is_en);
            Log.i(TAG,"save finish!");
        }catch (IOException e){
            e.printStackTrace();
            Log.i(TAG,"save IOException:"+e.toString());
        }finally {
            try {
                if(writer!=null)
                    writer.close();
            }catch (IOException e){
                e.printStackTrace();
                Log.i(TAG,"save finally IOException:"+e.toString());
            }
        }
    }
}
