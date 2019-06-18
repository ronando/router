package com.crgt.router;

/**
 * ${DESC}
 *
 * @author android
 * @date 2019/5/9
 * @mail android@crgecent.com
 */

public class RouterRoster {
    private static final String TAG = RouterRoster.class.getSimpleName();

    private RosterMap mActivityRoster;
    private RosterMap mFragmentRoster;
    private RosterMap mServiceRoster;

    public RouterRoster() {
        mActivityRoster = new RosterMap();
        mFragmentRoster = new RosterMap();
        mServiceRoster = new RosterMap();
    }

    public void collect(RosterCollector collector) {
        collector.getActivities(mActivityRoster);
        collector.getFragments(mFragmentRoster);
        collector.getServices(mServiceRoster);
    }

    public String getActivityName(String path) {
        return mActivityRoster.getMap().get(path);
    }

    public String getFragmentName(String path) {
        return mFragmentRoster.getMap().get(path);
    }

    public String getServiceName(String path) {
        return mServiceRoster.getMap().get(path);
    }

    public void setActivityClassName(String componentName, String activityClassName) {
        mActivityRoster.getMap().put(componentName, activityClassName);
    }

    public void setFragmentClassName(String componentName, String fragmentClassName) {
        mFragmentRoster.getMap().put(componentName, fragmentClassName);
    }

    public Class findActivity(String name) throws ClassNotFoundException {
        String className = mActivityRoster.getMap().get(name);
        return Class.forName(className);
    }

    public Class findFragment(String name) throws ClassNotFoundException {
        String className = mFragmentRoster.getMap().get(name);
        return Class.forName(className);
    }

    public Class findService(String name) throws ClassNotFoundException {
        String className = mServiceRoster.getMap().get(name);
        return Class.forName(className);
    }
}
