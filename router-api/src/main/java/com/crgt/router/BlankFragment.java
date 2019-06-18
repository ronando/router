package com.crgt.router;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Return this blank fragment if router can not find fragment instance
 *
 * @author android
 * @date 2019/6/1
 * @mail android@crgecent.com
 */

public class BlankFragment extends Fragment {

    private String pathname;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView textView = new TextView(getContext());
        textView.setBackgroundColor(Color.WHITE);
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        textView.setGravity(Gravity.CENTER);
        if (TextUtils.isEmpty(pathname)) {
            textView.setText("404. Can't find fragment.");
        } else {
            textView.setText("404. Can't find fragment " + pathname + ".");
        }
        return textView;
    }

    public void setPathName(String pathName) {
        this.pathname = pathName;
    }
}
