package me.hydrokel.client.Eventbus.Events;

import me.hydrokel.client.Eventbus.Event;

public class EventKey extends Event {
    private int key;

    public EventKey(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }
}
