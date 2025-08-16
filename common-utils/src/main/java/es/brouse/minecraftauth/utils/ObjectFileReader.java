package es.brouse.minecraftauth.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@UtilityClass
public class ObjectFileReader {
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Reads a configuration file from the internal resources.
     *
     * @param clazz    the class type to deserialize the JSON into.
     * @param fileName the name of the file in the resources' directory.
     * @param <T>      the type of the class to deserialize into
     * @return an instance of the specified class type containing the data from the file
     */
    public static <T> T readInternalFile(Class<T> clazz, String fileName) throws IOException {
        // TODO Improve creation of file
        final ClassLoader classLoader = ObjectFileReader.class.getClassLoader();

        try (InputStream is = classLoader.getResourceAsStream(fileName)) {
            if (is == null) throw new IOException("Resource not found: " + fileName);

            try (InputStreamReader reader = new InputStreamReader(is, StandardCharsets.UTF_8)) {
                return objectMapper.readValue(reader, clazz);
            }
        }
    }

    /**
     * Reads a configuration file from an external file system path.
     *
     * @param type the type reference to deserialize the JSON into.
     * @param fileName the path to the file in the external file system.
     * @param <T> the type of the class to deserialize into
     * @return an instance of the specified class type containing the data from the file
     */
    public static <T> T readExternalFile(TypeReference<T> type, String fileName) throws IOException {
        // TODO Improve creation of file
        return objectMapper.readValue(new FileReader(fileName), type);
    }
}
