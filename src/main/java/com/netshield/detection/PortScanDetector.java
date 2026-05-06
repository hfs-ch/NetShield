package com.netshield.detection;

import com.netshield.capture.PacketInfo;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class PortScanDetector {

    private static final int  PORT_THRESHOLD  = 20;
    private static final long TIME_WINDOW_MS  = 10000;

    private final Map<String, Map<Integer, Long>> portTracker = new ConcurrentHashMap<>();

    public void analyze(PacketInfo packet) {
        if (packet == null) {
            return;
        }

        String protocol = packet.getProtocol();
        if (!"TCP".equals(protocol) && !"UDP".equals(protocol)) {
            return;
        }

        String srcIP = packet.getSrcIP();
        int dstPort = packet.getDstPort();
        if (srcIP == null || srcIP.isBlank() || dstPort <= 0) {
            return;
        }

        long now = System.currentTimeMillis();
        portTracker.putIfAbsent(srcIP, new ConcurrentHashMap<>());
        Map<Integer, Long> ports = portTracker.get(srcIP);

        synchronized (ports) {
            ports.put(dstPort, now);
            ports.entrySet().removeIf(e -> now - e.getValue() > TIME_WINDOW_MS);

            if (ports.size() >= PORT_THRESHOLD) {
                System.out.println("🚨 [PORT SCAN] IP: " + srcIP
                    + " | " + ports.size() + " ports différents en 10s"
                    + " → " + ports.keySet());
            }
        }
    }
}