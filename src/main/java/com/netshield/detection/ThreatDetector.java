package com.netshield.detection;

import com.netshield.capture.PacketInfo;

public class ThreatDetector {

    private final SynFloodDetector synFloodDetector = new SynFloodDetector();
    private final PortScanDetector portScanDetector = new PortScanDetector();
    private final DDoSDetector     ddosDetector     = new DDoSDetector();

    public void analyze(PacketInfo packet) {
        synFloodDetector.analyze(packet);
        portScanDetector.analyze(packet);
        ddosDetector.analyze(packet);
    }
}