package com.crgt.demorouter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.crgt.demorouter.param.JumpUrlInterruptSpec;
import com.crgt.router.DefaultProtocolParser;
import com.crgt.router.ParamBuilder;
import com.crgt.router.Router;
import com.crgt.router.RouterInterceptor;
import com.crgt.router.RouterNotFoundHandler;
import com.crgt.router.RouterPath;
import com.crgt.router.autofillparam.ParamAutoFillInterceptor;

import java.util.ArrayList;

/**
 * route demo activity
 *
 * @author android
 * @date 2019/5/7
 * @mail android@crgecent.com
 */

@RouterPath(path = "main")
public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configRouter();
    }

    private void configRouter() {
        Router.init(getApplication());
        Router.configProtocol(new DefaultProtocolParser("crgt", DefaultProtocolParser.ComponentIdentifier.PATH));
        Router.setNotFoundHandler(new RouterNotFoundHandler() {
            @Override
            public void noActivityFound(Context appContext, String path, String errorMsg) {
                Toast.makeText(appContext, "Custom msg: " + path + " not found", Toast.LENGTH_SHORT).show();
            }
        });
        Router.addInterceptor(new RouterInterceptor() {
            @Override
            public void intercept(Context context, String componentName, @Nullable ParamBuilder param, Callback callback) {
                callback.onContinue();
            }
        });
        Router.addInterceptor(new RouterInterceptor() {
            @Override
            public void intercept(Context context, String componentName, @Nullable ParamBuilder param, Callback callback) {
                if ("intercept_test".equals(componentName)) {
                    toastMsg("Activity was intercepted");
                    callback.onIntercept();
                } else {
                    callback.onContinue();
                }
            }
        });

        Router.addInterceptor(new ParamAutoFillInterceptor(new JumpUrlInterruptSpec()) {
            @Override
            public void intercept(Context context, String componentName, @Nullable ParamBuilder param, @NonNull Callback callback) {
                super.intercept(context, componentName, param, callback);
            }
        });
    }


    public void gotoSimpleActivity(View view) {
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

        ParamBuilder params = Router.buildParams()
                .withInt("photo_id", 666)
                .withIntArray("testIntArray", new int[]{1, 2, 3, 4, 5})
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
                .withBundle("testBundle", new Bundle());
        Router.toActivity(this, "photo_detail", params);
    }

    public void gotoActivityForResult(View view) {
        ParamBuilder paramBuilder = Router.buildParams().withInt("aaa", 123);
        Router.toActivity(this, "photo_detail", paramBuilder);
    }

    public void gotoActivityWithFragment(View view) {
        Router.toActivity(this, "fragment_activity", null);
    }

    public void gotoActivityOtherModule(View view) {
        Router.toActivity(this, "module_test", null);
    }

    public void gotoNotExistActivity(View view) {
        Router.toActivity(this, "error", null);
    }

    public void gotoInterceptActivity(View view) {
        Router.toActivity(this, "intercept_test", null);
    }

    public void gotoGetFragment(View view) {
        Fragment fragment = (Fragment) Router.buildParams()
                .withInt("aaa", 123)
                .withString("bbb", "bbb")
                .getFragmentInstance("test");
        toastMsg(fragment.toString());
    }

    public void gotoGetService(View view) {
        Class clazz = Router.getServiceClass("message");
        if (clazz != null) {
            Intent intent = new Intent(this, clazz);
            intent.setAction("com.crgt.infashion.msgservice");
            startService(intent);
        }
    }

    public void gotoProtocol(View view) {
        Router.toActivity(this, "crgt://ccrgt.com/photo_detail?photo_id=123&content=hahahahaha&testBoolean=true&testFloat=3.1415", null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 111) {
            toastMsg("onResult OK.");
        }
    }

    private void toastMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void autoFillParam(View view) {
        Router.toActivity(this, JumpUrlInterruptSpec.ROUTER_PROVIDR_URL, null);
    }
}
