package es.brouse.minecraftauth.auth.provider.command;

import es.brouse.minecraftauth.auth.AuthResult;
import es.brouse.minecraftauth.auth.DiscordAuthProvider;
import es.brouse.minecraftauth.config.ConfigurationFile;
import lombok.AllArgsConstructor;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@AllArgsConstructor
public class DiscordAuthCommand extends ListenerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(DiscordAuthCommand.class);

    private final ConfigurationFile config;

    private final DiscordAuthProvider provider;

    public void registerCommand(JDA instance) {
        final DefaultMemberPermissions permission = DefaultMemberPermissions.enabledFor(
                Permission.ADMINISTRATOR
        );

        final String commandName = config.getCommandConfiguration().getName();
        final String description = config.getCommandConfiguration().getDescription();

        final SlashCommandData command = Commands.slash(commandName, description)
                .addOption(OptionType.STRING, "username", "Your Minecraft username", true)
                .setDefaultPermissions(permission);

        instance.upsertCommand(command).queue(
                success -> registerCommandHandler(commandName, instance),
                error -> logger.error("Failed to register command: " + commandName + " - " + error.getMessage())
        );
    }

    @Override
    public void onSlashCommandInteraction(final SlashCommandInteractionEvent event) {
        final String commandName = config.getCommandConfiguration().getName();

        if (!event.getName().equals(commandName)) return;
        if (event.getUser().isBot()) return;

        final Long userId = event.getUser().getIdLong();
        final String username = event.getOption("username", "", OptionMapping::getAsString);

        final AuthResult result = provider.authenticate(userId, username);

        event.reply(result.getMessage())
                .setEphemeral(true)
                .queue(
                        success -> logger.info("User {} authenticated with result: {}", userId, result),
                        error -> logger.error("Failed to reply to command: " + commandName + " - " + error.getMessage())
                );
    }

    private void registerCommandHandler(String commandName, JDA instance) {
        instance.addEventListener(this);
        logger.info("Registered command: " + commandName);
    }
}
