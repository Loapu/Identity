package dev.loapu.template.common.util;

import dev.loapu.template.common.logging.Logger;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class SingletonFactory
{
	private static final Map<Class<?>, Object> instances = new ConcurrentHashMap<>();
	
	public static <T> T instance(Class<T> clazz)
	{
		return clazz.cast(instances.computeIfAbsent(clazz, SingletonFactory::createInstance));
	}
	
	private static <T> T createInstance(Class<T> clazz)
	{
		try
		{
			Constructor<T> constructor = clazz.getDeclaredConstructor();
			if (!constructor.canAccess(null)) constructor.setAccessible(true);
			if (instances.containsKey(Logger.class))
				instance(Logger.class).debug("[SF] Creating new instance of <span>" + clazz.getSimpleName() + "</span>.");
			return constructor.newInstance();
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}
	
	public static <T> void deposit(Class<T> clazz, T instance)
	{
		if (instances.containsKey(Logger.class))
			instance(Logger.class).debug("[SF] Depositing instance of <span>" + clazz.getSimpleName() + "</span>.");
		instances.put(clazz, instance);
	}
}
