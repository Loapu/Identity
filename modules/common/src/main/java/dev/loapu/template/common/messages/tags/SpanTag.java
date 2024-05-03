package dev.loapu.template.common.messages.tags;

import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

public abstract class SpanTag
{
	private static final String SPAN = "span";
	protected static final TagResolver RESOLVER = Placeholder.styling(SPAN, NamedTextColor.AQUA);
}
