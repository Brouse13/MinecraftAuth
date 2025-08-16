package es.brouse.minecraftauth.config;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ConfigurationFile extends TypeAdapter<ConfigurationFile> {
    // General configuration
    private String guildId;
    private String token;
    private String verifiedRole;
    private List<String> adminRoles;

    private CommandConfiguration commandConfiguration;

    @Override
    public void write(JsonWriter writer, ConfigurationFile object) throws IOException {
        writer.beginObject();
        // Common
        writer.name("guild_id").value(object.getGuildId());
        writer.name("token").value(object.getToken());
        writer.name("verified_role").value(object.getVerifiedRole());
        writer.name("admin_roles").beginArray();
        for (String role : object.getAdminRoles()) writer.value(role);
        writer.endArray();

        // Command
        writer.name("command");
        new CommandConfiguration().write(writer, object.getCommandConfiguration());

        // End object
        writer.endObject();
    }

    @Override
    public ConfigurationFile read(JsonReader reader) throws IOException {
        var builder = ConfigurationFile.builder();

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case "guild_id":
                    builder.guildId(reader.nextString());
                    break;
                case "token":
                    builder.token(reader.nextString());
                    break;
                case "verified_role":
                    builder.verifiedRole(reader.nextString());
                    break;
                case "admin_roles":
                    List<String> adminRoles = new ArrayList<>();
                    reader.beginArray();
                    while (reader.hasNext()) adminRoles.add(reader.nextString());
                    reader.endArray();
                    builder.adminRoles(adminRoles);
                    break;
                case "command":
                    builder.commandConfiguration(new CommandConfiguration().read(reader));
                    break;
                default:
                    reader.skipValue();
            }
        }
        reader.endObject();
        return builder.build();
    }

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
