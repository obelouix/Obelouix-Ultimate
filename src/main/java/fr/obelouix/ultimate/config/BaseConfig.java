package fr.obelouix.ultimate.config;

import org.spongepowered.configurate.serialize.SerializationException;

public abstract class BaseConfig {

    /**
     * Used for adding options into the config file
     *
     * @throws SerializationException
     */
    protected void addToConfig() throws SerializationException {
    }

    /**
     * Read the config file and setup the plugin
     */
    protected void readConfig() {
    }

}
