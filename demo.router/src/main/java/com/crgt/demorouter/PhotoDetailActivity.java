package com.crgt.demorouter;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.crgt.router.RouterParam;
import com.crgt.router.RouterPath;

import java.util.ArrayList;

/**
 * ${DESC}
 *
 * @author android
 * @date 2019/5/11
 * @mail android@crgecent.com
 */

@RouterPath(path = "photo_detail")
public class PhotoDetailActivity extends BaseInjectActivity {
    private static final String TAG = PhotoDetailActivity.class.getSimpleName();

    @RouterParam(key = "photo_id")
    private int photoId = 100;

    @RouterParam(key = "content")
    private String content;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView textView = new TextView(this);
        textView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setContentView(textView);
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
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finish();
            }
        });
    }
}
