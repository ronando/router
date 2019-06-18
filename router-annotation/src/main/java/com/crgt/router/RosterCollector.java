package com.crgt.router;

/**
 * Created by Gary on 18/2/9.
 */

public interface RosterCollector {

    void getActivities(RosterMap map);

    void getServices(RosterMap map);

    void getFragments(RosterMap map);
}
