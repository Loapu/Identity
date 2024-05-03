package dev.loapu.template.paper.commands.base;

import dev.jorel.commandapi.CommandPermission;
import dev.loapu.template.paper.commands.SubCommand;
import net.kyori.adventure.key.Key;
import org.bukkit.entity.Player;

public class BaseDebug extends SubCommand
{
	protected BaseDebug(CommandPermission parentPermission)
	{
		super("debug", parentPermission);
		executes((sender, args) -> {
			var debug = configManager.config().enableDebugLogging();
			configManager.config().enableDebugLogging(!debug);
			var messageKey = debug ? Key.key("command.base.debug.disabled") : Key.key("command.base.debug.enabled");
			var locale = sender instanceof Player player ? player.locale() : configManager.config().defaultLocale();
			sender.sendMessage(messages.render(messageKey, locale));
		});
	}
}
