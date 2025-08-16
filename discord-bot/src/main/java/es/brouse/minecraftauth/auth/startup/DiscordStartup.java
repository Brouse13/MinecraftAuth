package es.brouse.minecraftauth.auth.startup;

import lombok.experimental.UtilityClass;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import net.dv8tion.jda.internal.utils.JDALogger;


import static net.dv8tion.jda.api.requests.GatewayIntent.*;

@UtilityClass
public class DiscordStartup {
    private static JDA instance;

    /**
     * Get the JDA instance for the Discord bot.
     *
     * @return The JDA instance.
     */
    public static JDA getInstance(DiscordStartupHook hook) throws InterruptedException {
        if (instance == null) instance = buildInstance(hook);

        return instance;
    }

    /**
     * Builds the JDA instance for the Discord bot. This method is called when the instance is null.
     *
     * @return The JDA instance.
     */
    private static JDA buildInstance(DiscordStartupHook hook) throws InterruptedException {
        JDALogger.setFallbackLoggerEnabled(false);

        // Implement the shutdown hook to ensure the bot cleans up resources on JVM shutdown
        Runtime.getRuntime().addShutdownHook(new Thread(hook::onShutdown, "Shutdown Hook"));

        // Create the JDA instance with the provided token and intents
        return JDABuilder.createDefault(hook.getConfiguration().getToken())
                .setActivity(Activity.playing("Minecraft Authentication"))
                .setEnabledIntents(
                        GUILD_MESSAGES,
                        GUILD_MESSAGE_REACTIONS,
                        GUILD_EXPRESSIONS,
                        GUILD_MEMBERS,
                        MESSAGE_CONTENT,
                        SCHEDULED_EVENTS
                )
                .addEventListeners(hook)
                .disableCache(CacheFlag.VOICE_STATE)
                .build()
                .awaitReady();
    }
}