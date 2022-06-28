package fr.obelouix.ultimate.events;

import com.j256.ormlite.stmt.QueryBuilder;
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
import org.bukkit.map.MapView;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.SQLException;
import java.util.List;

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
            PlayerData.setShowCoordinates(player, true);
            final PlayerTable playerData = new PlayerTable(player.getUniqueId(), player.getName(), fr.obelouix.ultimate.data.player.Player.getLocale());
            Database.PlayerDao.createOrUpdateData(playerData);

            final PlayerOptionsTable playerOptionsTable = new PlayerOptionsTable(player.getUniqueId(), true);
            Database.PlayerOptionsDao.createData(playerOptionsTable);

            QueryBuilder<PlayerOptionsTable, String> playerOptions = Database.getPlayerOptionsDao().queryBuilder();
            try {
                List<PlayerOptionsTable> list = playerOptions.selectColumns("SHOWCOORDINATES").where().eq("UUID", player.getUniqueId()).query();
                if (!list.isEmpty()) {
                    player.sendMap((MapView) list.get(0));
                }
            } catch (SQLException ignored) {

            }
/*            try {
                if (Database.getPlayerOptionsDao().queryBuilder().selectColumns("showCoordinates").where().eq("UUID", player.getUniqueId()) == null) {

                    final PlayerOptionsTable playerOptions = new PlayerOptionsTable(player.getUniqueId(), true);
                    Database.PlayerOptionsDao.createOrUpdateData(playerOptions);

                    final boolean show = Boolean.parseBoolean(String.valueOf(Database.getPlayerOptionsDao().queryBuilder().selectColumns("SHOWCOORDINATES").where().eq("UUID", player.getUniqueId())));

                    PlayerData.setShowCoordinates(player, show);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }*/
        }
    }

}
