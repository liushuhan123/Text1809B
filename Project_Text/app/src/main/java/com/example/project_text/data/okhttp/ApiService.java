package com.example.project_text.data.okhttp;

import com.example.project_text.data.entity.ArticleData;
import com.example.project_text.data.entity.Banner;
import com.example.project_text.data.entity.HttpResult;
import com.example.project_text.data.entity.TencentTab;
import com.example.project_text.data.entity.User;
import com.example.project_text.view.AppConstant;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    @POST(AppConstant.REGISTER)
    @FormUrlEncoded
    Observable<HttpResult<User>> register(@FieldMap Map<String, String> params);

    @POST(AppConstant.LOGIN)
    @FormUrlEncoded
    Observable<HttpResult<User>> login(@FieldMap Map<String, String> params);


    @GET("banner/json")
    Observable<HttpResult<List<Banner>>> getBanners();

    @GET("article/top/json")
    Observable<HttpResult<List<ArticleData.Article>>> getTopArticles();

    @GET("article/list/{page}/json")
    Observable<HttpResult<ArticleData>> getArticleData(@Path("page") int page);

    //公众号tab
    //https://wanandroid.com/wxarticle/chapters/json
    @GET("wxarticle/chapters/json")
    Observable<HttpResult<List<TencentTab>>> getTectab();

    //公众号list
    //https://wanandroid.com/wxarticle/list/408/1/json
    @GET("wxarticle/list/{page1}/{page2}/json")
    Observable<HttpResult<ArticleData>> getTabBean(@Path("page1") int page1, @Path("page2") int page2);


}
