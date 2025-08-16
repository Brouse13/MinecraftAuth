package es.brouse.minecraftauth;

import es.brouse.minecraftauth.events.WhitelistAddHandler;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MinecraftAuth implements DedicatedServerModInitializer {
    public static final String MOD_ID = "minecraftauth";
    public static final Logger logger = LoggerFactory.getLogger(MOD_ID);
    private WhitelistAddHandler whitelistAddHandler;
    private DiscordBot discordBot;
    @Override
    public void onInitializeServer() {

        ServerLifecycleEvents.SERVER_STARTED.register(dedicatedServer -> {
            whitelistAddHandler = new WhitelistAddHandler(dedicatedServer);
            loadDiscordBot();
        });

        ServerLifecycleEvents.SERVER_STOPPING.register(dedicatedServer -> discordBot.shutdown());
    }

    private void loadDiscordBot() {
        discordBot = new DiscordBot(whitelistAddHandler);
        try {
            discordBot.initialize();
        } catch (Exception e) {
            logger.error("Failed to initialize Discord bot: {}", e.getMessage());
        }
    }
}
