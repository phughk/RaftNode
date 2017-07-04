package com.kaznowski.hugh.raftservice;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class UdpMessageListener {

    private final Set<Consumer<String>> messageReceivedListeners = new HashSet<>();

    public void addListener(Consumer<String> listener) {
        messageReceivedListeners.add(listener);
    }

    public void sendMessage(String message) {

    }

    public void poll() {

    }
}
