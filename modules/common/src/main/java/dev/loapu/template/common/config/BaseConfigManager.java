package dev.loapu.template.common.config;

import dev.loapu.template.common.logging.Logger;
import dev.loapu.template.common.util.SingletonFactory;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.yaml.NodeStyle;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.nio.file.Path;

public class BaseConfigManager<T extends BaseConfig>
{
	private final YamlConfigurationLoader loader;
	private final Logger logger = SingletonFactory.instance(Logger.class);
	private final Class<T> clazz;
	private T config;
	private CommentedConfigurationNode node;
	
	public BaseConfigManager(Path configPath, Class<T> clazz)
	{
		loader = YamlConfigurationLoader.builder()
					 .nodeStyle(NodeStyle.BLOCK)
					 .indent(2)
					 .path(configPath)
					 .build();
		this.clazz = clazz;
		try
		{
			node = loader.load();
			config = node.get(clazz);
			loader.save(node);
			config.apply();
			logger.debug("==================================================", "Debug logging is enabled.", "It can be disabled via <span>/template debug</span> or by", "setting <span>enable-debug-logging</span> to <span>false</span> inside the config.", "==================================================");
		}
		catch (ConfigurateException e)
		{
			logger.error("Failed to load configuration file: " + e.getMessage());
		}
	}
	
	public T config()
	{
		return config;
	}
	
	public void loadFromDisk()
	{
		try
		{
			node = loader.load();
			config = node.get(clazz);
			assert config != null;
			config.apply();
		}
		catch (ConfigurateException e)
		{
			logger.error("Failed to load configuration file: " + e.getMessage());
		}
	}
	
	public void saveToDisk()
	{
		try
		{
			node.set(clazz, config);
			loader.save(node);
		}
		catch (ConfigurateException e)
		{
			logger.error("Failed to save configuration file: " + e.getMessage());
		}
	}
}
