package com.example.project_text.fragment.tencent;

import com.example.project_text.base.IBaseCallBack;
import com.example.project_text.data.entity.ArticleData;
import com.example.project_text.data.entity.TencentTab;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.List;

public class TencentPresenter implements TencentContract.ITencentPresnter {

    private TencentContract.ITencentModel mModel;
    private TencentContract.ITencentView mView;

    public TencentPresenter() {
        mModel = new TencentRepository();
    }

    @Override
    public void getTab(@TencentRepository.TencentLoadType int type) {
        mModel.getTab((LifecycleProvider) mView, type, new IBaseCallBack<List<TencentTab>>() {
            @Override
            public void onSuccess(List<TencentTab> data) {
                if (mView != null) {
                    mView.TopTencent(data, null);
                }

            }

            @Override
            public void onFail(String msg) {
                if (mView != null) {
                    mView.TopTencent(null, msg);
                }

            }
        });
    }

    @Override
    public void getList(@TencentRepository.TencentLoadType int type, int id, int page) {
        mModel.getList((LifecycleProvider) mView, type, id, page, new IBaseCallBack<ArticleData>() {
            @Override
            public void onSuccess(ArticleData data) {
                if (mView != null) {
                    mView.ListTencent(data, null);
                }
            }

            @Override
            public void onFail(String msg) {
                if (mView != null) {
                    mView.ListTencent(null, msg);
                }
            }
        });
    }

    @Override
    public void attachView(TencentContract.ITencentView view) {
        mView = view;
    }

    @Override
    public void detachView(TencentContract.ITencentView view) {
        mView = null;
    }
}
