package com.crgt.demorouter;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.crgt.router.Router;
import com.crgt.router.RouterPath;

import java.util.ArrayList;

/**
 * ${DESC}
 *
 * @author android
 * @date 2019/6/1
 * @mail android@crgecent.com
 */

@RouterPath(path = "fragment_activity")
public class TestFragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_with_fragment);

        ArrayList<Integer> integerArrayList = new ArrayList<>();
        integerArrayList.add(1);
        integerArrayList.add(2);

        ArrayList<String> stringArrayList = new ArrayList<>();
        stringArrayList.add("adam");
        stringArrayList.add("bill");
        stringArrayList.add("carl");
        stringArrayList.add("david");

        ArrayList<Parcelable> parcelableArrayList = new ArrayList<>();
        parcelableArrayList.add(new TestParcelable(1, "a"));
        parcelableArrayList.add(new TestParcelable(2, "aa"));
        parcelableArrayList.add(new TestParcelable(3, "aaa"));
        parcelableArrayList.add(new TestParcelable(4, "aaaa"));

        Fragment fragment = (Fragment) Router.buildParams()
                .withInt("photo_id", 666)
                .withIntArray("testIntArray", new int[]{ 1, 2, 3, 4, 5 })
                .withIntegerArrayList("testIntegerArrayList", integerArrayList)
                .withString("content", "this is content")
                .withBoolean("testBoolean", true)
                .withLong("testLong", 111111111111L)
                .withFloat("testFloat", 3.1415926f)
                .withDouble("testDouble", Math.PI)
                .withStringArray("testStringArray", new String[]{"a", "b", "c"})
                .withStringArrayList("testStringArrayList", stringArrayList)
                .withParcelable("testParcelable", new TestParcelable(111, "adam"))
                .withParcelableArrayList("testParcelableArrayList", parcelableArrayList)
                .withSerializable("testSerializable", new TestSerializable(123, "test"))
                .withBundle("testBundle", new Bundle())
                .getFragmentInstance("test");

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_fragment_container, fragment)
                .commit();
    }

    public void replaceNotExistFragment(View view) {
        Fragment fragment = (Fragment) Router.getFragmentInstance("error");

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_fragment_container, fragment)
                .commit();
    }
}
