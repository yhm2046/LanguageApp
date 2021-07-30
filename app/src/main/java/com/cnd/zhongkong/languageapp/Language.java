package com.cnd.zhongkong.languageapp;

import org.litepal.crud.DataSupport;

public class Language extends DataSupport {
    private String language;

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }
}
