package com.example.project_text.fragment.home;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.text.TextUtils;

import androidx.annotation.IntDef;

import com.example.project_text.base.BaseRepository;
import com.example.project_text.base.IBaseCallBack;
import com.example.project_text.base.ServerException;
import com.example.project_text.data.entity.ArticleData;
import com.example.project_text.data.entity.Banner;
import com.example.project_text.data.entity.HttpResult;
import com.example.project_text.data.okhttp.DataService;
import com.example.project_text.utile.DataCacheUtils;
import com.example.project_text.utile.SPUtils;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

@SuppressLint("StaticFieldLeak")
public class HomeRepository extends BaseRepository implements HomeContract.IHomeSource {
    //用于sp存储的变量
    //banner
    private static final String BANNER_CACHE_KEY = "BANNER_CACHE_KEY";
    //top
    private static final String TOP_CACHE_KEY = "TOP_CACHE_KEY";
    //list
    private static final String NORMAL_CACHE_KEY = "NORMAL_CACHE_KEY";

    //自定义注解
    @IntDef({HomeLoadType.LOAD_TYPE_LOAD, HomeLoadType.LOAD_TYPE_REFRESH, HomeLoadType.LOAD_TYPE_MORE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface HomeLoadType {
        int LOAD_TYPE_LOAD = 1;
        int LOAD_TYPE_REFRESH = 2;
        int LOAD_TYPE_MORE = 3;
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void getBanner(final LifecycleProvider provider, int type, final IBaseCallBack<List<Banner>> callBack) {
        //如果第一次加载数据需要去看缓存有没有，如果有直接返回缓存数据，不用加载服务器，如果没有去加载数据
        if (type == HomeLoadType.LOAD_TYPE_LOAD) {
            setAsyncTask(provider, callBack);
        } else {
            observer(provider, DataService.getService().getBanners(), new Function<HttpResult<List<Banner>>, ObservableSource<List<Banner>>>() {
                @Override
                public ObservableSource<List<Banner>> apply(HttpResult<List<Banner>> listHttpResult) throws Exception {
                    if (listHttpResult.errorCode == 0 && listHttpResult.data != null) {
                        SPUtils.saveValueToDefaultSpByCommit(BANNER_CACHE_KEY, DataCacheUtils.convertToJsonFromObject(listHttpResult.data));
                        return Observable.just(listHttpResult.data);
                    }
                    return Observable.error(new ServerException(listHttpResult.errorMsg));

                }
            }, callBack);
        }

    }


    private void setAsyncTask(final LifecycleProvider provider, final IBaseCallBack<List<Banner>> callBack) {
        new AsyncTask<String, Integer, List<Banner>>() {
            @Override
            protected List<Banner> doInBackground(String... strings) {
                //把sp专成字符串
                String json = SPUtils.getValue(BANNER_CACHE_KEY);
                //判断如果不为空
                if (!TextUtils.isEmpty(json)) {
                    //把字符串转成对象
                    List<Banner> banners = DataCacheUtils.convertDataListFromJson(Banner.class, json);
                    if (banners != null && banners.size() > 0) {

                        return banners;
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(List<Banner> banners) {
                if (banners != null && banners.size() > 0) {
                    callBack.onSuccess(banners);
                } else {
                    observer(provider, DataService.getService().getBanners(), new Function<HttpResult<List<Banner>>, ObservableSource<List<Banner>>>() {
                        @Override
                        public ObservableSource<List<Banner>> apply(HttpResult<List<Banner>> listHttpResult) throws Exception {
                            if (listHttpResult.errorCode == 0 && listHttpResult.data != null) {
                                SPUtils.saveValueToDefaultSpByCommit(BANNER_CACHE_KEY, DataCacheUtils.convertToJsonFromObject(listHttpResult.data));
                                return Observable.just(listHttpResult.data);

                            }
                            return Observable.error(new ServerException(listHttpResult.errorMsg));
                        }
                    }, callBack);
                }

            }
        }.execute();
    }

    @Override
    public void getTopArticles(final LifecycleProvider provider, int type, final IBaseCallBack<List<ArticleData.Article>> callBack) {
        if (type == HomeLoadType.LOAD_TYPE_LOAD) {
            new AsyncTask<String, Integer, List<ArticleData.Article>>() {
                @Override
                protected List<ArticleData.Article> doInBackground(String... strings) {
                    final String value = SPUtils.getValue(TOP_CACHE_KEY);
                    if (!TextUtils.isEmpty(value)) {
                        List<ArticleData.Article> data = DataCacheUtils.convertDataListFromJson(ArticleData.Article.class, value);
                        if (data != null && data.size() > 0) {

                            return data;
                        }
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(List<ArticleData.Article> articles) {
                    if (articles != null && articles.size() > 0) {
                        callBack.onSuccess(articles);
                    } else {
                        observer(provider, DataService.getService().getTopArticles(), new Function<HttpResult<List<ArticleData.Article>>, ObservableSource<List<ArticleData.Article>>>() {
                            @Override
                            public ObservableSource<List<ArticleData.Article>> apply(HttpResult<List<ArticleData.Article>> listHttpResult) throws Exception {
                                if (listHttpResult.errorCode == 0 && listHttpResult.data != null) {
                                    SPUtils.saveValueToDefaultSpByCommit(TOP_CACHE_KEY, DataCacheUtils.convertToJsonFromObject(listHttpResult.data));
                                    return Observable.just(listHttpResult.data);

                                }
                                return Observable.error(new ServerException(listHttpResult.errorMsg));

                            }
                        }, callBack);
                    }
                }
            }.execute();
        } else {
            observer(provider, DataService.getService().getTopArticles(), new Function<HttpResult<List<ArticleData.Article>>, ObservableSource<List<ArticleData.Article>>>() {
                @Override
                public ObservableSource<List<ArticleData.Article>> apply(HttpResult<List<ArticleData.Article>> listHttpResult) throws Exception {
                    if (listHttpResult.errorCode == 0 && listHttpResult.data != null) {
                        SPUtils.saveValueToDefaultSpByCommit(TOP_CACHE_KEY, DataCacheUtils.convertToJsonFromObject(listHttpResult.data));
                        return Observable.just(listHttpResult.data);

                    }
                    return Observable.error(new ServerException(listHttpResult.errorMsg));

                }
            }, callBack);
        }

    }

    @Override
    public void getArticles(final LifecycleProvider provider, final int type, final IBaseCallBack<ArticleData> callBack) {
        if (type == HomeLoadType.LOAD_TYPE_LOAD) {
            new AsyncTask<String, Integer, ArticleData>() {

                @Override
                protected ArticleData doInBackground(String... strings) {
                    final String value = SPUtils.getValue(NORMAL_CACHE_KEY);
                    if (!TextUtils.isEmpty(value)) {
                        ArticleData data = DataCacheUtils.convertDataFromJson(ArticleData.class, value);
                        if (data != null) {
                            data.setCurPage(-1);
                            return data;
                        }
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(ArticleData articleData) {
                    if (articleData != null) {
                        callBack.onSuccess(articleData);
                    } else {
                        observer(provider, DataService.getService().getArticleData(0), new Function<HttpResult<ArticleData>, ObservableSource<ArticleData>>() {
                            @Override
                            public ObservableSource<ArticleData> apply(HttpResult<ArticleData> articleDataHttpResult) throws Exception {
                                if (articleDataHttpResult.errorCode == 0 && articleDataHttpResult != null) {
                                    SPUtils.saveValueToDefaultSpByCommit(NORMAL_CACHE_KEY, DataCacheUtils.convertToJsonFromObject(articleDataHttpResult.data));
                                    return Observable.just(articleDataHttpResult.data);
                                }
                                return Observable.error(new ServerException(articleDataHttpResult.errorMsg));
                            }
                        }, callBack);
                    }
                }
            }.execute();
        } else {
            observer(provider, DataService.getService().getArticleData(0), new Function<HttpResult<ArticleData>, ObservableSource<ArticleData>>() {
                @Override
                public ObservableSource<ArticleData> apply(HttpResult<ArticleData> articleDataHttpResult) throws Exception {
                    if (articleDataHttpResult.errorCode == 0 && articleDataHttpResult != null) {
                        SPUtils.saveValueToDefaultSpByCommit(NORMAL_CACHE_KEY, DataCacheUtils.convertToJsonFromObject(articleDataHttpResult.data));
                        return Observable.just(articleDataHttpResult.data);
                    }
                    return Observable.error(new ServerException(articleDataHttpResult.errorMsg));
                }
            }, callBack);
        }

    }

    @Override
    public void loadMoreArticles(LifecycleProvider provider, int type, IBaseCallBack<ArticleData> callBack, int page) {
        requestAticles(provider, type, callBack, page);

    }

    private void requestAticles(LifecycleProvider provider, int type, IBaseCallBack<ArticleData> callBack, int page) {
        observer(provider, DataService.getService().getArticleData(page), new Function<HttpResult<ArticleData>, ObservableSource<ArticleData>>() {
            @Override
            public ObservableSource<ArticleData> apply(HttpResult<ArticleData> articleDataHttpResult) throws Exception {
                if (articleDataHttpResult.errorCode == 0 && articleDataHttpResult != null) {
                    return Observable.just(articleDataHttpResult.data);
                }
                return Observable.error(new ServerException(articleDataHttpResult.errorMsg));
            }
        }, callBack);
    }


}
