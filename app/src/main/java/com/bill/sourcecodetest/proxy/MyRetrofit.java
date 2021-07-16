package com.bill.sourcecodetest.proxy;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * author : Bill
 * date : 2021/7/16
 * description :
 */
public class MyRetrofit {

    private OkHttpClient okHttpClient;

    private String url;

    public MyRetrofit(String url) {
        okHttpClient = new OkHttpClient.Builder().build();
        this.url = url;
    }

    @SuppressWarnings("unchecked")
    public <T> T create(final Class<T> service) {
        Log.e("Bill", "create");
        return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class<?>[]{service},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        Log.e("Bill", "method:" + method);
                        Request request = new Request.Builder()
                                .url(url)
                                .build();
                        return okHttpClient.newCall(request);
                    }
                });
    }

}
