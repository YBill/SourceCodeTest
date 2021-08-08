package com.bill.sourcecodetest.aidl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;

import com.bill.sourcecodetest.R;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class BookManagerActivity extends AppCompatActivity {

    private IBookManager mBookManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_manager);
        Intent intent = new Intent(this, BookManagerService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mBookManager = IBookManager.Stub.asInterface(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override
    protected void onDestroy() {
        unbindService(mConnection);
        super.onDestroy();
    }

    public void handleGet(View view) {
        try {
            List<Book> list = mBookManager.getBookList();
            Log.d("Bill", "query book list:" + list.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int i = 2;

    public void handleAdd(View view) {
        try {
            Book book = new Book(++i, "Android " + i);
            mBookManager.addBook(book);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}