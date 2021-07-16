package com.bill.sourcecodetest.retrofit;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.bill.sourcecodetest.R;
import com.bill.sourcecodetest.factory.MyAdapter;
import com.bill.sourcecodetest.factory.MyPlatform;
import com.bill.sourcecodetest.proxy.MyRetrofit;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public class RetrofitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
    }

    interface Api {
        @Headers("replace:url")
        @GET("v3/6b4f4285-b21f-4a7c-96a5-4cbf38bac511")
        Call<ResponseBody> get();
    }

    public void handleNet(View view) {
        // 创建Builder并得到OkHttpClient
        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(new BaseUrlInterceptor());
        OkHttpClient okHttpClient = client.build();

        // 通过OkHttpClient获取Builder会将之前Builder值数据带过来
        OkHttpClient newClient = okHttpClient.newBuilder().build();

        // 创建Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://run.mocky.io/")
                .client(newClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // 通过动态代理创建接口对象
        Api api = retrofit.create(Api.class);
        // 执行动态代理invoke方法解析参数，并创建retrofit2.Call返回来
        final Call<ResponseBody> call = api.get();
        // 通过retrofit2.Call异步请求，内部通过静态代理执行okhttp3.Call的异步请求
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String body = response.body().string();
                    Log.i("Bill", "result:" + body);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i("Bill", "error:" + t.getMessage());
            }
        });
    }

    interface MyApi {
        okhttp3.Call get();
    }

    public void handleProxy(View view) {
        final String url = "https://run.mocky.io/v3/6b4f4285-b21f-4a7c-96a5-4cbf38bac511";
        MyRetrofit retrofit = new MyRetrofit(url);
        MyApi api = retrofit.create(MyApi.class);
        okhttp3.Call call = api.get();
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                Log.i("Bill", "error:" + e.getMessage());
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                String result = "is null";
                try {
                    result = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.i("Bill", result);
            }
        });
    }

    public void handleFactory(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 学Retrofit内部工厂代码
                MyAdapter.MyFactory factory = MyPlatform.get().defaultAdapterFactory();
                String s = factory.create().adapt(100);
                Log.d("Bill", "获取的值为：" + s + "，当前线程：" + Thread.currentThread());
            }
        }).start();
    }
}