package com.example.project_text.base;


public interface IBaseCallBack<T> {

    void onSuccess(T data);
    void onFail(String msg);
}
