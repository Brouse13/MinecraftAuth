package es.brouse.minecraftauth;

import java.util.List;

/**
 * Interface to define common methods for file-based configuration management.
 * This interface allows for getting and setting configuration properties,
 * as well as saving the configuration to disk.
 */
public interface FileConfiguration {

    /**
    * Gets the value of a configuration property.
    *
    * @param key The key of the property to retrieve
    * @return The value of the property, or null if not found
    */
    String getProperty(String key);

    /**
     * Gets a list of values for a configuration property.
     *
     * @param key The key of the property to retrieve
     * @return A list of values for the property, or an empty list if not found
     */
    List<String> getListProperty(String key);

    /**
    * Sets the value of a configuration property.
    *
    * @param key The key of the property to set
    * @param value The value to set for the property
    */
    void setProperty(String key, String value);

    /**
     * Sets a list of values for a configuration property.
     *
     * @param key The key of the property to set
     * @param value The list of values to set for the property
     */
    void setListProperty(String key, List<String> value);

    /**
    * Saves the current configuration to disk.
    */
    void save();
}
