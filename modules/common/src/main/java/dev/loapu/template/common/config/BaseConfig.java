package dev.loapu.template.common.config;

import dev.loapu.template.common.logging.Logger;
import dev.loapu.template.common.messages.Messages;
import dev.loapu.template.common.util.SingletonFactory;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Matches;

import java.util.Locale;

@ConfigSerializable
public class BaseConfig
{
	private boolean enableDebugLogging = false;
	@Matches(Messages.SUPPORTED_LOCALES_REGEX)
	private String defaultLocale = "en";
	
	public final boolean enableDebugLogging()
	{
		return enableDebugLogging;
	}
	
	public final Locale defaultLocale()
	{
		return Locale.forLanguageTag(defaultLocale);
	}
	
	public final void enableDebugLogging(boolean enableDebugLogging)
	{
		this.enableDebugLogging = enableDebugLogging;
		apply();
	}
	
	public final void defaultLocale(String defaultLocale)
	{
		this.defaultLocale = defaultLocale;
		apply();
	}
	
	public void apply()
	{
		var logger = SingletonFactory.instance(Logger.class);
		logger.enableDebugLogging(enableDebugLogging);
		logger.debug("Debug logging is now <span>" + (enableDebugLogging ? "enabled" : "disabled") + "</span>.");
		Messages.defaultLocale(defaultLocale());
		logger.debug("Default locale is now <span>" + defaultLocale().getDisplayName() + "</span>.");
	}
}
