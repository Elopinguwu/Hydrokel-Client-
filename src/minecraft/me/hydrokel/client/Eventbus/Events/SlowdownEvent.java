package me.hydrokel.client.Eventbus.Events;

import me.hydrokel.client.Eventbus.CancelableEvent;

public class SlowdownEvent extends CancelableEvent {

	private Type type;
	
	public SlowdownEvent(final Type type) {
		this.type = type;
	}
	
	public Type getType() {
		return type;
	}
	
	public enum Type {
		Item, Sprinting, SoulSand, Water
	}
}
