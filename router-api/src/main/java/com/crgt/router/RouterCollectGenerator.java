package com.crgt.router;

import java.util.ArrayList;
import java.util.List;

/**
 * Router roster collector
 *
 * @author android
 * @date 2019/6/12
 * @mail android@crgecent.com
 */

public class RouterCollectGenerator {

    private List<RosterCollector> mCollector = new ArrayList<>();
    private boolean isAdded = false;

    public void collect(RouterRoster roster) {
        if (isAdded) {
            return;
        }
        registerCollect();
        for (RosterCollector collector : mCollector) {
            roster.collect(collector);
        }
        isAdded = true;
    }

    private void registerCollect() {
        // this method will be auto generated, don not to change it!
        // for example:
        // mCollector.add(new RouterCollectImpl$$demorouter());
    }
}
