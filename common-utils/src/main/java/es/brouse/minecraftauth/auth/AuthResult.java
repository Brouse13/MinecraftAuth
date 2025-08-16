package es.brouse.minecraftauth.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AuthResult {
    SUCCESS("Authentication successful."),
    USERNAME_TAKEN("Username is already taken by another player use."),
    INVALID_USERNAME("Invalid username provided."),
    ALREADY_REGISTERED("You have already a registered user."),
    USER_NOT_FOUND("User not found."),
    ERROR("An error occurred during authentication."),
    WHITELIST_ERROR("Failed to add user to the whitelist. Please try again later."),
    CHANGED_USERNAME("Your registered username has changed, last one lost access to the server."),;

    private final String message;
}
