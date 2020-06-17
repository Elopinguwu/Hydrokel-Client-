package me.hydrokel.client.Eventbus.Events;


import me.hydrokel.client.Eventbus.Event;

public class EventMouse extends Event {

    private int key;

    public EventMouse(final int key) {
        this.key = key;
    }

    public int getKey() {
        return this.key;
    }

    public void setKey(final int key) {
        this.key = key;
    }
}
