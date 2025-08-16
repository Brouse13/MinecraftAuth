package es.brouse.minecraftauth;

import es.brouse.minecraftauth.auth.CommonAuthProvider;
import es.brouse.minecraftauth.auth.startup.DiscordStartup;
import es.brouse.minecraftauth.auth.provider.command.DiscordAuthCommand;
import es.brouse.minecraftauth.config.ConfigurationFile;
import es.brouse.minecraftauth.registry.MapPlayerRegistry;
import es.brouse.minecraftauth.utils.ObjectFileReader;
import es.brouse.minecraftauth.whitelist.WhitelistProcessor;
import lombok.Getter;
import net.dv8tion.jda.api.JDA;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
public class DiscordBot {
    private static final Logger logger = LoggerFactory.getLogger(DiscordBot.class);
    private static final MapPlayerRegistry registry = new MapPlayerRegistry();
    private final WhitelistProcessor whitelistProcessor;
    private JDA jda;

    public DiscordBot(WhitelistProcessor whitelistProcessor) {
        this.whitelistProcessor = whitelistProcessor;
    }

    public void initialize() throws Exception {
        logger.info("Initializing discord Bot...");

        // Make sure the registry is initialized before reading the configuration
        ConfigurationFile configuration = ObjectFileReader.readInternalFile(ConfigurationFile.class, "configuration.json");
        jda = DiscordStartup.getInstance(new DiscordBotStartup(registry, configuration));

        // The player registry should not be defined in the DiscordAuthProvider,
        final CommonAuthProvider provider = new CommonAuthProvider(registry, whitelistProcessor);
        final var discordAuthCommand = new DiscordAuthCommand(configuration, provider);
        discordAuthCommand.registerCommand(jda);

        logger.info("Discord Bot initialized successfully.");
    }

    public void shutdown() {
        if (jda == null) {
            logger.warn("Discord Bot is not initialized, nothing to shutdown.");
            return;
        }

        logger.info("Shutting down Discord Bot...");
        jda.shutdown();
        logger.info("Discord Bot shutdown complete.");
    }
}
