package es.brouse.minecraftauth.whitelist;

public interface WhitelistProcessor {
    /**
     * Registers a player to the whitelist.
     *
     * @param username Username of the player to register
     * @return true if the player was successfully registered, false otherwise
     */
    boolean register(String username);

    /**
     * Unregisters a player from the whitelist.
     *
     * @param username Username of the player to unregister
     * @return true if the player was successfully unregistered, false otherwise
     */
    boolean unregister(String username);
}
