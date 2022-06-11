package fr.obelouix.ultimate.events;

import fr.obelouix.ultimate.ObelouixUltimate;
import fr.obelouix.ultimate.config.Config;
import fr.obelouix.ultimate.data.PlayerData;
import fr.obelouix.ultimate.database.Database;
import fr.obelouix.ultimate.database.models.PlayerOptionsTable;
import fr.obelouix.ultimate.database.models.PlayerTable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.SQLException;

public class PlayerConnectionEvent implements Listener {

    private static final ObelouixUltimate plugin = ObelouixUltimate.getInstance();

    public PlayerConnectionEvent() {

    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerJoin(PlayerLoginEvent event) {
        final Player player = event.getPlayer();

        final BukkitRunnable bukkitRunnable = new BukkitRunnable() {
            @Override
            public void run() {
                PlayerData.setLocale(PlayerData.getPlayerLocaleString(event.getPlayer()));
            }
        };
        //execute the task 10 ticks ( = 500 ms) after player logged in
        bukkitRunnable.runTaskLaterAsynchronously(plugin, 10L);

        if (!Config.getStorageType().equalsIgnoreCase("file")) {

            final PlayerTable playerData = new PlayerTable(player.getUniqueId(), player.getName(), fr.obelouix.ultimate.data.player.Player.getLocale());
            Database.PlayerDao.createOrUpdateData(playerData);

            try {
                if (Database.getPlayerOptionsDao().queryBuilder().selectColumns("showCoordinates").where().eq("UUID", player.getUniqueId()) == null) {

                    final PlayerOptionsTable playerOptions = new PlayerOptionsTable(player.getUniqueId(), true);
                    Database.PlayerOptionsDao.createOrUpdateData(playerOptions);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
