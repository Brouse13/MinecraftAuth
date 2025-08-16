package es.brouse.minecraftauth.registry;

public interface SavingRegister {
    /**
     * Saves the current state of the registry to a file.
     *
     * @param fileName the name of the file where the registry will be saved
     * @return true if the save operation was successful, false otherwise
     */
    boolean save(String fileName);

    /**
     * Loads the registry from a specified file.
     *
     * @param fileName the name of the file from which to load the registry
     * @return true if the load operation was successful, false otherwise
     */
    boolean load(String fileName);
}
