package es.brouse.minecraftauth.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import es.brouse.minecraftauth.config.CommandConfiguration;
import es.brouse.minecraftauth.config.ConfigurationFile;
import lombok.experimental.UtilityClass;

import java.io.FileWriter;
import java.io.IOException;

@UtilityClass
public class ObjectFileWriter {
    private static final Gson gson;

    static {
        gson = new GsonBuilder()
                .registerTypeHierarchyAdapter(ConfigurationFile.class, new CommandConfiguration())
                .registerTypeAdapter(ConfigurationFile.class, new ConfigurationFile())
                .disableHtmlEscaping()
                .create();
    }

    /**
     * Writes a file from an external file system path.
     *
     * @param object object to save into the file as json.
     * @param fileName the path to the file in the external file system.
     * @param <T> the type of the class to deserialize into
     * @throws IllegalStateException if the file name ends with ".json"
     */
    public static <T> void writeExternalFile(T object, String fileName) throws IOException {
        // if (fileName.endsWith(".json")) throw new IllegalStateException("File should be a valid json");

        try(FileWriter writer = new FileWriter(fileName)) {
           gson.toJson(object, writer);
        }
    }
}
