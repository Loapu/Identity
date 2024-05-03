package dev.loapu.template.paper;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import dev.loapu.template.common.logging.Logger;
import dev.loapu.template.common.messages.Messages;
import dev.loapu.template.common.util.SingletonFactory;
import dev.loapu.template.paper.commands.base.BaseCommand;
import dev.loapu.template.paper.config.ConfigManager;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Set;

public class TemplatePlugin extends JavaPlugin
{
	private final Set<Class<?>> commands = Set.of(BaseCommand.class);
	private Logger logger;
	private ConfigManager configManager;
	private Messages messages;
	
	@Override
	public void onLoad()
	{
		SingletonFactory.deposit(Logger.class, new Logger(getComponentLogger()));
		logger = SingletonFactory.instance(Logger.class);
		CommandAPI.onLoad(new CommandAPIBukkitConfig(this).silentLogs(true).shouldHookPaperReload(true).useMojangMappings(true).usePluginNamespace());
		SingletonFactory.deposit(ConfigManager.class, new ConfigManager(getDataFolder().toPath().resolve("config.yml")));
		messages = SingletonFactory.instance(Messages.class);
		configManager = SingletonFactory.instance(ConfigManager.class);
		SingletonFactory.deposit(TemplatePlugin.class, this);
		logger.debug("Plugin successfully loaded.");
	}
	
	@Override
	public void onEnable()
	{
		logger.debug("Enabling plugin...");
		var start = System.currentTimeMillis();
		CommandAPI.onEnable();
		logger.info(messages.translate(Component.translatable("log.info.hello-world", Component.text(Bukkit.getName()))));
		registerCommands();
		logger.debug("Plugin successfully enabled in <span>" + (System.currentTimeMillis() - start) + " ms</span>.");
	}
	
	@Override
	public void onDisable()
	{
		CommandAPI.onDisable();
		configManager.saveToDisk();
	}
	
	private void registerCommands()
	{
		commands.forEach(SingletonFactory::instance);
	}
}
