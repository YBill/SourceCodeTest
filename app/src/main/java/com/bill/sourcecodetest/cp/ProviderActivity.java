package com.bill.sourcecodetest.cp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bill.sourcecodetest.R;

public class ProviderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider);
        Log.i("Bill", "currentThread:" + Thread.currentThread());
    }

    public void handleQuery(View view) {
        Log.e("Bill", "click");

        Uri uri = Uri.parse("content://com.bill.sourcecodetest.cp.BooksProvider/book");

        ContentValues values = new ContentValues();
        values.put("id", 6);
        values.put("name", "程序设计艺术");
        getContentResolver().insert(uri, values);

        Cursor bookCursor = getContentResolver().query(uri, new String[]{"id", "name"}, null, null, null);
        while (bookCursor.moveToNext()) {
            Log.d("Bill", "query book id:" + bookCursor.getInt(0) + " book name:" + bookCursor.getString(1));
        }
        bookCursor.close();

        Uri userUri = Uri.parse("content://com.bill.sourcecodetest.cp.BooksProvider/user");
        Cursor userCursor = getContentResolver().query(userUri, new String[]{"id", "name", "sex"}, null, null, null);
        while (userCursor.moveToNext()) {
            Log.d("Bill", "query user id:" + userCursor.getInt(0) + " name:" +
                    userCursor.getString(1) + " sex:" + userCursor.getInt(2));
        }
        userCursor.close();
    }
}