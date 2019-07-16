package com.example.project_text.fragment.tencent;

import com.example.project_text.base.IBaseCallBack;
import com.example.project_text.base.IBasePresenter;
import com.example.project_text.base.IBaseView;
import com.example.project_text.data.entity.ArticleData;
import com.example.project_text.data.entity.TencentTab;
import com.example.project_text.fragment.home.HomeRepository;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.List;

public interface TencentContract {

    public interface ITencentView extends IBaseView<ITencentPresnter> {
        //tab
        void TopTencent(List<TencentTab> tabs, String msg);

        //list
        void ListTencent(ArticleData articleData, String msg);

    }

    public interface ITencentPresnter extends IBasePresenter<ITencentView> {
        void getTab(@TencentRepository.TencentLoadType int type);

        void getList(@TencentRepository.TencentLoadType int type,int id, int page);
    }

    public interface ITencentModel {
        void getTab(LifecycleProvider provider, int type, IBaseCallBack<List<TencentTab>> callBack);

        void getList(LifecycleProvider provider, int type, int id, int page, IBaseCallBack<ArticleData> callBack);

    }
}
