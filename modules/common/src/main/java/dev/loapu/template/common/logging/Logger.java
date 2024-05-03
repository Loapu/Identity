package dev.loapu.template.common.logging;

import dev.loapu.template.common.messages.serialization.SerializationProvider;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.util.Arrays;

public final class Logger
{
	private final MiniMessage textSerializer;
	private final ComponentLogger componentLogger;
	private boolean enableDebugLogging = false;
	
	public Logger(ComponentLogger componentLogger)
	{
		this.componentLogger = componentLogger;
		this.textSerializer = SerializationProvider.loggingSerializer();
	}
	
	public void enableDebugLogging(boolean enableDebugLogging)
	{
		this.enableDebugLogging = enableDebugLogging;
	}
	
	public void info(String... message)
	{
		Arrays.stream(message).map(textSerializer::deserialize).forEach(componentLogger::info);
	}
	
	public void warn(String... message)
	{
		Arrays.stream(message).map(textSerializer::deserialize).forEach(componentLogger::warn);
	}
	
	public void error(String... message)
	{
		Arrays.stream(message).map(textSerializer::deserialize).forEach(componentLogger::error);
	}
	
	public void debug(String... message)
	{
		if (!enableDebugLogging) return;
		Arrays.stream(message).map(msg -> Component.text("[DEBUG] ").color(NamedTextColor.LIGHT_PURPLE).append(textSerializer.deserialize(msg).colorIfAbsent(NamedTextColor.LIGHT_PURPLE))).forEach(componentLogger::info);
	}
}
