package com.bill.sourcecodetest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.bill.sourcecodetest.okhttp.OkHttpActivity;
import com.bill.sourcecodetest.retrofit.RetrofitActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void handleOkHttp(View view) {
        startActivity(new Intent(this, OkHttpActivity.class));
    }

    public void handleRetrofit(View view) {
        startActivity(new Intent(this, RetrofitActivity.class));
    }
}