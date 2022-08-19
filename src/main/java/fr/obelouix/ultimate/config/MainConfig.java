package fr.obelouix.ultimate.config;

import org.spongepowered.configurate.serialize.SerializationException;

class MainConfig extends BaseConfig {

    @Override
    protected void addToConfig() throws SerializationException {

        if (Config.getRoot().node("data", "storage-type").empty()) {
            Config.getRoot().node("data", "storage-type").set("file")
                    .commentIfAbsent("Choose between: file, h2, mysql");
        }

        if (Config.getRoot().node("data", "database", "url").empty()) {
            Config.getRoot().node("data", "database", "url").set("")
                    .commentIfAbsent("Use this only for mysql");
        }

        if (Config.getRoot().node("data", "database", "port").empty()) {
            Config.getRoot().node("data", "database", "port").set(3306)
                    .commentIfAbsent("Use this only for mysql");
        }

        if (Config.getRoot().node("data", "database", "username").empty()) {
            Config.getRoot().node("data", "database", "username").set("")
                    .commentIfAbsent("Optional for h2");
        }

        if (Config.getRoot().node("data", "database", "password").empty()) {
            Config.getRoot().node("data", "database", "password").set("")
                    .commentIfAbsent("Optional for h2");
        }

        if (Config.getRoot().node("debug-mode").empty()) {
            Config.getRoot().node("debug-mode").set(false)
                    .commentIfAbsent("print extra plugin logs (only use it you have problems)");
        }

        if (Config.getRoot().node("disable-default-reload-command").empty()) {
            Config.getRoot().node("disable-default-reload-command").set(true)
                    .commentIfAbsent("""
                            Cancel the reload command when fired
                            If you don't know why this command shouldn't be used read this: https://madelinemiller.dev/blog/problem-with-reload/
                            """);
        }
    }

    @Override
    protected void readConfig() {
        Config.debugMode = Config.getRoot().node("debug-mode").getBoolean();
        Config.disableReloadCommand = Config.getRoot().node("disable-default-reload-command").getBoolean();

        Config.storageType = Config.getRoot().node("storage-type").getString();
        if (Config.storageType != null && Config.storageType.equalsIgnoreCase("mysql")) {
            Config.databaseUrl = Config.getRoot().node("data", "database", "url").getString();
            Config.databasePort = Config.getRoot().node("data", "database", "port").getInt();

        }
/*        if (Stream.of("mysql", "h2").anyMatch(s -> Config.storageType.equalsIgnoreCase(s))) {
            Config.databaseUsername = Config.getRoot().node("data", "database", "username").getString();
            Config.databasePassword = Config.getRoot().node("data", "database", "password").getString();
        }*/

    }

}
