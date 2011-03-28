package com.gemserk.animation4j.timeline;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.junit.Test;

import com.gemserk.animation4j.converters.Converters;

@SuppressWarnings({ "rawtypes", "serial" })
public class TimelineTest {
	
	@Test
	public void testGetValueForAGivenTime() {

		Timeline timeline = new Timeline(new HashMap<String, TimelineValue>() {
			{
				put("myvalue", new TimelineValueBuilder<Float>(Converters.floatValue()).keyFrame(0, 100f).keyFrame(100, 200f).build());
			}
		});

		assertEquals(100f, (Float) timeline.getValue(0, "myvalue"), 0.01f);
		assertEquals(200f, (Float) timeline.getValue(100, "myvalue"), 0.01f);
	}

	@Test
	public void testGetValueForAGivenTimeWithDelay() {

		Timeline timeline = new Timeline(new HashMap<String, TimelineValue>() {
			{
				put("myvalue", new TimelineValueBuilder<Float>(Converters.floatValue()).keyFrame(0, 100f).keyFrame(100, 200f).build());
			}
		});

		float delay = 100f;

		assertEquals(100f, (Float) timeline.getValue(0 - delay, "myvalue"), 0.01f);
		assertEquals(100f, (Float) timeline.getValue(100 - delay, "myvalue"), 0.01f);
		assertEquals(150f, (Float) timeline.getValue(150 - delay, "myvalue"), 0.01f);
		assertEquals(200f, (Float) timeline.getValue(200 - delay, "myvalue"), 0.01f);
	}

}
