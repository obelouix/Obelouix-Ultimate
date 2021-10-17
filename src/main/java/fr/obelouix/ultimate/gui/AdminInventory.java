package fr.obelouix.ultimate.gui;

import fr.obelouix.ultimate.api.InventoryAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryView;
import org.jetbrains.annotations.NotNull;

public class AdminInventory extends BaseGUI {

    private Component playerManagementComponent;
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
        setupInventory();
        showInventory(player);
    }

    @Override
    protected Component title(Player player) {
        return Component.text(i18n.getTranslation(player, "obelouix.inventory.admin_center"), NamedTextColor.DARK_RED);
    }

    protected void setupInventory() {
        inventory.setItem(0, InventoryAPI.addCustomSkull(viewer, playerManagementComponent));
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
