package com.netshield.detection;

import com.netshield.capture.PacketInfo;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class SynFloodDetector {

    private static final int SYN_THRESHOLD  = 100;
    private static final long TIME_WINDOW_MS = 5000;

    private final Map<String, List<Long>> synTracker = new ConcurrentHashMap<>();

    public void analyze(PacketInfo packet) {
        if (packet == null || packet.getProtocol() == null) {
            return;
        }
        if (!"TCP".equals(packet.getProtocol())) {
            return;
        }
        if (!packet.isSynOnly()) {
            return;
        }

        String srcIP = packet.getSrcIP();
        if (srcIP == null || srcIP.isBlank()) {
            return;
        }

        long now = System.currentTimeMillis();
        synTracker.putIfAbsent(srcIP, new ArrayList<>());
        List<Long> timestamps = synTracker.get(srcIP);

        synchronized (timestamps) {
            timestamps.add(now);
            timestamps.removeIf(t -> now - t > TIME_WINDOW_MS);

            if (timestamps.size() >= SYN_THRESHOLD) {
                System.out.println("🚨 [SYN FLOOD] IP attaquante: " + srcIP
                    + " | " + timestamps.size() + " SYN en 5s");
            }
        }
    }
}