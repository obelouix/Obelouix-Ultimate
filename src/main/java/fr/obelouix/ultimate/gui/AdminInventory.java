package fr.obelouix.ultimate.gui;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class AdminInventory extends BaseGUI {

    public AdminInventory() {
    }

    public AdminInventory(Player player) {
        inventory = Bukkit.createInventory(null, 54, title(player));
        setupInventory();
        showInventory(player);
    }

    @Override
    protected Component title(Player player) {
        return Component.text(i18n.getTranslation(player, "obelouix.inventory.admin_center"), NamedTextColor.DARK_RED);
    }

    private void setupInventory() {
        inventory.setItem(0, new ItemStack(Material.DIRT));
    }

    @Override
    public void showInventory(@NotNull Player player) {
        player.openInventory(inventory);
    }

    @Override
    @EventHandler
    public void cancelClick(InventoryClickEvent event) {
        InventoryView view = event.getView();
        Player player = (Player) event.getWhoClicked();
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
