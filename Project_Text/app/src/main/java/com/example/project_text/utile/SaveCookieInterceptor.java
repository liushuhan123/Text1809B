package com.example.project_text.utile;

import android.text.TextUtils;

import com.example.project_text.view.AppConstant;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class SaveCookieInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        String url = request.url().toString();
        Response response = chain.proceed(request);

        if (!TextUtils.isEmpty(response.header(AppConstant.LoginParamsKey.SET_COOKIE_KEY)) && (url.contains(AppConstant.LOGIN) || url.contains(AppConstant.REGISTER))) {

            // 从header 中取出cookie，取出的事一个list。
            List<String> cookies = response.headers(AppConstant.LoginParamsKey.SET_COOKIE_KEY);

            if (cookies.size() > 0) {
                HashSet<String> cookieSet = new HashSet<>(); // 去重
                for (String cooke : cookies) {
                    for (String value : cooke.split(";")) {
                        if (!TextUtils.isEmpty(value)) {
                            cookieSet.add(value);
                        }
                    }
                }
                StringBuilder sb = new StringBuilder();
                for (String aCookieSet : cookieSet) {
                    sb.append(aCookieSet);
                    sb.append(";");
                }

                sb.deleteCharAt(sb.length() - 1);

                SPUtils.saveValueToDefaultSpByApply(AppConstant.LoginParamsKey.SET_COOKIE_KEY, sb.toString());

            }

        }

        return response;
    }
}
