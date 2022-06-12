package fr.obelouix.ultimate.data;

import fr.obelouix.ultimate.ObelouixUltimate;
import fr.obelouix.ultimate.config.Config;
import fr.obelouix.ultimate.database.Database;

import java.util.Locale;

public class StorageType {

    private static final ObelouixUltimate plugin = ObelouixUltimate.getInstance();
    private static final String H2_URL = "jdbc:h2:file:" + plugin.getDataFolder().getAbsolutePath() + "/" + plugin.getName();
    private static String MYSQL_URL = "jdbc:mysql://{url}:{port}/ObelouixUltimate";
    private static boolean usingFiles = false;

    private static Database database;

    public static void setup() throws Exception {

        switch (Config.getStorageType().toLowerCase(Locale.ROOT)) {
            case "h2" -> {
                if (database == null) database = new Database(H2_URL + ";AUTO_SERVER=TRUE");
            }
            case "mysql" -> {
                if (database == null) {

                    MYSQL_URL = MYSQL_URL.replace("{url}", Config.getDatabaseUrl())
                            .replace("{port}", String.valueOf(Config.getDatabasePort()));

                    database = new Database(MYSQL_URL);
                }
            }
            case "file" -> usingFiles = true;
            default -> {
                plugin.getLogger().severe("misconfigured storage type, using files to store data as fallback");
                plugin.getLogger().severe("Please stop the server and fix this in the plugin config");
                usingFiles = true;
            }

        }

        plugin.getLogger().info("Using " + Config.getStorageType() + " as storage method");

    }

    public static boolean isUsingFiles() {
        return usingFiles;
    }

    public static Database getDatabase() {
        return database;
    }
}
