package es.brouse.minecraftauth.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ConfigurationFile  {
    @JsonProperty("guild_id")
    private String guildId;

    @JsonProperty("token")
    private String token;

    @JsonProperty("verified_role")
    private String verifiedRole;

    @JsonProperty("admin_roles")
    private List<String> adminRoles;

    @JsonProperty("command")
    private CommandConfiguration commandConfiguration;

    @Override
    public String toString() {
        return "ConfigurationFile{" +
                "guildId='" + guildId + '\'' +
                ", token='" + token + '\'' +
                ", verifiedRole='" + verifiedRole + '\'' +
                ", adminRoles=" + adminRoles +
                ", commandConfiguration=" + commandConfiguration +
                '}';
    }
}
