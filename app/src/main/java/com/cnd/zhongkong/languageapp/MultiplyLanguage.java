package com.cnd.zhongkong.languageapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioGroup;

import com.cnd.zhongkong.languageapp.databinding.ActivityLanguageBinding;

import org.litepal.crud.DataSupport;

import java.util.List;
import java.util.Locale;

public class MultiplyLanguage extends AppCompatActivity {
    private String strLanguage="auto";
    private static final String TAG="MultiplyLanguage:xwg";
    private static final String LAN_AUTO="auto";
    private static final String LAN_EN="en";
    private static final String LAN_ZH="zh";
    private ActivityLanguageBinding activityLanguageBinding=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLanguageBinding=ActivityLanguageBinding.inflate(LayoutInflater.from(this));
        setContentView(activityLanguageBinding.getRoot());
//        buildDatabase();

//        进入界面的选择按钮
        try {
            List<Language>list= DataSupport.select("language").find(Language.class);
            if(list.size()>0) {
                String lan=list.get(0).getLanguage();
                Log.i(TAG,"select lan:"+lan);
                switch (lan){
                    case LAN_AUTO:activityLanguageBinding.rbAuto.setChecked(true);break;
                    case LAN_EN:activityLanguageBinding.rbEn.setChecked(true);break;
                    case LAN_ZH:activityLanguageBinding.rbCn.setChecked(true);break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG,"get database error:"+e.toString());
        }

        activityLanguageBinding.rgLanguage.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(activityLanguageBinding.rbAuto.isChecked()){
                    strLanguage=LAN_AUTO;
                }
                if(activityLanguageBinding.rbEn.isChecked()){
                    strLanguage=LAN_EN;
                }
                if(activityLanguageBinding.rbCn.isChecked()){
                    strLanguage=LAN_ZH;
                }
            }
        });

        activityLanguageBinding.btnLanguageSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                保存选择语言并保存到本地，下一次进入记住上次选择的语言
                Log.i(TAG,"choose lan:"+strLanguage);
                Language language=new Language();
                language.setLanguage(strLanguage);
                language.updateAll();
//                应用设置好的语言
                if (strLanguage.equals(LAN_AUTO))
                    strLanguage=Locale.getDefault().getLanguage();
                    Log.i(TAG,"strLanguage save as:"+strLanguage);
                Locale myLocale = new Locale(strLanguage);
                Resources res = getResources();
                DisplayMetrics dm = res.getDisplayMetrics();
                Configuration conf = res.getConfiguration();
                conf.locale = myLocale;
                res.updateConfiguration(conf, dm);
                finish();
            }
        });
    }


}