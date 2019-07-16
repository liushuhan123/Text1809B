package com.example.project_text.fragment.home;

import com.example.project_text.base.IBaseCallBack;
import com.example.project_text.base.IBaseView;
import com.example.project_text.data.entity.ArticleData;
import com.example.project_text.data.entity.Banner;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.List;

public class HomePresenter implements HomeContract.IHomePresenter {
    private HomeContract.IHomeSource mHomeSource;
    private HomeContract.IHomeView mIHomeView;

    public HomePresenter() {
        mHomeSource = new HomeRepository();
    }

    @Override
    public void getBannner(@HomeRepository.HomeLoadType int type) {
        mHomeSource.getBanner((LifecycleProvider) mIHomeView, type, new IBaseCallBack<List<Banner>>() {
            @Override
            public void onSuccess(List<Banner> data) {
                if (mIHomeView != null) {
                    mIHomeView.onBannerReceiveData(data, null);
                }
            }

            @Override
            public void onFail(String msg) {
                if (mIHomeView != null) {
                    mIHomeView.onBannerReceiveData(null, msg);
                }
            }
        });

    }

    @Override
    public void getTopArticles(@HomeRepository.HomeLoadType int type) {
        mHomeSource.getTopArticles((LifecycleProvider) mIHomeView, type, new IBaseCallBack<List<ArticleData.Article>>() {
            @Override
            public void onSuccess(List<ArticleData.Article> data) {
                if (mIHomeView != null) {
                    for (ArticleData.Article article : data) {
                        article.setTop(true);
                    }
                    mIHomeView.onTopArticlesReceiveData(data, null);

                }
            }

            @Override
            public void onFail(String msg) {
                if (mIHomeView != null) {
                    mIHomeView.onTopArticlesReceiveData(null, msg);
                }
            }
        });

    }

    @Override
    public void getArticles(@HomeRepository.HomeLoadType int type) {
        mHomeSource.getArticles((LifecycleProvider) mIHomeView, type, new IBaseCallBack<ArticleData>() {
            @Override
            public void onSuccess(ArticleData data) {
                if (mIHomeView != null) {
                    mIHomeView.onArticlesReceiveData(data, null);
                }
            }

            @Override
            public void onFail(String msg) {
                if (mIHomeView != null) {
                    mIHomeView.onArticlesReceiveData(null, msg);
                }
            }
        });

    }

    @Override
    public void loadMoreArticles(final int page, int type) {
        mHomeSource.loadMoreArticles((LifecycleProvider) mIHomeView, type, new IBaseCallBack<ArticleData>() {
            @Override
            public void onSuccess(ArticleData data) {
                if (mIHomeView != null) {
                    mIHomeView.onLoadMoreArticleReceiverData(data, null);
                }
            }

            @Override
            public void onFail(String msg) {
                if (mIHomeView != null) {
                    mIHomeView.onLoadMoreArticleReceiverData(null, msg);
                }
            }
        }, page);
    }


    @Override
    public void attachView(HomeContract.IHomeView view) {
        mIHomeView = view;
    }

    @Override
    public void detachView(HomeContract.IHomeView view) {
        mIHomeView = null;
    }
}
