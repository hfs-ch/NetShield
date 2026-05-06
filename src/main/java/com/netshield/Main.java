package com.netshield;

import com.netshield.capture.PacketSniffer;

public class Main {
    public static void main(String[] args) {

        
        PacketSniffer.listInterfaces();

        String myInterface = "ens33";

        PacketSniffer sniffer = new PacketSniffer(myInterface);

        Thread snifferThread = new Thread(sniffer::start);
        snifferThread.start();

        try {
            Thread.sleep(120000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        sniffer.stop();
        System.out.println("✅ Capture complete.");
    }
}
