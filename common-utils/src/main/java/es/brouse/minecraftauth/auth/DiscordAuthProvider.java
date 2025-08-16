package es.brouse.minecraftauth.auth;

public interface DiscordAuthProvider {
    AuthResult authenticate(Long userId, String username);
}

