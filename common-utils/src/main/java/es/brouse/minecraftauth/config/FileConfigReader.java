package es.brouse.minecraftauth.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@UtilityClass
public class FileConfigReader {
    private static final Gson gson;

    static {
        gson = new GsonBuilder()
                .registerTypeHierarchyAdapter(ConfigurationFile.class, new CommandConfiguration())
                .registerTypeAdapter(ConfigurationFile.class, new ConfigurationFile())
                .disableHtmlEscaping()
                .create();
    }
    public static ConfigurationFile loadConfigFromFile(String resourcePath) {
        final ClassLoader classLoader = FileConfigReader.class.getClassLoader();

        try (InputStream is = classLoader.getResourceAsStream(resourcePath)) {
            if (is == null) throw new IOException("Resource not found: " + resourcePath);

            try (InputStreamReader reader = new InputStreamReader(is, StandardCharsets.UTF_8)) {
                return gson.fromJson(reader, ConfigurationFile.class);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
