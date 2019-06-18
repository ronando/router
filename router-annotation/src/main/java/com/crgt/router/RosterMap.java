package com.crgt.router;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gary on 18/2/9.
 */

public class RosterMap {
    private Map<String, String> map = new HashMap<>();

    /**
     * put router data
     *
     * @param name
     * @param className
     */
    public void put(String name, String className) {
        if (map.containsKey(name)) {
            throw new IllegalArgumentException(name + " is duplicate, can not have same router path!");
        }
        map.put(name, className);
    }

    /**
     * get map data
     *
     * @return map
     */
    public Map<String, String> getMap() {
        return map;
    }

    /**
     * combine two map data
     *
     * @param otherMap other roster map
     */
    public void combine(RosterMap otherMap) {
        if (otherMap == null || otherMap.getMap() == null) {
            return;
        }
        for (Map.Entry<String, String> entry : otherMap.getMap().entrySet()) {
            if (map.containsKey(entry.getKey())) {
                throw new IllegalArgumentException(entry.getKey() + " is duplicate, can not have same router path.");
            }
            map.put(entry.getKey(), entry.getValue());
        }
    }
}
