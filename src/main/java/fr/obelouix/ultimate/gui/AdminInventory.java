package fr.obelouix.ultimate.gui;

import fr.obelouix.ultimate.api.InventoryAPI;
import fr.obelouix.ultimate.utils.CustomHeadSkins;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryView;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class AdminInventory extends BaseGUI {

    // title of the item that will show the inventory that manage online players
    private Component playerManagementComponent;
    private Component playerManagementDescriptionComponent;
    // title of the item that will show the inventory that manage server worlds
    private Component worldManagementComponent;
    // the player who see the inventory
    private Player viewer;

    /**
     * This constructor is only for registering the event
     */
    public AdminInventory() {
    }

    /**
     * This constructor set up the inventory to open when called
     *
     * @param player - the player that will see the inventory
     */
    public AdminInventory(Player player) {
        inventory = Bukkit.createInventory(null, 54, title(player));
        this.viewer = player;
        playerManagementComponent = Component.text(i18n.getTranslation(player, "obelouix.inventory.admin_center.player_management"), NamedTextColor.GREEN);
        playerManagementDescriptionComponent = Component.text(i18n.getTranslation(player, "obelouix.inventory.admin_center.player_management.description"), NamedTextColor.GOLD)
                .decoration(TextDecoration.ITALIC, false);
        worldManagementComponent = Component.text(i18n.getTranslation(player, "obelouix.inventory.admin_center.world_management"), NamedTextColor.GOLD);
        setupInventory();
        showInventory(player);
    }

    @Override
    protected Component title(Player player) {
        return Component.text(i18n.getTranslation(player, "obelouix.inventory.admin_center"), NamedTextColor.DARK_RED);
    }

    protected void setupInventory() {
        inventory.setItem(0, InventoryAPI.addCustomSkull(viewer, playerManagementComponent));
        InventoryAPI.addLore(Objects.requireNonNull(inventory.getItem(0)),
                List.of(playerManagementDescriptionComponent));
        inventory.setItem(4, InventoryAPI.addCustomSkull(CustomHeadSkins.GLOBE, worldManagementComponent));
    }

    @Override
    public void showInventory(@NotNull Player player) {
        player.openInventory(inventory);
    }

    @Override
    @EventHandler
    public void cancelClick(InventoryClickEvent event) {
        final InventoryView view = event.getView();
        final Player player = (Player) event.getWhoClicked();
        if (view.title().equals(title(player))) {
            event.setCancelled(true);
        }
    }

    @Override
    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent event) {
//        event.setCancelled(true);
    }
}
