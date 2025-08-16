package es.brouse.minecraftauth.auth;

import es.brouse.minecraftauth.registry.PlayerRegistry;
import es.brouse.minecraftauth.whitelist.WhitelistProcessor;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CommonAuthProvider implements DiscordAuthProvider {

    private final PlayerRegistry registry;
    private final WhitelistProcessor whitelistProcessor;
    @Override
    public AuthResult authenticate(Long userId, String username) {
        String lastUsername = null;

        // Check if the userId is valid
        if (username.isEmpty())
            return AuthResult.INVALID_USERNAME;

        // Check if the username is already taken
        if (registry.isPlayerUsername(username))
            return AuthResult.USERNAME_TAKEN;

        // If the user is registered try to unregister them first
        if (registry.isRegistered(userId)) {
            lastUsername = registry.unregisterPlayer(userId);
            if (!whitelistProcessor.unregister(lastUsername))  {
                return AuthResult.ALREADY_REGISTERED;
            }
        }

        // Try to register the player on the users.json
        if (!registry.registerPlayer(userId, username, false))
            return AuthResult.ERROR;

        // Try to add the player to the whitelist
        return whitelistProcessor.register(username) ?
                lastUsername == null ? AuthResult.SUCCESS : AuthResult.CHANGED_USERNAME :
                AuthResult.WHITELIST_ERROR;
    }
}
