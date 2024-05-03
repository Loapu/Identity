package dev.loapu.template.paper.commands.base;

import dev.jorel.commandapi.CommandPermission;
import dev.loapu.template.paper.commands.SubCommand;
import net.kyori.adventure.key.Key;
import org.bukkit.entity.Player;

public class BaseReload extends SubCommand
{
	protected BaseReload(CommandPermission parentPermission)
	{
		super("reload", parentPermission);
		executes((sender, args) -> {
			configManager.loadFromDisk();
			sender.sendMessage(messages.render(Key.key("command.base.reload"), sender instanceof Player player ? player.locale() : configManager.config().defaultLocale()));
		});
	}
}
