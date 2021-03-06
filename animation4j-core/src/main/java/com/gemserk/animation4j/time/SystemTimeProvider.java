package com.gemserk.animation4j.time;

/**
 * Default implementation of TimeProvider using system timer.
 * 
 * @author acoppes
 */
public class SystemTimeProvider implements TimeProvider {

	public float getTime() {
		return (float) System.nanoTime() * 0.000000001f;
	}

}