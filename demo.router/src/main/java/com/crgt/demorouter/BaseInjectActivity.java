package com.crgt.demorouter;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.crgt.router.Router;

/**
 * ${DESC}
 *
 * @author android
 * @date 2019/6/1
 * @mail android@crgecent.com
 */

public abstract class BaseInjectActivity extends AppCompatActivity {
    private static final String TAG = BaseInjectActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        long time = SystemClock.uptimeMillis();
        Router.inject(this);
        Log.d(TAG, "onCreate: inject cost = " + (SystemClock.uptimeMillis() - time));
    }
}
