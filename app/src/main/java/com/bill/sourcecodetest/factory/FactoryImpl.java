package com.bill.sourcecodetest.factory;

import android.util.Log;

import java.util.concurrent.Executor;

/**
 * author : Bill
 * date : 2021/7/15
 * description :
 */
public class FactoryImpl extends MyAdapter.MyFactory {

    final Executor callbackExecutor;

    public FactoryImpl(Executor callbackExecutor) {
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
                return "FactoryImpl " + num;
            }
        };
    }

}
