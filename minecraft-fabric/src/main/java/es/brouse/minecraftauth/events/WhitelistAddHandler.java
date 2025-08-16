package es.brouse.minecraftauth.events;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import es.brouse.minecraftauth.whitelist.WhitelistProcessor;
import lombok.AllArgsConstructor;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.Whitelist;
import net.minecraft.server.WhitelistEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Optional;
import java.util.UUID;

import static es.brouse.minecraftauth.MinecraftAuth.MOD_ID;

@AllArgsConstructor
public class WhitelistAddHandler implements WhitelistProcessor {
    public static final Logger logger = LoggerFactory.getLogger(MOD_ID);
    public static final String MINECRAFT_API = "https://api.mojang.com/users/profiles/minecraft/";

    private MinecraftServer server;

    private boolean addToWhitelist(String username) {
        final Whitelist whitelist = server.getPlayerManager().getWhitelist();

        try {
            // Get the player username UUID from Minecraft API
            final UUID uuid = getPlayerUUID(username).orElseThrow(
                    () -> new IllegalArgumentException("Player not found")
            );

            // Get profile and add to whitelist
            final GameProfile gameProfile = new GameProfile(uuid, username);
            whitelist.add(new WhitelistEntry(gameProfile));
            whitelist.save();

            return true;
        }catch (IOException e) {
            logger.warn("Failed to add player '{}' with UUID {} to whitelist.", username, e.getMessage());
            return false;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private boolean removeFromWhitelist(String username) {
        final Whitelist whitelist = server.getPlayerManager().getWhitelist();

        try {
            // Get the player username UUID from Minecraft API
            final UUID uuid = getPlayerUUID(username).orElseThrow(
                    () -> new IllegalArgumentException("Player not found")
            );

            // Get profile and add to whitelist
            final GameProfile gameProfile = new GameProfile(uuid, username);
            whitelist.remove(new WhitelistEntry(gameProfile));
            whitelist.save();

            // Kick the player if they are online
            final ServerPlayerEntity player = server.getPlayerManager().getPlayer(uuid);
            if (player != null && !player.isDisconnected()) {
                player.networkHandler.disconnect(
                        Text.of("You have been removed from the whitelist.")
                );
            }

            return true;
        }catch (IOException e) {
            logger.warn("Failed to remove player '{}' with UUID {} from the whitelist.", username, e.getMessage());
            return false;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private Optional<UUID> getPlayerUUID(String username) {
        try {
            URL url = URI.create(MINECRAFT_API + username).toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // User not found
            if (connection.getResponseCode() != 200) return Optional.empty();


            final InputStreamReader reader = new InputStreamReader(connection.getInputStream());
            JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
            String id = json.get("id").getAsString();

            // Convertir a UUID con guiones
            return Optional.of(UUID.fromString(id.replaceFirst(
                    "(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})",
                    "$1-$2-$3-$4-$5"
            )));
        }catch (IOException e) {
            logger.warn("Error fetching UUID for player {}: {}", username, e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public boolean register(String username) {
        return addToWhitelist(username);
    }

    @Override
    public boolean unregister(String username) {
        return removeFromWhitelist(username);
    }
}
