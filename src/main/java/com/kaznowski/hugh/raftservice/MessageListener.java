package com.kaznowski.hugh.raftservice;

import java.util.function.Consumer;

public interface MessageListener {
    void addListener(Consumer<String> listener);
    void sendMessage(String message);
    void poll();
}
