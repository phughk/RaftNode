package com.kaznowski.hugh.raftservice;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class UdpMessageListener implements MessageListener {

    private final Set<Consumer<String>> messageReceivedListeners = new HashSet<>();

    @Override
    public void addListener(Consumer<String> listener) {
        messageReceivedListeners.add(listener);
    }

    @Override
    public void sendMessage(String message) {

    }

    @Override
    public void poll() {

    }
}
