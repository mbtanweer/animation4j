package com.gemserk.animation4j.interpolator;

import com.gemserk.animation4j.converters.TypeConverter;

/**
 * Provides an implementation of the Interpolator interface using a TypeConverter to convert from the object to a float[] and vice versa, and a Interpolator<float[]> to interpolate float[] values.
 * 
 * @param <T>
 * @author acoppes
 */
public class GenericInterpolator<T> implements Interpolator<T> {

	private final TypeConverter<T> converter;

	private float[] a;

	private float[] b;
	
	private T object;

	private Interpolator<float[]> interpolator;

	public GenericInterpolator(TypeConverter<T> converter, Interpolator<float[]> floatArrayInterpolator) {
		this.converter = converter;
		this.interpolator = floatArrayInterpolator;
	}

	public T interpolate(T t1, T t2, float t) {
		a = converter.copyFromObject(t1, a);
		b = converter.copyFromObject(t2, b);
		object = converter.copyToObject(object, interpolator.interpolate(a, b, t));
		return object;
	}

}