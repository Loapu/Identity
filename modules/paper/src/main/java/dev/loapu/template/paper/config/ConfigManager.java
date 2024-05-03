package dev.loapu.template.paper.config;

import dev.loapu.template.common.config.BaseConfigManager;

import java.nio.file.Path;

public class ConfigManager extends BaseConfigManager<PaperConfig>
{
	public ConfigManager(Path configPath)
	{
		super(configPath, PaperConfig.class);
	}
}
