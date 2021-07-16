package com.bill.sourcecodetest.retrofit;

import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * author : Bill
 * date : 2021/7/14
 * description : 动态更换Retrofit的baseUrl
 */
class BaseUrlInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

        // 获取request
        Request request = chain.request();
        HttpUrl oldHttpUrl = request.url();

        Log.e("Bill", "oldHttpUrl:" + oldHttpUrl.url());

        String header = request.header("replace");
        Log.e("Bill", "header:" + header);
        if (!TextUtils.isEmpty(header)) {
            Request.Builder builder = request.newBuilder();
            builder.removeHeader("replace");

            HttpUrl url = HttpUrl.parse("https://run.mocky.io/");
            Log.e("Bill", "url:" + url.url());
            HttpUrl newFullUrl = oldHttpUrl
                    .newBuilder()
                    // 更换网络协议
                    .scheme(url.scheme())
                    // 更换主机名
                    .host(url.host())
                    // 更换端口
                    .port(url.port())
                    .build();

            Log.e("Bill", "newFullUrl:" + newFullUrl.url());
            return chain.proceed(builder.url(newFullUrl).build());
        }

        return chain.proceed(request);
    }
}
