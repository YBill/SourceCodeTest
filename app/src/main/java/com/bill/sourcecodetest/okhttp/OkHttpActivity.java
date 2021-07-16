package com.bill.sourcecodetest.okhttp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.bill.sourcecodetest.R;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpActivity extends AppCompatActivity {

    final OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Response response = chain.proceed(chain.request());
                    Log.e("Bill", "url:" + chain.request().url());
                    Log.e("Bill", "method:" + chain.request().method());
                    String result = response.peekBody(Long.MAX_VALUE).string();
                    Log.e("Bill", "body:" + result);
                    return response;
                }
            })
            .addNetworkInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Response response = chain.proceed(chain.request());
                    String result = response.peekBody(Long.MAX_VALUE).string();
                    Log.d("Bill", "body:" + result);
                    return response;
                }
            })
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp);
    }

    public void handleNet(View view) {
        final String url = "https://run.mocky.io/v3/6b4f4285-b21f-4a7c-96a5-4cbf38bac511";

        /*new Thread(new Runnable() {
            @Override
            public void run() {
                Request request = new Request.Builder().url(url).build();
                try {
                    Response response = client.newCall(request).execute();
                    String result = response.body().string();
                    Log.e("Bill", result);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();*/


        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("Bill", "error:" + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
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
}