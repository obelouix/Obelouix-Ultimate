package fr.obelouix.ultimate.data;

import fr.obelouix.ultimate.ObelouixUltimate;
import fr.obelouix.ultimate.config.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataStorage {

    private static final ObelouixUltimate plugin = ObelouixUltimate.getInstance();
    private static boolean fileBasedStorage = false;

    public static void setupStorage(){
        Connection connection = null;
        Statement statement = null;
        String databaseURL;

        plugin.getLogger().info("Using " + Config.getStorageType() + " data storage type");

        if (Config.getStorageType().equalsIgnoreCase("file")) fileBasedStorage = true;
        else if (Config.getStorageType().equalsIgnoreCase("H2")) {
            try {
                Class.forName("org.h2.Driver");
                databaseURL = "jdbc:h2:" + plugin.getDataFolder() + "/database";
                connection = DriverManager.getConnection(databaseURL, "", "");
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        } else {
            //if a wrong value if given force the plugin to use file storage method
            fileBasedStorage = true;
        }
    }

    public static boolean isFileBasedStorage() {
        return fileBasedStorage;
    }
}
