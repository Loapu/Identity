package dev.loapu.template.paper.commands.base;

import dev.loapu.template.common.util.SingletonFactory;
import dev.loapu.template.paper.TemplatePlugin;
import dev.loapu.template.paper.commands.Command;
import net.kyori.adventure.key.Key;
import org.bukkit.entity.Player;

public class BaseCommand extends Command
{
	private BaseCommand()
	{
		super(SingletonFactory.instance(TemplatePlugin.class).getName().toLowerCase());
		command.executes((sender, args) -> {
			var messageKey = Key.key("command.base");
			var name = sender instanceof Player player ? player.displayName() : sender.name();
			var message = sender instanceof Player player ? messages.render(messageKey, player.locale(), name) : messages.render(messageKey, name);
			sender.sendMessage(message);
		});
		subCommands.add(new BaseDebug(command.getPermission()));
		subCommands.add(new BaseReload(command.getPermission()));
		register();
	}
}
