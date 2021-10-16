package fr.obelouix.ultimate.gui;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;

public class AdminInventory extends BaseGUI {

    public AdminInventory(Player player) {
        inventory = Bukkit.createInventory(null, 54, Component.text(i18n.getTranslation(player, "obelouix.inventory.admin_center"), NamedTextColor.DARK_RED));
        setupInventory();
        showInventory(player);
    }

    private void setupInventory() {

    }

    @Override
    public void showInventory(@NotNull Player player) {
        player.openInventory(inventory);
    }

    @Override
    public void cancelClick(InventoryClickEvent event) {

    }

    @Override
    public void onInventoryClickEvent(InventoryClickEvent event) {

    }
}
