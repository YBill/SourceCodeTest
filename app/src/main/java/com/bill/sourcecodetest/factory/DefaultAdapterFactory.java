package com.bill.sourcecodetest.factory;

import android.util.Log;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * author : Bill
 * date : 2021/7/15
 * description :
 */
public class DefaultAdapterFactory extends MyAdapter.MyFactory {

    static final Executor executor = Executors.newSingleThreadExecutor();

    static final MyAdapter.MyFactory INSTANCE = new DefaultAdapterFactory(executor);

    final Executor callbackExecutor;

    public DefaultAdapterFactory(Executor callbackExecutor) {
        this.callbackExecutor = callbackExecutor;
    }

    @Override
    public MyAdapter create() {
        return new MyAdapter() {
            @Override
            public String adapt(int num) {
                callbackExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("Bill", "当前线程：" + Thread.currentThread());
                    }
                });
                return "Default " + num;
            }
        };
    }
}
