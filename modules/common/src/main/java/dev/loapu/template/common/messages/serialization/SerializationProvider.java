package dev.loapu.template.common.messages.serialization;

import dev.loapu.template.common.messages.tags.CommonTags;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.minimessage.tag.standard.StandardTags;
import org.checkerframework.checker.nullness.qual.NonNull;

public abstract class SerializationProvider
{
	public static @NonNull MiniMessage loggingSerializer()
	{
		return MiniMessage.builder().tags(TagResolver.resolver(StandardTags.defaults(), CommonTags.span())).build();
	}
	
	public static @NonNull MiniMessage messageSerializer()
	{
		return MiniMessage.builder().tags(TagResolver.resolver(StandardTags.defaults())).build();
	}
}
