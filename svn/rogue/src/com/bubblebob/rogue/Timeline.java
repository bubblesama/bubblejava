package com.bubblebob.rogue;

public interface Timeline {

	public void addEvent(Event event);
	public Event resume(int steps);
	public int getCurrentStep();
	
}
