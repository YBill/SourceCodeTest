package com.bill.sourcecodetest.eventbus;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.bill.sourcecodetest.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class EventBusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus);
        EventBus.getDefault().register(this);
    }

    public void handlePost(View view) {
        EventBus.getDefault().post(new TestEvent());
        EventBus.getDefault().postSticky(new TestEvent());
    }

    @Subscribe()
    public void onSubscribeEvent(TestEvent event) {
        Log.e("Bill", "event:" + event.id);
    }

    @Subscribe()
    public void onSubscribeEvent2(TestEventParent event) {
        TestEvent e = (TestEvent) event;
        Log.e("Bill", "event parent:" + e.id);
    }

    @Subscribe()
    public void onSubscribeEvent3(TestEventInterface event) {
        TestEvent e = (TestEvent) event;
        Log.e("Bill", "event interface:" + e.id);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}