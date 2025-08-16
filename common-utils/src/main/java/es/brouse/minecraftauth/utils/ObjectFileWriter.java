package es.brouse.minecraftauth.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;

import java.io.FileWriter;
import java.io.IOException;

@UtilityClass
public class ObjectFileWriter {
    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * Writes a file from an external file system path.
     *
     * @param object object to save into the file as json.
     * @param fileName the path to the file in the external file system.
     * @param <T> the type of the class to deserialize into
     * @throws IllegalStateException if the file name ends with ".json"
     */
    public static <T> void writeExternalFile(T object, String fileName) throws IOException {
        if (!fileName.endsWith(".json")) throw new IllegalStateException("File should be a valid json");

        mapper.writeValue(new FileWriter(fileName), object);
    }
}
