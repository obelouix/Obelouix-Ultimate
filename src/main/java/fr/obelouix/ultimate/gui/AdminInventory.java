package fr.obelouix.ultimate.gui;

import fr.obelouix.ultimate.api.InventoryAPI;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public class AdminInventory extends BaseGUI {

    private final Inventory inventory;

    public AdminInventory(Player player) {
        inventory = new InventoryAPI(54, Component.text(i18n.getTranslation(player, "obelouix.inventory.admin_center")));
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
