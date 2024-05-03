package dev.loapu.template.common.messages.tags;

import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.checkerframework.checker.nullness.qual.NonNull;

public abstract class CommonTags
{
	public static @NonNull TagResolver span()
	{
		return SpanTag.RESOLVER;
	}
}
