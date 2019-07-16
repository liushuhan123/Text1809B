package com.example.project_text.fragment.home;

import com.example.project_text.base.IBaseCallBack;
import com.example.project_text.base.IBasePresenter;
import com.example.project_text.base.IBaseView;
import com.example.project_text.data.entity.ArticleData;
import com.example.project_text.data.entity.Banner;
import com.trello.rxlifecycle2.LifecycleProvider;
import java.util.List;


public interface HomeContract {
    public interface IHomeView extends IBaseView<IHomePresenter> {
        //banner
        void onBannerReceiveData(List<Banner> banners, String msg);

        //顶部列表
        void onTopArticlesReceiveData(List<ArticleData.Article> articles, String msg);

        //列表
        void onArticlesReceiveData(ArticleData articleData, String msg);

        //下拉加载
        void onLoadMoreArticleReceiverData(ArticleData articleData, String msg);
    }

    public interface IHomePresenter extends IBasePresenter<IHomeView> {
        void getBannner(@HomeRepository.HomeLoadType int type);

        void getTopArticles(@HomeRepository.HomeLoadType int type);

        void getArticles(@HomeRepository.HomeLoadType int type);

        void loadMoreArticles(int page, @HomeRepository.HomeLoadType int type);
    }

    public interface IHomeSource {

        void getBanner(LifecycleProvider provider, @HomeRepository.HomeLoadType int type, IBaseCallBack<List<Banner>> callBack);

        void getTopArticles(LifecycleProvider provider, @HomeRepository.HomeLoadType int type, IBaseCallBack<List<ArticleData.Article>> callBack);

        void getArticles(LifecycleProvider provider, @HomeRepository.HomeLoadType int type, IBaseCallBack<ArticleData> callBack);

        void loadMoreArticles(LifecycleProvider provider, @HomeRepository.HomeLoadType int type, IBaseCallBack<ArticleData> callBack, int page);
    }
}
