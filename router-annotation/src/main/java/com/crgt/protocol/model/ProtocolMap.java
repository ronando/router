package com.crgt.protocol.model;

import java.util.HashMap;
import java.util.Map;


public class ProtocolMap {
    private HashMap<String, String> map = new HashMap<>();


    /**
     * @param protocolIdentifier         协议唯一标识符，可以是host、path或者host/path
     * @param protocolProcessorClassName
     */
    public void put(String protocolIdentifier, String protocolProcessorClassName) {
        if (map.get(protocolIdentifier) != null) {
            throw new RuntimeException("protocolIdentifier conflict:" + protocolIdentifier);
        }
        map.put(protocolIdentifier, protocolProcessorClassName);
    }

    public void put(ProtocolMap map) {
        if (map == null || map.get() == null) {
            return;
        }
        for (Map.Entry<String, String> entry : map.get().entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    public String get(String protocolIdentifier) {
        return map.get(protocolIdentifier);
    }

    public HashMap<String, String> get() {
        return map;
    }
}
