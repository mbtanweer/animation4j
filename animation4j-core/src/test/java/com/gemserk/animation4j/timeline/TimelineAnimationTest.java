package com.gemserk.animation4j.timeline;

import static org.junit.Assert.*;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsEqual;
import org.junit.Test;

public class TimelineAnimationTest {

	public static class IsNear {

		public static Matcher<Float> isNear(float x, float range) {
			return new Near(x, range);
		}

	}

	public static class Near extends BaseMatcher<Float> {

		private final float x;

		private final float range;

		public Near(float x, float range) {
			this.x = x;
			this.range = range;
		}

		@Override
		public boolean matches(Object o) {
			if (!(o instanceof Float))
				return false;
			Float x1 = (Float) o;
			return Math.abs(x1 - x) < range;
		}

		@Override
		public void describeTo(Description description) {

		}
	}

	@Test
	public void shouldNotBeStartedBeforeDelay() {

		TimelineAnimation animation = new TimelineAnimation(new TimelineBuilder() {
			{
				delay(100);
				value("myvalue", new TimelineValueBuilder<Float>() {
					{
						keyFrame(0, 100f, null);
						keyFrame(100, 200f, null);
					}
				});
			}
		}.build());

		animation.resume();

		assertFalse(animation.isStarted());
		animation.update(100);
		assertTrue(animation.isStarted());
		animation.update(100);
		assertTrue(animation.isFinished());
	}

	@Test
	public void shouldIterateIterationCountTimes() {

		TimelineAnimation animation = new TimelineAnimation(new TimelineBuilder() {
			{
				value("myvalue", new TimelineValueBuilder<Float>() {
					{
						keyFrame(0, 100f, null);
						keyFrame(100, 200f, null);
					}
				});
			}
		}.build());

		animation.start(2);

		// assertThat(animation.getIteration(), IsEqual.equalTo(1));
		assertThat((Float) animation.getValue("myvalue"), IsEqual.equalTo(100f));
		animation.update(99.99f);
		assertThat((Float) animation.getValue("myvalue"), IsNear.isNear(200f, 0.1f));
		animation.update(0.01f);
		// assertThat(animation.getIteration(), IsEqual.equalTo(2));
		assertThat(animation.isFinished(), IsEqual.equalTo(false));
		assertThat((Float) animation.getValue("myvalue"), IsNear.isNear(100f, 0.1f));
		animation.update(100f);
		assertThat(animation.isFinished(), IsEqual.equalTo(true));
		assertThat((Float) animation.getValue("myvalue"), IsNear.isNear(200f, 0.1f));
	}

	@Test
	public void shouldIterateAnIndefiniteNumberOfTimes() {

		TimelineAnimation animation = new TimelineAnimation(new TimelineBuilder() {
			{
				value("myvalue", new TimelineValueBuilder<Float>() {
					{
						keyFrame(0, 100f, null);
						keyFrame(100, 200f, null);
					}
				});
			}
		}.build());

		animation.start(0);

		// assertThat(animation.getIteration(), IsEqual.equalTo(1));
		assertThat((Float) animation.getValue("myvalue"), IsEqual.equalTo(100f));
		animation.update(99.99f);
		assertThat((Float) animation.getValue("myvalue"), IsNear.isNear(200f, 0.1f));
		animation.update(0.01f);
		// assertThat(animation.getIteration(), IsEqual.equalTo(2));
		assertThat(animation.isFinished(), IsEqual.equalTo(false));
		assertThat((Float) animation.getValue("myvalue"), IsNear.isNear(100f, 0.1f));
		animation.update(99.99f);
		assertThat(animation.isFinished(), IsEqual.equalTo(false));
		assertThat((Float) animation.getValue("myvalue"), IsNear.isNear(200f, 0.1f));

		animation.update(9999999f);
		assertThat(animation.isFinished(), IsEqual.equalTo(false));
		// assertThat(animation.getIteration(), IsEqual.equalTo(100));

	}

	@Test
	public void shouldBeStartedBetweenIterationsWithDelays() {

		TimelineAnimation animation = new TimelineAnimation(new TimelineBuilder() {
			{
				value("myvalue", new TimelineValueBuilder<Float>() {
					{
						delay(100);
						keyFrame(0, 100f, null);
						keyFrame(100, 200f, null);
					}
				});
			}
		}.build());

		animation.start(2);
		assertThat(animation.isStarted(), IsEqual.equalTo(false));
		animation.update(100);
		assertThat(animation.isStarted(), IsEqual.equalTo(true));
		animation.update(101);
		assertThat(animation.isStarted(), IsEqual.equalTo(true));
	}

	@Test
	public void shouldPlayInReversIfAlternateDirection() {

		TimelineAnimation animation = new TimelineAnimation(new TimelineBuilder() {
			{
				value("myvalue", new TimelineValueBuilder<Float>() {
					{
						keyFrame(0, 200f, null);
						keyFrame(100, 900f, null);
						keyFrame(150, 1900f, null);
						keyFrame(200, 2000f, null);
					}
				});
			}
		}.build());
		
		animation.setAlternateDirection(true);
		animation.start(2);
		
		assertThat((Float) animation.getValue("myvalue"), IsNear.isNear(200f, 0.1f));
		
		animation.update(100);
		assertThat((Float) animation.getValue("myvalue"), IsNear.isNear(900f, 0.1f));

		animation.update(100);
		assertThat(animation.isStarted(), IsEqual.equalTo(true));
		assertThat((Float) animation.getValue("myvalue"), IsNear.isNear(2000f, 0.1f));

		animation.update(50);
		assertThat(animation.isStarted(), IsEqual.equalTo(true));
		assertThat((Float) animation.getValue("myvalue"), IsNear.isNear(1900f, 0.1f));
		assertThat(animation.isFinished(), IsEqual.equalTo(false));
		
		animation.update(151);
		assertThat(animation.isFinished(), IsEqual.equalTo(true));
		assertThat((Float) animation.getValue("myvalue"), IsNear.isNear(200f, 0.1f));

	}
	
	// test reverse Animation with delay
	
	// test restart is not setting iteration to 0? 

}
