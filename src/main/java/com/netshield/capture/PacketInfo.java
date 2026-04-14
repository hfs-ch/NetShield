package com.netshield.capture;

import java.time.LocalDateTime;

public class PacketInfo {

    private String sourceIP;
    private String destinationIP;
    private int sourcePort;
    private int destinationPort;
    private String protocol;   
    private int size;          
    private LocalDateTime timestamp;

    public PacketInfo(String sourceIP, String destinationIP,
                      int sourcePort, int destinationPort,
                      String protocol, int size) {
        this.sourceIP = sourceIP;
        this.destinationIP = destinationIP;
        this.sourcePort = sourcePort;
        this.destinationPort = destinationPort;
        this.protocol = protocol;
        this.size = size;
        this.timestamp = LocalDateTime.now();
    }

    public String getSourceIP()       { return sourceIP; }
    public String getDestinationIP()  { return destinationIP; }
    public int getSourcePort()        { return sourcePort; }
    public int getDestinationPort()   { return destinationPort; }
    public String getProtocol()       { return protocol; }
    public int getSize()              { return size; }
    public LocalDateTime getTimestamp() { return timestamp; }

    @Override
    public String toString() {
        return String.format("[%s] %s | %s:%d → %s:%d | %d bytes",
                timestamp, protocol,
                sourceIP, sourcePort,
                destinationIP, destinationPort,
                size);
    }
}
