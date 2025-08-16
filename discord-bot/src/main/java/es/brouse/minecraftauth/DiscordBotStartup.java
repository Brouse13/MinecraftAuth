package es.brouse.minecraftauth;

import es.brouse.minecraftauth.auth.startup.DiscordStartupHook;
import es.brouse.minecraftauth.config.ConfigurationFile;
import es.brouse.minecraftauth.registry.PlayerRegistry;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@AllArgsConstructor
public class DiscordBotStartup implements DiscordStartupHook {
    private static final Logger logger = LoggerFactory.getLogger(DiscordBotStartup.class);
    private final PlayerRegistry registry;
    @Getter private final ConfigurationFile configuration;

    @Override
    public void onStartup() {
        logger.info("Loading player registry from file...");
        registry.load("users.json");
    }

    @Override
    public void onShutdown() {
        logger.info("Saving player registry to file...");
        registry.save("users.json");
    }
}
