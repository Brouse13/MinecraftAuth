package es.brouse.minecraftauth.registry;

import com.fasterxml.jackson.core.type.TypeReference;
import es.brouse.minecraftauth.utils.ObjectFileReader;
import es.brouse.minecraftauth.utils.ObjectFileWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MapPlayerRegistry implements PlayerRegistry {
    private static final Logger logger = LoggerFactory.getLogger(MapPlayerRegistry.class);
    private final Map<Long, String> registry = new HashMap<>();

    @Override
    public boolean isRegistered(Long playerName) {
        return registry.containsKey(playerName);
    }

    @Override
    public boolean isPlayerUsername(String username) {
        return registry.containsValue(username);
    }

    @Override
    public String getPlayerUserName(Long id) {
        return registry.get(id);
    }

    @Override
    public boolean registerPlayer(Long id, String playerName, boolean force) {
        return (force || !registry.containsKey(id)) && registry.put(id, playerName) == null;
    }

    @Override
    public String unregisterPlayer(Long id) {
        return registry.remove(id);
    }

    @Override
    public boolean save(String fileName) {
        try {
            // TODO Improve creation of file
            ObjectFileWriter.writeExternalFile(registry, fileName);
        }catch (IOException e) {
            logger.error("Failed to save player registry to file: " + fileName, e);
            return false;
        }

        return true;
    }

    @Override
    public boolean load(String fileName) {
        try {
            // TODO Improve creation of file
            registry.putAll(ObjectFileReader.readExternalFile(new TypeReference<Map<Long, String>>() {}, fileName));
        } catch (IOException e) {
            logger.error("Failed to load player registry from file: " + fileName, e);
            return false;
        }

        logger.info(registry.keySet().toString());

        return true;
    }
}
