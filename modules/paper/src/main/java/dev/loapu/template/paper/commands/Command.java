package dev.loapu.template.paper.commands;

import dev.jorel.commandapi.CommandAPICommand;
import dev.loapu.template.common.logging.Logger;
import dev.loapu.template.common.messages.Messages;
import dev.loapu.template.common.util.SingletonFactory;
import dev.loapu.template.paper.TemplatePlugin;
import dev.loapu.template.paper.config.ConfigManager;

import java.util.ArrayList;
import java.util.List;

public abstract class Command
{
	protected final TemplatePlugin plugin = SingletonFactory.instance(TemplatePlugin.class);
	protected final Logger logger = SingletonFactory.instance(Logger.class);
	protected final Messages messages = SingletonFactory.instance(Messages.class);
	protected final ConfigManager configManager = SingletonFactory.instance(ConfigManager.class);
	protected final CommandAPICommand command;
	protected final List<CommandAPICommand> subCommands = new ArrayList<>();
	
	protected Command(String name)
	{
		logger.debug("Registering command <span>/" + name + "</span>...");
		command = new CommandAPICommand(name).withPermission(plugin.getName().toLowerCase() + ".command." + name);
	}
	
	protected final void register()
	{
		command.withSubcommands(subCommands.toArray(new CommandAPICommand[0]));
		command.register();
		logger.debug("Command registered with <span>" + subCommands.size() + "</span> sub commands.");
	}
}
