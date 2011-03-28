package com.gemserk.animation4j.converters;

import java.util.HashMap;
import java.util.Map;

/**
 * Provides type converters for common java2d classes like Color and Point2D.
 * 
 * @author acoppes
 */
public class Converters {

	private static Map<Class<?>, TypeConverter<?>> converters = new HashMap<Class<?>, TypeConverter<?>>();

	private static TypeConverter<Float> floatConverter = new TypeConverter<Float>() {

		@Override
		public float[] copyFromObject(Float f, float[] x) {
			if (x == null)
				x = new float[variables()];
			x[0] = f.floatValue();
			return x;
		}

		@Override
		public Float copyToObject(Float f, float[] x) {
			// as Float is immutable, it will return a new float each time.
			return Float.valueOf(x[0]);
		}

		@Override
		public int variables() {
			return 1;
		}

	};

	/**
	 * This converter will be boxing and unboxing from float to Float and vice versa each time a method is called, so it is not recommended to use, we recommend you to use a TypeConverter of a FloatValue mutable implementation instead.
	 */
	public static TypeConverter<Float> floatValue() {
		// not recommended to use, it will be boxing and unboxing from float to Float and vice versa.
		return floatConverter;
	}

	/**
	 * Returns the registered TypeConverter for the given class.
	 * 
	 * @param clazz
	 *            The class of the type to search for the corresponding TypeConverter.
	 * @return The corresponding TypeConverter.
	 * @throws RuntimeException
	 *             if no converter found for the given class.
	 */
	@SuppressWarnings("unchecked")
	public static <T> TypeConverter<T> converter(Class<T> clazz) {
		TypeConverter<?> converter = converters.get(clazz);
		if (converter == null)
			throw new RuntimeException("failed to get converter for type " + clazz.getCanonicalName());
		return (TypeConverter<T>) converter;
	}

	/**
	 * Register a TypeConverter for a given class, used by Transitions to infer the converter type.
	 * 
	 * @param clazz
	 *            The class of the type of the converter to be registered.
	 * @param typeConverter
	 *            The TypeConverter to be registered for the given class.
	 */
	public static void register(Class<?> clazz, TypeConverter<?> typeConverter) {
		converters.put(clazz, typeConverter);
	}

	public static void init() {
		Converters.register(Float.class, floatConverter);
	}
}