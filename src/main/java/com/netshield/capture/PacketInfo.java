package com.netshield.capture;

import java.time.Instant;

public class PacketInfo {

    private final String  srcIP, dstIP, protocol;
    private final int     srcPort, dstPort, size;
    private final boolean synFlag, ackFlag;
    private final Instant timestamp;

    public PacketInfo(String srcIP, String dstIP, int srcPort, int dstPort,
                      String protocol, int size, boolean synFlag, boolean ackFlag) {
        this.srcIP     = srcIP;
        this.dstIP     = dstIP;
        this.srcPort   = srcPort;
        this.dstPort   = dstPort;
        this.protocol  = protocol;
        this.size      = size;
        this.synFlag   = synFlag;
        this.ackFlag   = ackFlag;
        this.timestamp = Instant.now();
    }

    // ✅ Getters manquants
    public String  getSrcIP()    { return srcIP;    }
    public String  getDstIP()    { return dstIP;    }
    public int     getSrcPort()  { return srcPort;  }
    public int     getDstPort()  { return dstPort;  }
    public String  getProtocol() { return protocol; }
    public int     getSize()     { return size;     }

    // ✅ SYN sans ACK = SYN Flood
    public boolean isSynOnly()   { return synFlag && !ackFlag; }

    @Override
    public String toString() {
        return String.format("[%s] %s | %s:%d → %s:%d | %d bytes",
            timestamp, protocol, srcIP, srcPort, dstIP, dstPort, size);
    }
}