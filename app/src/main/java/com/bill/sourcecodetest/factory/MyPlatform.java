package com.bill.sourcecodetest.factory;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;

/**
 * author : Bill
 * date : 2021/7/15
 * description :
 */
public class MyPlatform {

    private static final MyPlatform PLATFORM = findPlatform();

    public static MyPlatform get() {
        return PLATFORM;
    }

    private static MyPlatform findPlatform() {
        boolean condition = true;
        if (condition)
            return new MyAndroid();
        return new MyPlatform();
    }

    public MyAdapter.MyFactory defaultAdapterFactory() {
        return DefaultAdapterFactory.INSTANCE;
    }

    static class MyAndroid extends MyPlatform {

        private Executor defaultCallbackExecutor() {
            return new MainThreadExecutor();
        }

        @Override
        public MyAdapter.MyFactory defaultAdapterFactory() {
            return new FactoryImpl(defaultCallbackExecutor());
        }

        static class MainThreadExecutor implements Executor {

            private final Handler handler = new Handler(Looper.getMainLooper());

            @Override
            public void execute(Runnable command) {
                handler.post(command);
            }
        }
    }

}
