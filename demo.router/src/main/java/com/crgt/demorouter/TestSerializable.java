package com.crgt.demorouter;

import java.io.Serializable;

/**
 * ${DESC}
 *
 * @author android
 * @date 2019/5/28
 * @mail android@crgecent.com
 */

public class TestSerializable implements Serializable {

    private int id;
    private String name;

    public TestSerializable(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "[id = " + id + ", name = " + name + "]";
    }
}
