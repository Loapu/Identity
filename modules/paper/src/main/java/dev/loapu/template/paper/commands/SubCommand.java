package dev.loapu.template.paper.commands;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.CommandPermission;
import dev.loapu.template.common.logging.Logger;
import dev.loapu.template.common.messages.Messages;
import dev.loapu.template.common.util.SingletonFactory;
import dev.loapu.template.paper.TemplatePlugin;
import dev.loapu.template.paper.config.ConfigManager;

public abstract class SubCommand extends CommandAPICommand
{
	protected final TemplatePlugin plugin = SingletonFactory.instance(TemplatePlugin.class);
	protected final Logger logger = SingletonFactory.instance(Logger.class);
	protected final Messages messages = SingletonFactory.instance(Messages.class);
	protected final ConfigManager configManager = SingletonFactory.instance(ConfigManager.class);
	
	protected SubCommand(String name, CommandPermission parentPermission)
	{
		super(name);
		withPermission(parentPermission + "." + name);
	}
}
