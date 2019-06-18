package com.crgt.demorouter;

import android.os.Bundle;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.crgt.router.Router;
import com.crgt.router.RouterParam;
import com.crgt.router.RouterPath;

import java.util.ArrayList;

/**
 * ${DESC}
 *
 * @author android
 * @date 2019/5/27
 * @mail android@crgecent.com
 */

@RouterPath(path = "test")
public class TestFragment extends Fragment {
    private static final String TAG = TestFragment.class.getSimpleName();

    @RouterParam(key = "photo_id")
    int photoId = 100;

    @RouterParam(key = "content")
    String content;

    @RouterParam(key = "testIntArray")
    int[] testIntArray;

    @RouterParam(key = "testIntegerArrayList")
    ArrayList<Integer> testIntegerArrayList;

    @RouterParam(key = "testBoolean")
    boolean testBoolean;

    @RouterParam(key = "testLong")
    long testLong;

    @RouterParam(key = "testFloat")
    float testFloat;

    @RouterParam(key = "testDouble")
    double testDouble;

    @RouterParam(key = "testStringArray")
    String[] testStringArray;

    @RouterParam(key = "testStringArrayList")
    ArrayList<String> testStringArrayList;

    @RouterParam(key = "testBundle")
    Bundle testBundle;

    @RouterParam(key = "testParcelable")
    TestParcelable testParcelable;

    @RouterParam(key = "testParcelableArrayList")
    ArrayList<Parcelable> testParcelableArrayList;

    @RouterParam(key = "testSerializable")
    TestSerializable testSerializable;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        long time = SystemClock.uptimeMillis();
        Router.inject(this);
        Log.d(TAG, "onCreate: inject cost = " + (SystemClock.uptimeMillis() - time));
        TextView textView = new TextView(getContext());
        textView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        String text = "photoId = " + photoId + "\n" +
                "content = " + content + "\n" +
                "testIntArray = " + (testIntArray == null ? "null" : testIntArray.length) + "\n" +
                "testIntegerArrayList = " + testIntegerArrayList + "\n" +
                "testBoolean = " + testBoolean + "\n" +
                "testLong = " + testLong + "\n" +
                "testFloat = " + testFloat + "\n" +
                "testDouble = " + testDouble + "\n" +
                "testStringArray length = " + (testStringArray == null ? "null" : testStringArray.length) + "\n" +
                "testStringArrayList = " + testStringArrayList + "\n" +
                "testBundle = " + testBundle + "\n" +
                "testParcelable = " + testParcelable + "\n" +
                "testParcelableArrayList = " + testParcelableArrayList + "\n" +
                "testSerializable = " + testSerializable + "\n" +
                "end\n";
        textView.setText(text);
        return textView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
