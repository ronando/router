package com.crgt.protocol;


import java.util.ArrayList;
import java.util.List;

import com.crgt.protocol.model.ProtocolMap;

/**
 * Router roster collector
 *
 * @author jesse.lu
 * @date 2019/6/12
 * @mail jesse.lu@foxmail.com
 */

public class ProtocolCollectGenerator {

    private List<ProtocolCollector> mCollector = new ArrayList<>();
    private boolean isAdded = false;

    public void collect(ProtocolMap protocolMap) {
        if (isAdded) {
            return;
        }
        registerCollect();
        for (ProtocolCollector collector : mCollector) {
            collector.collectProtocol(protocolMap);
        }
        isAdded = true;
    }

    private void registerCollect() {
        // this method will be auto generated, don not to change it!
        // for example:
        // mCollector.add(new ProtocolCollectImpl$$demoprotocol());
    }
}
