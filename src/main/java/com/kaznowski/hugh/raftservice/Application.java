package com.kaznowski.hugh.raftservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.DatagramPacket;
import java.util.Arrays;
import java.util.Collection;

public class Application {

    private static Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        Collection<UdpService> udpServices = UdpService.getAllUdpServices();
        udpServices.forEach(udpService -> udpService.send("Test"));
        udpServices.stream()
                .map(UdpService::receive)
                .map(Application::payload)
                .forEach(data -> logger.info("Data received is: {}", data));
    }

    private static String payload(DatagramPacket datagramPacket) {
        byte[] data = datagramPacket.getData();
        data = Arrays.copyOfRange(data, 0, datagramPacket.getLength());
        return new String(data);
    }
}
