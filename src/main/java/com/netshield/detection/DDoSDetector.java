package com.netshield.detection;

import com.netshield.capture.PacketInfo;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class DDoSDetector {

    private static final int  PACKET_THRESHOLD = 500;
    private static final long TIME_WINDOW_MS   = 5000;

    private final Map<String, List<Long>> packetTracker = new ConcurrentHashMap<>();

    public void analyze(PacketInfo packet) {
        if (packet == null) {
            return;
        }

        String dstIP = packet.getDstIP();
        if (dstIP == null || dstIP.isBlank()) {
            return;
        }

        long now = System.currentTimeMillis();
        packetTracker.putIfAbsent(dstIP, new ArrayList<>());
        List<Long> timestamps = packetTracker.get(dstIP);

        synchronized (timestamps) {
            timestamps.add(now);
            timestamps.removeIf(t -> now - t > TIME_WINDOW_MS);

            if (timestamps.size() >= PACKET_THRESHOLD) {
                System.out.println("🚨 [DDoS DÉTECTÉ] Cible: " + dstIP
                    + " | " + timestamps.size() + " paquets reçus en 5s"
                    + " (protocole=" + packet.getProtocol() + ")");
            }
        }
    }
}