package fr.obelouix.ultimate.events;

import com.j256.ormlite.stmt.QueryBuilder;
import fr.obelouix.ultimate.ObelouixUltimate;
import fr.obelouix.ultimate.api.ItemBuilder;
import fr.obelouix.ultimate.api.WeaponBuilder;
import fr.obelouix.ultimate.config.Config;
import fr.obelouix.ultimate.data.PlayerData;
import fr.obelouix.ultimate.database.Database;
import fr.obelouix.ultimate.database.models.PlayerOptionsTable;
import fr.obelouix.ultimate.database.models.PlayerTable;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.map.MapView;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class PlayerConnectionEvent implements Listener {

    private static final ObelouixUltimate plugin = ObelouixUltimate.getInstance();

    public PlayerConnectionEvent() {

    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onPlayerJoin(PlayerLoginEvent event) {
        final Player player = event.getPlayer();

        if (!Config.getStorageType().equalsIgnoreCase("file")) {
            PlayerData.setShowCoordinates(player, true);
            final PlayerTable playerData = new PlayerTable(player.getUniqueId(), player.getName(), fr.obelouix.ultimate.data.player.Player.getLocale());
            Database.PlayerDao.createOrUpdateData(playerData);

            final PlayerOptionsTable playerOptionsTable = new PlayerOptionsTable(player.getUniqueId(), true);
            Database.PlayerOptionsDao.createData(playerOptionsTable);

            QueryBuilder<PlayerOptionsTable, String> playerOptions = Database.getPlayerOptionsDao().queryBuilder();
            try {
                List<PlayerOptionsTable> list = playerOptions.selectColumns("SHOWCOORDINATES").where().eq("UUID", player.getUniqueId()).query();
                list.forEach(System.out::println);
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

/*    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent event) {
        //FakeServerBrand.sendFakeBrand(event.getPlayer());
        final Player player = event.getPlayer();
        if (player.hasPermission("obelouix.silentjoin")) {
            event.joinMessage(null);
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                Component message = Translator.translate("obelouix.silentjoin", player)
                        .color(NamedTextColor.DARK_AQUA)
                        .replaceText(builder -> builder.matchLiteral("{0}").replacement(event.getPlayer().displayName()));
                MessagesAPI.sendMessage(Audience.SILENT_JOIN_AUDIENCE, message);
            }
        }.runTaskLater(plugin, 60);


//        event.joinMessage(Component.empty());
    }*/

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        final ItemStack testItem = new ItemBuilder(Material.ACACIA_LOG)
                .displayname(Component.text("Test", NamedTextColor.GREEN))
                .addGlowingEffect()
                .setLore(Collections.singletonList(Component.text("Ceci est un test", TextColor.color(100, 200, 125))))
                .build();

        final ItemStack weaponTest = new WeaponBuilder(Material.NETHERITE_SWORD)
                .displayname(Component.text("Test", NamedTextColor.GREEN))
                .addGlowingEffect()
                .addEnchantement(Enchantment.DAMAGE_ALL, 5)
                .setAttackDamageAndSpeed(10, 2.5)
                .setLore(Collections.singletonList(Component.text("Ceci est un test", TextColor.color(100, 200, 125))))
                .build();

        event.getPlayer().getInventory().addItem(testItem);
        event.getPlayer().getInventory().addItem(weaponTest);
    }

}
