package com.example.project_text.fragment.tencent;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import androidx.annotation.IntDef;

import com.example.project_text.base.BaseRepository;
import com.example.project_text.base.IBaseCallBack;
import com.example.project_text.base.ServerException;
import com.example.project_text.data.entity.ArticleData;
import com.example.project_text.data.entity.HttpResult;
import com.example.project_text.data.entity.TencentTab;
import com.example.project_text.data.okhttp.DataService;
import com.example.project_text.utile.MyDbUtils;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

public class TencentRepository extends BaseRepository implements TencentContract.ITencentModel {

    //自定义注解
    @IntDef({TencentRepository.TencentLoadType.LOAD_TYPE_LOAD, TencentRepository.TencentLoadType.LOAD_TYPE_REFRESH, TencentRepository.TencentLoadType.LOAD_TYPE_MORE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface TencentLoadType {
        //第一次加载

        int LOAD_TYPE_LOAD = 1;
        int LOAD_TYPE_REFRESH = 2;
        int LOAD_TYPE_MORE = 3;
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void getTab(final LifecycleProvider provider, int type, final IBaseCallBack<List<TencentTab>> callBack) {

        if (type == TencentLoadType.LOAD_TYPE_LOAD) {
            new AsyncTask<String, Integer, List<TencentTab>>() {
                @Override
                protected List<TencentTab> doInBackground(String... strings) {
                    final List<TencentTab> select = MyDbUtils.getMyHelper().select();
                    if (select != null && select.size() > 0) {
                        return select;
                    }
                    return null;
                }


                @Override
                protected void onPostExecute(final List<TencentTab> tabs) {
                    //数据库里有数据
                    if (tabs != null && tabs.size() > 0) {
                        //如数据库有数据去跟服务器判断
                        observer(provider, DataService.getService().getTectab(), new Function<HttpResult<List<TencentTab>>, ObservableSource<List<TencentTab>>>() {
                            @Override
                            public ObservableSource<List<TencentTab>> apply(HttpResult<List<TencentTab>> listHttpResult) throws Exception {
                                if (listHttpResult.errorCode == 0 && listHttpResult.data != null) {
                                    //本地数据保存的tab 数量和服务器返回的数量不一致，说明服务器的tab有改变，因此需要合并数据
                                    if (tabs.size() != listHttpResult.data.size()) {

                                    }
                                    //如长度相等
                                    else {
                                        TencentTab mTab;
                                        for (TencentTab datum : listHttpResult.data) {
                                            int indexOf = listHttpResult.data.indexOf(datum);
                                            // 不等于-1 说明服务器有一个tab 和当前tab 的id 是一样的。那么在看看其他字段是否一样。如果一样就没有改变，
                                            if (indexOf != -1) {
                                                // 如果其他字段也一样，那就说明本地的这个tab 目前在服务器上没有发生改变
                                                mTab = listHttpResult.data.get(indexOf);

                                            }
                                        }
                                    }
                                    return Observable.just(listHttpResult.data);
                                }
                                return Observable.error(new ServerException(listHttpResult.errorMsg));

                            }
                        }, callBack);

                        callBack.onSuccess(tabs);
                        //数据库里没有数据
                    } else {
                        observer(provider, DataService.getService().getTectab(), new Function<HttpResult<List<TencentTab>>, ObservableSource<List<TencentTab>>>() {
                            @Override
                            public ObservableSource<List<TencentTab>> apply(HttpResult<List<TencentTab>> listHttpResult) throws Exception {
                                if (listHttpResult.errorCode == 0 && listHttpResult.data != null) {
                                    MyDbUtils.getMyHelper().insert(listHttpResult.data);
                                    return Observable.just(listHttpResult.data);
                                }
                                return Observable.error(new ServerException(listHttpResult.errorMsg));

                            }
                        }, callBack);
                    }
                }
            }.execute();
        } else {
            observer(provider, DataService.getService().getTectab(), new Function<HttpResult<List<TencentTab>>, ObservableSource<List<TencentTab>>>() {
                @Override
                public ObservableSource<List<TencentTab>> apply(HttpResult<List<TencentTab>> listHttpResult) throws Exception {
                    if (listHttpResult.errorCode == 0 && listHttpResult.data != null) {
                        MyDbUtils.getMyHelper().insert(listHttpResult.data);
                        return Observable.just(listHttpResult.data);
                    }
                    return Observable.error(new ServerException(listHttpResult.errorMsg));

                }
            }, callBack);
        }




    }

    @Override
    public void getList(LifecycleProvider provider, int type, int id, int page, IBaseCallBack<ArticleData> callBack) {
        observer(provider, DataService.getService().getTabBean(id, page), new Function<HttpResult<ArticleData>, ObservableSource<ArticleData>>() {
            @Override
            public ObservableSource<ArticleData> apply(HttpResult<ArticleData> articleDataHttpResult) throws Exception {
                if (articleDataHttpResult.errorCode == 0 && articleDataHttpResult.data != null) {
                    return Observable.just(articleDataHttpResult.data);
                }
                return Observable.error(new ServerException(articleDataHttpResult.errorMsg));
            }
        }, callBack);

    }
}
