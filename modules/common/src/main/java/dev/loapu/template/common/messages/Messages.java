package dev.loapu.template.common.messages;

import dev.loapu.template.common.logging.Logger;
import dev.loapu.template.common.messages.serialization.SerializationProvider;
import dev.loapu.template.common.util.SingletonFactory;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.TranslatableComponent;
import net.kyori.adventure.text.TranslationArgument;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.translation.GlobalTranslator;
import net.kyori.adventure.translation.TranslationRegistry;
import net.kyori.adventure.util.UTF8ResourceBundleControl;

import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class Messages
{
	public static final String SUPPORTED_LOCALES_REGEX = "en|de";
	private static final TranslationRegistry registry = TranslationRegistry.create(Key.key("loapu:template"));
	private static Locale defaultLocale = Locale.ENGLISH;
	private final Logger logger = SingletonFactory.instance(Logger.class);
	private final MiniMessage textSerializer = SerializationProvider.messageSerializer();
	
	public Messages()
	{
		logger.debug("Registering translations...");
		var supportedLocales = Arrays.stream(SUPPORTED_LOCALES_REGEX.split("\\|")).map(Locale::forLanguageTag).toList();
		logger.debug("Supported locales: <span>" + supportedLocales.stream().map(Locale::getDisplayName).reduce((a, b) -> a + "</span>, <span>" + b).orElse("none") + "</span>.");
		supportedLocales.forEach(locale -> {
			ResourceBundle bundle = ResourceBundle.getBundle("Messages_" + locale.toLanguageTag(), locale, UTF8ResourceBundleControl.get());
			registry.registerAll(locale, bundle, true);
		});
		logger.debug("Default locale: <span>" + defaultLocale.getDisplayName() + "</span>.");
		defaultLocale(defaultLocale);
		logger.debug("Translations successfully registered.");
	}
	
	public static void defaultLocale(Locale defaultLocale)
	{
		var translator = GlobalTranslator.translator();
		translator.removeSource(registry);
		registry.defaultLocale(defaultLocale);
		Messages.defaultLocale = defaultLocale;
		translator.addSource(registry);
	}
	
	public Component render(TranslatableComponent component, Locale locale)
	{
		logger.debug("Translating component: <span>" + component.key() + "</span>.");
		return GlobalTranslator.render(component, locale);
		
	}
	
	public Component render(TranslatableComponent component)
	{
		return render(component, defaultLocale);
	}
	
	public String translate(TranslatableComponent component)
	{
		return translate(component, defaultLocale);
	}
	
	public String translate(TranslatableComponent component, Locale locale)
	{
		var args = component.arguments();
		var textComponents = args.stream().map(TranslationArgument::value).filter(TextComponent.class::isInstance).map(TextComponent.class::cast).map(TextComponent::content).toArray();
		return Objects.requireNonNull(registry.translate(component.key(), locale)).format(textComponents, new StringBuffer(), null).toString();
	}
	
	public Component render(Key key)
	{
		return render(key, defaultLocale);
	}
	
	public Component render(Key key, ComponentLike... args)
	{
		return render(key, defaultLocale, args);
	}
	
	public Component render(Key key, Locale locale)
	{
		return render(key, locale, Component.empty());
	}
	
	public Component render(Key key, Locale locale, ComponentLike... args)
	{
		var translatable = Component.translatable(key.value(), args);
		var rendered = render(translatable, locale);
		var message = textSerializer.serialize(rendered);
		message = message.replaceAll("\\\\<", "<");
		return textSerializer.deserialize(message);
	}
}
