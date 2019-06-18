package com.crgt.demorouter;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * ${DESC}
 *
 * @author android
 * @date 2019/5/28
 * @mail android@crgecent.com
 */

public class TestParcelable implements Parcelable {

    private int id;

    private String name;

    public TestParcelable(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "[id = " + id + ", name = " + name + "]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
    }

    protected TestParcelable(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
    }

    public static final Creator<TestParcelable> CREATOR = new Creator<TestParcelable>() {
        @Override
        public TestParcelable createFromParcel(Parcel source) {
            return new TestParcelable(source);
        }

        @Override
        public TestParcelable[] newArray(int size) {
            return new TestParcelable[size];
        }
    };
}
