package es.brouse.minecraftauth.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import es.brouse.minecraftauth.config.CommandConfiguration;
import es.brouse.minecraftauth.config.ConfigurationFile;
import lombok.experimental.UtilityClass;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

@UtilityClass
public class ObjectFileReader {
    private static final Gson gson;

    static {
        gson = new GsonBuilder()
                .registerTypeHierarchyAdapter(ConfigurationFile.class, new CommandConfiguration())
                .registerTypeAdapter(ConfigurationFile.class, new ConfigurationFile())
                .disableHtmlEscaping()
                .create();
    }

    /**
     * Reads a configuration file from the internal resources.
     *
     * @param clazz the class type to deserialize the JSON into.
     * @param fileName the name of the file in the resources' directory.
     * @return an instance of the specified class type containing the data from the file
     * @param <T> the type of the class to deserialize into
     */
    public static <T> T readInternalFile(Class<T> clazz, String fileName) throws IOException {
        // TODO Improve creation of file
        final ClassLoader classLoader = ObjectFileReader.class.getClassLoader();

        try (InputStream is = classLoader.getResourceAsStream(fileName)) {
            if (is == null) throw new IOException("Resource not found: " + fileName);

            try (InputStreamReader reader = new InputStreamReader(is, StandardCharsets.UTF_8)) {
                return gson.fromJson(reader, clazz);
            }
        }
    }

    /**
     * Reads a configuration file from an external file system path.
     *
     * @param clazz the class type to deserialize the JSON into.
     * @param fileName the path to the file in the external file system.
     * @return an instance of the specified class type containing the data from the file
     * @param <T> the type of the class to deserialize into
     */
    public static <T> T readExternalFile(Class<T> clazz, String fileName) throws IOException {
        try(FileReader reader = new FileReader(fileName)) {
            // TODO Improve creation of file
            return gson.fromJson(reader, clazz);
        }
    }

    public static <T> T readExternalFile(Type type, String fileName) throws IOException {
        try (FileReader reader = new FileReader(fileName)) {
            // TODO Improve creation of file
            return gson.fromJson(reader, type);
        }
    }
}
