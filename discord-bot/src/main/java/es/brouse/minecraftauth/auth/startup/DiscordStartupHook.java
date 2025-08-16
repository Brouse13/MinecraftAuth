package es.brouse.minecraftauth.auth.startup;

import es.brouse.minecraftauth.config.ConfigurationFile;
import lombok.NonNull;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.events.session.ShutdownEvent;
import net.dv8tion.jda.api.hooks.EventListener;

public interface DiscordStartupHook extends EventListener {

    /**
     * Gets the configuration for the Discord bot.
     *
     * @return the FileConfiguration instance containing the bot's settings.
     */
    ConfigurationFile getConfiguration();

    /**
     * Called when the Discord bot is starting up.
     * This method is used to register commands and listeners.
     */
    void onStartup();

    /**
     * Called when the Discord bot is shutting down.
     * This method is used to clean up resources and unregister commands.
     */
    void onShutdown();

    @Override
    default void onEvent(@NonNull GenericEvent event) {
         if (event instanceof ReadyEvent) onStartup();
         if (event instanceof ShutdownEvent) onShutdown();
    }
}
