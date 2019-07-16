package com.example.project_text.view;

public interface AppConstant {

    String BASEURL = "https://www.wanandroid.com";


    String LOGIN = "user/login";
    String REGISTER = "user/register";
    String COLLECTIONS = "lg/collect";
    String UNCOLLECTIONS = "lg/uncollect";
    String ARTICLE = "article";


    public interface LoginParamsKey {
        String SET_COOKIE_KEY = "set-cookie";
        String SET_COOKIE_NAME = "Cookie";
        String USER_NAME = "username";
        String PASSWORD = "password";
        String REPASSWORD = "repassword";
    }
}
