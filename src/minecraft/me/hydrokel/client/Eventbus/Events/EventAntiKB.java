package me.hydrokel.client.Eventbus.Events;

import me.hydrokel.client.Eventbus.Event;

public class EventAntiKB extends Event {
	
	private boolean cancelled;
	
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
	
	public boolean isCancelled() {
		return cancelled;
	}
	
}
