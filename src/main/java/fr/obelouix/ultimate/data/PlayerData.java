package fr.obelouix.ultimate.data;

import fr.obelouix.ultimate.ObelouixUltimate;
import fr.obelouix.ultimate.utils.FakeServerBrand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLocaleChangeEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;

public class PlayerData implements Listener {

    private static final ObelouixUltimate plugin = ObelouixUltimate.getInstance();
    private static String playerLocale;
    private static final File dataFolder = new File(plugin.getDataFolder(), "data");
    /**
     * This method allow to get the client locale of a player
     *
     * @return the locale of the player
     */
    static String getPlayerLocaleString(@NotNull Player player) {
        return player.locale().toString();
    }

    //High priority because we must get the player locale before sending any translated messages
    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerJoin(PlayerJoinEvent event) throws IOException {
        final BukkitRunnable bukkitRunnable = new BukkitRunnable() {
            @Override
            public void run() {
                playerLocale = getPlayerLocaleString(event.getPlayer());
            }
        };
        //execute the task 10 ticks ( = 500 ms) after player logged in
        bukkitRunnable.runTaskLaterAsynchronously(plugin, 10L);

        FakeServerBrand.sendFakeBrand(event.getPlayer());

        if (DataStorage.isFileBasedStorage()) {
            HoconConfigurationLoader playerFile = HoconConfigurationLoader.builder()
                    .path(Path.of(plugin.getDataFolder().getPath(), "data", "players", event.getPlayer().getName() + ".conf"))
                    .build();
            CommentedConfigurationNode root = playerFile.load();
            root.node("uuid").set(event.getPlayer().getUniqueId());
            root.node("IP").set(Objects.requireNonNull(event.getPlayer().getAddress()).getHostName());
            playerFile.save(root);
        }

    }

    @EventHandler
    public void onPlayerChangeLocale(PlayerLocaleChangeEvent event) {
        final BukkitRunnable bukkitRunnable = new BukkitRunnable() {
            @Override
            public void run() {
                playerLocale = getPlayerLocaleString(event.getPlayer());
            }
        };
        //execute the task 10 ticks ( = 500 ms) after player logged in
        bukkitRunnable.runTaskLaterAsynchronously(plugin, 10L);
    }

    public String getPlayerLocale() {
        return playerLocale;
    }


}