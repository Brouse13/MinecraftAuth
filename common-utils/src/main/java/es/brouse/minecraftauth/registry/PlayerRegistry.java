package es.brouse.minecraftauth.registry;

/**
 * Interface to define common methods to register a new
 * player into the system.
 * Each player will have a unique ID associated with it.
 */
public interface PlayerRegistry extends SavingRegister {

    /**
     * Checks if a player is registered in the system.
     *
     * @param playerId Player id to check
     * @return If the player is registered in the system
     */
    boolean isRegistered(Long playerId);

    /**
     * Get if the given username is associated with a player ID.
     *
     * @param username Player name to check
     * @return True if the username is associated with a player ID, false otherwise
     */
    boolean isPlayerUsername(String username);

    /**
     * Get the player userName associated with a player id.
     *
     * @param id Player id to get the username for
     * @return The player userName associated with the player id, or null if not found
     */
    String getPlayerUserName(Long id);

    /**
     * Registers a player in the system.
     *
     * @param id Associated ID to register with
     * @param playerName Player name to register
     * @param force If true will replace the existing registration
     * @return True if the player was registered successfully, false otherwise
     */
    boolean registerPlayer(Long id, String playerName , boolean force);

    /**
     * Unregisters a player from the system.
     *
     * @param id Player name to unregister
     * @return The player userName associated with the player id, or null if not found
     */
    String unregisterPlayer(Long id);
}
