package fr.obelouix.ultimate.utils;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import fr.obelouix.ultimate.ObelouixUltimate;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Updater {

    private static final ObelouixUltimate plugin = ObelouixUltimate.getInstance();

    public Updater() {
        // 216000 -> 3 hours
        checkForUpdate().runTaskTimerAsynchronously(plugin, 0, 216000);
    }

    private BukkitRunnable checkForUpdate() {
        return new BukkitRunnable() {

            @Override
            public void run() {
                BufferedReader reader = null;
                Gson gson = new Gson();
                final JsonPrimitive result;
                try {
                    InputStream inputStream = new URL("https://api.github.com/repos/obelouix/Obelouix-Ultimate/releases/latest").openStream();
                    reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

                    plugin.getLogger().info("Checking for updates...");
                    result = JsonParser.parseReader(reader).getAsJsonObject().getAsJsonPrimitive("tag_name");

                    if (updateAvailable(plugin.getDescription().getVersion().replace("-SNAPSHOT", ""), result.getAsString().replace("-SNAPSHOT", ""))) {
                        boolean isBothSnapshot = plugin.getDescription().getVersion().contains("-SNAPSHOT") && result.getAsString().contains("-SNAPSHOT");
                        if (isBothSnapshot) {
                            plugin.getLogger().info("A new snapshot is available, download it here: https://github.com/obelouix/Obelouix-Ultimate/releases/latest");
                        } else {
                            if (!isBothSnapshot) {
                                plugin.getLogger().info("A new update available, download it here: https://github.com/obelouix/Obelouix-Ultimate/releases/latest");
                            } else plugin.getLogger().info("We are up-to-date!");
                        }

                    } else plugin.getLogger().info("We are up-to-date!");


                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }

    private boolean updateAvailable(String pluginVersion, String githubPluginVersion) {
        String[] pVersion = pluginVersion.split("\\.");
        String[] gVersion = githubPluginVersion.split("\\.");
        for (int i = 0; i < 3; i++) {
            if (Integer.parseInt(gVersion[i]) > Integer.parseInt(pVersion[i])) return true;
        }
        return false;
    }

}
