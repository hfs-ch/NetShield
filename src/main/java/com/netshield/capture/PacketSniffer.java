package com.netshield.capture;

import org.pcap4j.core.*;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.IpV4Packet;
import org.pcap4j.packet.TcpPacket;
import org.pcap4j.packet.UdpPacket;
import org.pcap4j.packet.IcmpV4CommonPacket;
import java.util.ArrayList;
import java.util.List;

public class PacketSniffer {

    private String networkInterface;
    private PcapHandle handle;
    private boolean running = false;

    
    private static final int SNAP_LEN = 65536;
    
    private static final PcapNetworkInterface.PromiscuousMode PROMISCUOUS =
            PcapNetworkInterface.PromiscuousMode.PROMISCUOUS;
    
    private static final int TIMEOUT = 10;

    public PacketSniffer(String networkInterface) {
        this.networkInterface = networkInterface;
    }

    public void start() {
        try {
            
            PcapNetworkInterface nif = Pcaps.getDevByName(networkInterface);
            if (nif == null) {
                System.err.println("❌ Interface not found: " + networkInterface);
                return;
            }

            System.out.println("✅ Listening on interface: " + nif.getName());
            System.out.println("   Description: " + nif.getDescription());

            
            handle = nif.openLive(SNAP_LEN, PROMISCUOUS, TIMEOUT);

            running = true;

            
            while (running) {
                try {
                    Packet packet = handle.getNextPacketEx();
                    if (packet != null) {
                        processPacket(packet);
                    }
                } catch (Exception e) {
                    
                }
            }

        } catch (PcapNativeException e) {
            System.err.println("❌ Pcap error: " + e.getMessage());
        }
    }

    private List<PacketInfo> capturedPackets = new ArrayList<>();

    private void processPacket(Packet packet) {

        
        IpV4Packet ipPacket = packet.get(IpV4Packet.class);
        if (ipPacket == null) return;

        String srcIP  = ipPacket.getHeader().getSrcAddr().getHostAddress();
        String dstIP  = ipPacket.getHeader().getDstAddr().getHostAddress();
        int size      = packet.length();
        String protocol;
        int srcPort = 0, dstPort = 0;

        
        if (packet.contains(TcpPacket.class)) {
            TcpPacket tcp = packet.get(TcpPacket.class);
            srcPort  = tcp.getHeader().getSrcPort().valueAsInt();
            dstPort  = tcp.getHeader().getDstPort().valueAsInt();
            protocol = "TCP";

        } else if (packet.contains(UdpPacket.class)) {
            UdpPacket udp = packet.get(UdpPacket.class);
            srcPort  = udp.getHeader().getSrcPort().valueAsInt();
            dstPort  = udp.getHeader().getDstPort().valueAsInt();
            protocol = "UDP";

        } else if (packet.contains(IcmpV4CommonPacket.class)) {
            protocol = "ICMP";

        } else {
            protocol = "OTHER";
        }

        
        PacketInfo info = new PacketInfo(srcIP, dstIP, srcPort, dstPort, protocol, size);
        capturedPackets.add(info);

    
        System.out.println("📦 " + info);
    }

    public void stop() {
        running = false;
        if (handle != null && handle.isOpen()) {
            handle.close();
            System.out.println("🛑 Sniffer stopped.");
        }
    }

    
    public static void listInterfaces() {
        try {
            System.out.println("=== Available Network Interfaces ===");
            for (PcapNetworkInterface nif : Pcaps.findAllDevs()) {
                System.out.println("  → " + nif.getName() + " | " + nif.getDescription());
                nif.getAddresses().forEach(addr ->
                    System.out.println("      IP: " + addr.getAddress())
                );
            }
        } catch (PcapNativeException e) {
            System.err.println("Error listing interfaces: " + e.getMessage());
        }
    }
}
