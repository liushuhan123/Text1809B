package com.example.project_text.login;

import com.example.project_text.base.IBaseCallBack;
import com.example.project_text.base.IBasePresenter;
import com.example.project_text.base.IBaseView;
import com.example.project_text.data.entity.User;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.Map;

public interface LoginContract {



    public interface ILoginView extends IBaseView<ILoginPresenter> {

        void onSuccess(User user);

        void onFail(String msg);
    }


    public interface ILoginPresenter extends IBasePresenter<ILoginView> {

        void register(String username, String password, String repassword);
        void login(String username, String password);

    }


    public interface ILoginSource{

        void register(LifecycleProvider provider, Map<String, String> params, IBaseCallBack<User> callBack);

        void login(LifecycleProvider provider, Map<String, String> params, IBaseCallBack<User> callBack);

    }

}
