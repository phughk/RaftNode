package com.kaznowski.hugh.raftservice;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.net.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class UdpService {

    private final DatagramSocket datagramSocket;
    private final SocketAddress socketAddress;

    public static Collection<UdpService> getAllUdpServices() {
        return Stream.of(getAllAddresses())
                .map(UdpService::new)
                .collect(Collectors.toList());
    }

    @SneakyThrows
    public UdpService(InetAddress bindAddress) {
        log.info("Constructing service with address {}", bindAddress.getHostAddress());
        socketAddress = new InetSocketAddress(bindAddress, 9123);
        datagramSocket = new MulticastSocket(socketAddress);
    }

    @SneakyThrows
    public void send(String string) {
        log.info("Sending message: {}", string);
        byte[] bytes = string.getBytes();
        log.info("Message has {} bytes", bytes.length);
        DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length, socketAddress);
        datagramSocket.send(datagramPacket);
    }

    @SneakyThrows
    public DatagramPacket receive() {
        byte[] bytes = new byte[35];
        DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length, socketAddress);
        datagramSocket.receive(datagramPacket);
        return datagramPacket;
    }

    @SneakyThrows
    private static InetAddress[] getAllAddresses() {
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        List<InetAddress> addresses = collectionFromEnumeration(interfaces)
                .stream()
                .map(NetworkInterface::getInetAddresses)
                .map(UdpService::collectionFromEnumeration)
                .flatMap(Collection::stream)
                .peek(inetAddress -> log.info("Found address {}", inetAddress.getHostAddress()))
                .collect(Collectors.toList());
        InetAddress[] inetAddresses = new InetAddress[addresses.size()];
        addresses.toArray(inetAddresses);
        return inetAddresses;
    }

    private static <E> Collection<E> collectionFromEnumeration(Enumeration<E> enumeration) {
        List<E> items = new ArrayList<E>();
        while (enumeration.hasMoreElements()) {
            items.add(enumeration.nextElement());
        }
        return items;
    }
}
