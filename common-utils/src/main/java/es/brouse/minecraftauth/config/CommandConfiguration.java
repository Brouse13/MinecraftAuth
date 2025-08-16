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
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CommandConfiguration extends TypeAdapter<CommandConfiguration> {
    private String name;
    private String description;
    private String usage;
    private List<String> aliases;

    @Override
    public void write(JsonWriter writer, CommandConfiguration object) throws IOException {
        writer.beginObject();
        writer.name("command").beginObject();
        writer.name("name").value(object.getName());
        writer.name("description").value(object.getDescription());
        writer.name("usage").value(object.getUsage());
        writer.name("aliases").beginArray();
        for (String alias : object.getAliases()) writer.value(alias);
        writer.endArray();
        writer.endObject();
    }

    @Override
    public CommandConfiguration read(JsonReader reader) throws IOException {
        final var builder = CommandConfiguration.builder();

        reader.beginObject();
        while (reader.hasNext()) {
            String tokenName = reader.nextName();
            switch (tokenName) {
                case "name":
                    builder.name(reader.nextString());
                    break;
                case "description":
                    builder.description(reader.nextString());
                    break;
                case "usage":
                    builder.usage(reader.nextString());
                    break;
                case "aliases":
                    List<String> aliases = new ArrayList<>();
                    reader.beginArray();
                    while (reader.hasNext()) aliases.add(reader.nextString());
                    reader.endArray();
                    builder.aliases(aliases);
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
        return "CommandConfiguration{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", usage='" + usage + '\'' +
                ", aliases=" + aliases +
                '}';
    }
}
