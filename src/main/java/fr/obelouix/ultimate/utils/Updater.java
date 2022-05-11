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
import java.util.Objects;

public class Updater {

    private static final ObelouixUltimate plugin = ObelouixUltimate.getInstance();
    private static JsonParser parser = null;

    public Updater() {
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

                    if (!Objects.equals(result.getAsString(), plugin.getDescription().getVersion())) {
                        plugin.getLogger().warning("You are using a DEV build");
                        plugin.getLogger().info("Update available, download it here: https://github.com/obelouix/Obelouix-Ultimate/releases/latest");
                    } else plugin.getLogger().info("We are up-to-date!");

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            // 216000 -> 3 hours
        };
    }


}
