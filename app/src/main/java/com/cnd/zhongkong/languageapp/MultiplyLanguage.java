package com.cnd.zhongkong.languageapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioGroup;

import com.cnd.zhongkong.languageapp.databinding.ActivityLanguageBinding;

import org.litepal.crud.DataSupport;

import java.util.List;

public class MultiplyLanguage extends AppCompatActivity {
    private String strLanguage=null;
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
                finish();
            }
        });
    }

    /**
     * 生成数据库，必须在这里写否则写在application不生效
     */
    private void buildDatabase() {
        try {
            List<Language> list= DataSupport.select("language").find(Language.class);
            if (list.size()>0)
                Log.i(TAG,"select lan:"+list.get(0).getLanguage());
            else
                Log.i(TAG,"select is null");
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG,"select error:"+e.toString());
        }
    }
}