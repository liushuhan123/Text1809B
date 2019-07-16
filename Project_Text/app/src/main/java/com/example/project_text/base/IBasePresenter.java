package com.example.project_text.base;


public interface IBasePresenter<T extends IBaseView> {

    void attachView(T view);

    void detachView(T view);


}
